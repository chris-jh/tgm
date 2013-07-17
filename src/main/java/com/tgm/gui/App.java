package com.tgm.gui;

import com.tgm.gui.enums.Screen;
import com.tgm.gui.panels.MenuSidePanel;
import com.tgm.gui.interfaces.AppInterface;
import com.tgm.gui.interfaces.ScreenInterface;
import com.tgm.gui.lib.Engine;
import com.tgm.utils.OSUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;

/**
 * Hello world!
 *
 */
public class App implements AppInterface, Runnable {

    @Value(value = "${tgm.width:800}")
    private int width;
    @Value(value = "${tgm.height:600}")
    private int height;
    @Value(value = "${tgm.fullscreen:false}")
    private boolean fullscreen;
    private String title = "TGM";
    private HashMap<Screen, ScreenInterface> screens = new HashMap<Screen, ScreenInterface>();
    private TaskExecutor taskExecutor;
    private boolean running = true;
    private ConcurrentLinkedQueue<Screen> screenQueue = new ConcurrentLinkedQueue<Screen>();
    public static final MenuSidePanel menu = new MenuSidePanel();
    AppGameContainer appGameContainer;
    Engine engine;
    ScreenInterface screen = null;

    public App() {
    }

    public void init() throws Exception {
        Logger.getLogger(this.getClass()).info("APP INIT");
        initApp();
        play();
    }

    private void initApp() throws Exception {
        initScreens();
        initExtra();
        initDisplay();
    }

    private void initExtra() throws Exception {
        Logger.getLogger(this.getClass()).info("APP INIT EXTRA");
        menu.initMenu(this);
    }

    private void initDisplay() {
        Logger.getLogger(this.getClass()).info("APP INIT ENGINE");

        try {
            engine = new Engine(title);
            engine.setScreens(screens);
            appGameContainer = new AppGameContainer(engine);
            appGameContainer.setShowFPS(false);
            //appGameContainer.setFullscreen(true);
            appGameContainer.setDisplayMode(width, height, fullscreen);
            Logger.getLogger(this.getClass()).info("Display: " + width + "x" + height + " F:" + fullscreen);
            /*if (!fullscreen) {
                appGameContainer.setDisplayMode(width, height, false);
            } else {
                if (!OSUtils.isMac()) {
                    appGameContainer.setDisplayMode(width, height, true);
                } else {
                    appGameContainer.setFullscreen(true);
                }
            }*/

        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
            System.exit(0);
        }
    }

    /*
     * Add and Initialise The Screens
     * 
     */
    private void initScreens() {
        Logger.getLogger(this.getClass()).info("APP INIT SCREENS");

        try {
            for (Map.Entry<Screen, ScreenInterface> entry : screens.entrySet()) {
                entry.getValue().setAppInterface(this);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal("Error Initialsing Screens", e);
            System.exit(0);
        }
    }

    private void play() throws SlickException {
        Logger.getLogger(this.getClass()).info("APP PLAY");
        this.taskExecutor.execute(this);
        processNextScreen(Screen.MAIN);
        Logger.getLogger(this.getClass()).info("APP INIT ENGINE START");
        appGameContainer.start();
        Logger.getLogger(this.getClass()).info("APP INIT ENGINE STARTED");
    }

    /**
     * Play The Screen.
     *
     * @param screen
     */
    private void playScreen(Screen screenEnum) {
        screen = getScreen(screenEnum);
        if (screen != null) {
            Logger.getLogger(this.getClass()).info("Show Screen: " + screen.getTitle());
            engine.setScreen(screen);
        } else {
            Logger.getLogger(this.getClass()).info("No Screen: " + screenEnum);

        }
    }

    private ScreenInterface getScreen(Screen screenEnum) {
        return screens.get(screenEnum);
    }

    private ScreenInterface getPlayingScreen() {
        return screen;
    }

    @Override
    public void processNextScreen(Screen screen) {
        screenQueue.clear();
        screenQueue.add(screen);
    }

    @Override
    public void run() {
        while (running) {
            Screen nextScreen = screenQueue.poll();
            if (nextScreen != null) {
                playScreen(nextScreen);
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void quit() {
        if (getPlayingScreen() != null) {
            //getPlayingScreen().stopPlaying();
        }
        running = false;
        System.exit(0);
    }

    /**
     * @param taskExecutor the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @param screens the screens to set
     */
    public void setScreens(HashMap<Screen, ScreenInterface> screens) {
        this.screens = screens;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @param fullscreen the fullscreen to set
     */
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    @Override
    public int getWidth() {
        return appGameContainer.getWidth();
    }

    @Override
    public int getHeight() {
        return appGameContainer.getHeight();
    }

    @Override
    public ScreenInterface getCurrentScreenInterface() {
        return screen;
    }
}
