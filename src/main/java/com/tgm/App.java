package com.tgm;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;
import org.jsfml.internal.SFMLNative;
import org.jsfml.system.Clock;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

/**
 * Hello world!
 *
 */
public class App implements AppInterface, InitializingBean, Runnable {

    private final static int FPS = 60;
    private final static float FRAME_TIME = 1.0f / (float) FPS;
    private RenderWindow window;
    @Value(value = "${tgm.width:800}")
    private int width;
    @Value(value = "${tgm.height:600}")
    private int height;    
    @Value(value = "${tgm.fullscreen:false}")
    private boolean fullscreen;
    private String title = "TGM";
    private HashMap<SceneEnum, SceneInterface> scenes = new HashMap<SceneEnum, SceneInterface>();
    private Clock frameClock = new Clock();
    private TaskExecutor taskExecutor;
    private boolean running = true;
    private ConcurrentLinkedQueue<SceneEnum> sceneQueue = new ConcurrentLinkedQueue<SceneEnum>();

    public App() {
    }

    public void init(){
        initApp();
        play();
    }
    
    public void afterPropertiesSet() throws Exception {
        //Logger.getLogger(this.getClass()).info("SCREEN: "+width+","+height+" FULLSCREEN: "+fullscreen);
        
    }

    private void initApp() {
        initSFML();
        initScreen();
        initScenes();
    }

    private void initSFML() {
        //Try to load native libs.
        SFMLNative.loadNativeLibraries();

        //If we made it here, things are looking good!
        Logger.getLogger(this.getClass()).info("SFMLNative.loadNativeLibraries() succeeded.");
        Logger.getLogger(this.getClass()).info("");

        //Some SFML facts
        {

            Logger.getLogger(this.getClass()).info("Detecting basic SFML facts ...");
            Logger.getLogger(this.getClass()).info("Texture.getMaximumSize(): " + Texture.getMaximumSize());
            Logger.getLogger(this.getClass()).info("Shader.isAvailable(): " + Shader.isAvailable());
            Logger.getLogger(this.getClass()).info("");
        }
    }

    private void initScreen() {
        Logger.getLogger(this.getClass()).info("Init Screen...");
        //Set OpenGL 3.0 to be the desired version
        ContextSettings settings = new ContextSettings(2, 0);

        //Create a render window
        window = new RenderWindow(new VideoMode(width, height), title, (fullscreen ? Window.FULLSCREEN : Window.DEFAULT), settings);
    }

    /*
     * Add and Initialise The Scenes
     * 
     */
    private void initScenes() {
        try {
            for (Map.Entry<SceneEnum, SceneInterface> entry : scenes.entrySet()) {
                entry.getValue().initialize(this);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal("Error Initialsing Screens", e);
            System.exit(0);
        }
    }

    private void play() {
        this.taskExecutor.execute(this);
        processNextScene(SceneEnum.MAIN);
        processScene();
    }

    /**
     * Play The Scene.
     * @param scene
     */
    private void playScene(SceneEnum sceneEnum) {
        if (getPlayingScene() == null) {
            SceneInterface scene = getScene(sceneEnum);
            Logger.getLogger(this.getClass()).info("Playing Scene: " + scene.getSceneName());
            scene.play();
        } else {
            Logger.getLogger(this.getClass()).info("Scene [" + getPlayingScene().getSceneName() + "] is already playing....");
        }
    }

    private SceneInterface getScene(SceneEnum sceneEnum) {
        return scenes.get(sceneEnum);
    }

    private SceneInterface getPlayingScene() {
        for (Map.Entry<SceneEnum, SceneInterface> entry : scenes.entrySet()) {
            if (entry.getValue().isPlaying()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Clock getClock() {
        return frameClock;
    }

    public RenderTarget getRenderTarget() {
        return window;
    }

    public RenderWindow getRenderWindow() {
        return window;
    }

    public void processNextScene(SceneEnum scene) {
        sceneQueue.clear();
        sceneQueue.add(scene);
    }

    private void processScene() {
        while (running) {
            SceneInterface scene = getPlayingScene();

            for (Event event = getRenderWindow().pollEvent(); event != null; event = getRenderWindow().pollEvent()) {
                if (scene != null) {
                    scene.handleEvent(event);
                }
            }

            if (scene != null) {
                //Update the scene
                scene.update(getClock().restart().asSeconds());

                //Clear the window
                getRenderWindow().clear();

                //Render the scene
                scene.render();

                //Display the scene
                getRenderWindow().display();
            } else {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        }
    }

    public void run() {
        while (running) {
            SceneEnum nextScene = sceneQueue.poll();
            if (nextScene != null) {
                playScene(nextScene);
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void quit() {
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
     * @param scenes the scenes to set
     */
    public void setScenes(HashMap<SceneEnum, SceneInterface> scenes) {
        this.scenes = scenes;
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
}
