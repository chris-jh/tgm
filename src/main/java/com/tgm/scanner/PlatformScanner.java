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
import com.tgm.data.interfaces.GameDaoInterface;
import com.tgm.data.tgdb.Game;
import com.tgm.data.tgdb.images.BoxArt;
import com.tgm.data.tgdb.images.Images;
import com.tgm.enums.Platform;
import com.tgm.resources.Path;
import com.tgm.resources.TgmResource;
import com.tgm.scrapers.search.SearchResults;
import com.tgm.scrapers.search.type.SearchType;
import com.tgm.utils.GameUtils;
import com.tgm.utils.MountUtils;
import com.tgm.utils.OSUtils;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MountUtils mountUtils;
    private boolean scanning = false;
    private boolean processingScan = false;
    private final HashMap<Platform, PlatformConfig> platformConfigs = new HashMap<Platform, PlatformConfig>();
    @Autowired(required = false)
    private SearchType searchInterface;
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

    @Transactional
    @Override
    public GameEntity saveGame(PlatformEntity platform, String name, String path) throws Exception {
        GameEntity g = null;
        try {
            try {
                g = gameDao.findByNameAndPlatform(name, platform);
            } catch (Exception e) {
                g = null;
            }

            if (g == null) {
                SearchResults r = null;
                Game rs = null;
                try {
                    if (searchInterface != null) {
                        r = searchInterface.search(name, platform.getName().toString());
                        rs = r.getGames().get(0);
                    }
                } catch (Exception e) {
                    rs = null;
                }

                int i = RandomUtils.nextInt(4) + 1;
                g = gameDao.createInstance();
                g.setGamePath(path);
                g.setName(rs != null ? rs.getGameTitle() : name);
                g.setFileName(name);
                g.setDateAdded(new Date());
                g.setDateLastPlayed(new Date(0));
                g.setRating(i);


                String boxImage = null;
                String boxImagePath = null;
                if (rs != null) {
                    try {
                        for (Images images : rs.getGameImages()) {
                            for (BoxArt boxArt : images.getBoxArt()) {
                                if (boxArt.getSide().equals("front")) {
                                    boxImage = boxArt.getValue();
                                }
                            }
                        }
                        if (boxImage != null) {
                            boxImagePath = osUtils.getAppHomePath() + "/art/tgdb/" + boxImage;
                            TgmResource.saveImage(searchInterface.getArtUrl() + "/" + boxImage, boxImagePath);
                        }
                    } catch (Exception e) {
                        boxImagePath =null;
                    }
                }

                g.setImageBoxArtPath(boxImagePath != null ? boxImagePath : "images/test/gameTest" + i + ".jpg");
                g.setImageArtPath("images/test/artTest" + i + ".jpg");
                g.setImageScreenShotPath("images/test/artTest" + i + ".jpg");
                g.setOverviewText(rs != null ? rs.getOverview() : "Some Random Text " + i);
                g.setPlayCount(i);
                g.setExternalId(rs != null ? rs.getId() : (long) i);
                g.setPlatformRef(platform);
                g = gameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("SAVED GAME: " + g.toString() + " " + g.getName() + " " + g.getId());
                return g;
            } else {
                return g;
            }
        } finally {
            g = null;
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
            Logger.getLogger(this.getClass()).info("   " + game.getPlatformRef().getName() + " - " + game.getName() + " [" + game.getGamePath() + "]");
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
}
