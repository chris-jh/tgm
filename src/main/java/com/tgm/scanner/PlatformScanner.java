/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scanner;

import com.tgm.interfaces.PlatformScannerInterface;
import com.tgm.config.PlatformConfig;
import com.tgm.data.interfaces.PlatformDaoInterface;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.GamePathEntity;
import com.tgm.data.interfaces.GameDaoInterface;
import com.tgm.data.interfaces.GamePathDaoInterface;
import com.tgm.enums.Platform;
import com.tgm.resources.Path;
import com.tgm.resources.TgmResource;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
import com.tgm.scrapers.interfaces.ResultInterface;
import com.tgm.scrapers.interfaces.ScraperInterface;
import com.tgm.utils.GameUtils;
import com.tgm.utils.MountUtils;
import com.tgm.utils.OSUtils;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
public class PlatformScanner implements PlatformScannerInterface, DisposableBean {

    @Autowired
    private PlatformDaoInterface<PlatformEntity, Integer> platformDao;
    @Autowired
    private GameDaoInterface<GameEntity, Integer> gameDao;
    @Autowired
    private GamePathDaoInterface<GamePathEntity, Integer> gamePathDao;
    @Autowired
    private MountUtils mountUtils;
    private boolean scanning = false;
    private boolean processingScan = false;
    @Autowired(required = false)
    private ScraperInterface scraperInterface;
    @Autowired
    private OSUtils osUtils;

    @Transactional
    @Override
    public boolean scanPlatform(PlatformConfig config) throws Exception {
        Logger.getLogger(this.getClass()).info("Scanning Platform: " + config.getPlatform());
        scanning = true;
        PlatformEntity platform = savePlatform(config);
        for (Path path : config.getGamePath()) {
            scanGamePath(platform, path);
            if (!scanning) {
                return false;
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Platform: " + config.getPlatform() + " Done");
        scanning = false;
        return true;
    }

    @Override
    public void scanGamePath(PlatformEntity platform, Path path) {
        Logger.getLogger(this.getClass()).info("Scanning Game Path: [" + path.getPath() + "]");
        if (mountUtils.isMountable(path)) {
            if (mountUtils.mount(platform.getName(), path)) {
                processingScan = true;
                try {
                    GameUtils.processGameFiles(this, platform.getName(), mountUtils.getMountedPath(platform.getName(), path), platform.getName().getFileTypes(), true);
                } catch (Exception e) {
                    Logger.getLogger(this.getClass()).fatal(e);
                }
                if (!mountUtils.unmount(platform.getName(), path)) {
                    Logger.getLogger(this.getClass()).fatal("Unable to unmount drive for platform: " + platform.getName());
                }
                processingScan = false;
            } else {
                Logger.getLogger(this.getClass()).fatal("Unable to mount drive check gamepath config for platform: " + platform.getName());
            }
        } else {
            try {
                GameUtils.processGameFiles(this, platform.getName(), mountUtils.getMountedPath(platform.getName(), path), platform.getName().getFileTypes(), false);
            } catch (Exception e) {
                Logger.getLogger(this.getClass()).fatal(e);
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Game Path: [" + path.getPath() + "] Completed");
    }

    //TODO: Add method to find game details from thegamesdb.net
    @Transactional
    @Override
    public void processGame(Platform platform, String fileName, String path) throws Exception {
        Logger.getLogger(this.getClass()).info("Found Game: " + platform.name() + " - " + fileName + " | [" + path + "]");
        saveGame(platformDao.findPlatformByName(platform), fileName, path);
    }

    @Transactional
    @Override
    public PlatformEntity savePlatform(PlatformConfig config) throws Exception {
        PlatformEntity e = null;
        try {
            e = platformDao.findPlatformByName(config.getPlatform());
        } catch (Exception ex) {
        }

        if (e == null) {
            e = platformDao.createInstance();
            e.setName(config.getPlatform());
            e = platformDao.saveOrUpdate(e);
        }
        return e;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public GameEntity saveGame(PlatformEntity platform, String name, String path) throws Exception {
        GameEntity g = null;
        GamePathEntity gP = null;

        ResultInterface r = null;
        GameDetailsInterface gameDetail = null;

        String gameName = name;

        String boxImage = null;
        String artImage = null;
        String screenImage = null;

        String boxImagePath = null;
        String artImagePath = null;
        String screenImagePath = null;


        try {
            try {
                gP = gamePathDao.findByFileName(name);
            } catch (NoResultException e) {
                gP = null;
            }

            if (gP != null) {
                return gameDao.findById(gP.getGameRef().getId());
            }

            try {
                if (scraperInterface != null) {
                    r = scraperInterface.search(name, platform.getName().toString());
                    if (r.hasGame()) {
                        gameDetail = r.getFirstGame();
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(this.getClass()).fatal("SCRAPER ERROR: "+e);
                gameDetail = null;
            }

            gameName = (gameDetail != null ? gameDetail.getGameTitle() : name);

            try {
                g = gameDao.findByNameAndPlatform(gameName, platform);
            } catch (NoResultException e) {
                g = null;
            }

            if (g == null) {
                g = gameDao.createInstance();
                g.setName(gameDetail != null ? gameDetail.getGameTitle() : name);
                g.setDateAdded(new Date());
                g.setDateLastPlayed(new Date(0));
                g.setRating((int) Double.parseDouble(gameDetail != null ? gameDetail.getRating() : "0.0"));

                if (gameDetail != null) {
                    boxImage = gameDetail.getBoxArt();
                    artImage = gameDetail.getFanArt();

                    if (boxImage != null) {
                        try {
                            boxImagePath = osUtils.getAppHomePath() + "/boxart/tgdb/" + platform.getName().toString() + "/" + boxImage;
                            TgmResource.saveImage(r.getBaseImgUrl() + "/" + boxImage, boxImagePath);
                        } catch (Exception e) {
                            boxImagePath = null;
                        }
                    }

                    if (artImage != null) {
                        try {
                            artImagePath = osUtils.getAppHomePath() + "/fanart/tgdb/" + platform.getName().toString() + "/" + artImage;
                            TgmResource.saveImage(r.getBaseImgUrl() + "/" + artImage, artImagePath);
                        } catch (Exception e) {
                            artImagePath = null;
                        }
                    }

                    if (screenImage != null) {
                        try {
                            screenImagePath = osUtils.getAppHomePath() + "/fanart/tgdb/" + platform.getName().toString() + "/" + screenImage;
                            TgmResource.saveImage(r.getBaseImgUrl() + "/" + screenImage, screenImagePath);
                        } catch (Exception e) {
                            screenImagePath = null;
                        }
                    }

                }

                g.setImageBoxArtPath(boxImagePath != null ? boxImagePath : "images/blankbox.png");
                g.setImageArtPath(artImagePath != null ? artImagePath : "images/blankart.png");
                g.setImageScreenShotPath(screenImagePath != null ? screenImagePath : "images/blankscreenshot.png");
                g.setOverviewText(gameDetail != null ? gameDetail.getOverview() : "");
                g.setPlayCount(0);
                g.setExternalId(gameDetail != null ? gameDetail.getId() : (long) 0);
                g.setPlatformRef(platform);
                g = gameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("SAVED NEW GAME: " + g.toString() + " " + g.getName() + " " + g.getId());
            }

            gP = gamePathDao.createInstance();
            gP.setGameRef(g);
            gP.setFileName(name);
            gP.setGamePath(path);
            gP = gamePathDao.saveOrUpdate(gP);
            Logger.getLogger(this.getClass()).info("SAVED NEW GAME PATH: " + g.toString() + " " + g.getName() + " " + g.getId() + " " + gP.getGamePath());
            return g;
        } finally {
            g = null;
            g = null;
            gP = null;
            r = null;
            gameDetail = null;
            gameName = null;
            boxImage = null;
            artImage = null;
            screenImage = null;
            boxImagePath = null;
            artImagePath = null;
            screenImagePath = null;
        }
    }

    @Transactional
    @Override
    public void showList() {
        Logger.getLogger(this.getClass()).info("\n\n\nPLatform List...");
        int c = 0;
        for (PlatformEntity platformEntity : platformDao.findAll()) {
            Logger.getLogger(this.getClass()).info("PLATFORM: " + platformEntity.getName() + " " + platformEntity.getId());
        }
        Logger.getLogger(this.getClass()).info("\n\n\nGame List...");

        for (GameEntity game : gameDao.findAllAndFetch()) {
            Logger.getLogger(this.getClass()).info("   " + game.getPlatformRef().getName() + " - " + game.getName() + " [" + game.getDateAdded() + "]");
            for (GamePathEntity gamePathEntity : game.getGamePathList()) {
                Logger.getLogger(this.getClass()).info("       " + gamePathEntity.getFileName() + " - " + gamePathEntity.getGamePath());
            }
            c++;
        }
        Logger.getLogger(this.getClass()).info("Game List...Done [" + c + "]");
    }

    /**
     * @param platformDao the platformDao to set
     */
    public void setPlatformDao(PlatformDaoInterface platformDao) {
        this.platformDao = platformDao;
    }

    /**
     * @param gameDao the gameDao to set
     */
    public void setGameDao(GameDaoInterface gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * @param mountUtils the mountUtils to set
     */
    public void setMountUtils(MountUtils mountUtils) {
        this.mountUtils = mountUtils;
    }

    @Override
    public void destroy() throws Exception {
        Logger.getLogger(this.getClass()).info("DESTROYING SCANNER.....");
        scanning = false;
        int counter = 0;
        while (processingScan) {
            counter++;
            if (counter > 30) {
                processingScan = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean isScanning() {
        return scanning;
    }

    /**
     * @param osUtils the osUtils to set
     */
    public void setOsUtils(OSUtils osUtils) {
        this.osUtils = osUtils;
    }

    /**
     * @param gamePathDao the gamePathDao to set
     */
    public void setGamePathDao(GamePathDaoInterface<GamePathEntity, Integer> gamePathDao) {
        this.gamePathDao = gamePathDao;
    }
}
