package com.tgm;

import com.tgm.enums.SceneEnum;
import com.tgm.interfaces.AppInterface;
import com.tgm.interfaces.SceneInterface;
import com.tgm.scene.MainScene;
import com.tgm.scene.NextTestScene;
import java.util.HashMap;
import java.util.Map;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;
import org.jsfml.internal.SFMLNative;
import org.jsfml.system.Clock;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;

/**
 * Hello world!
 *
 */
public class App implements AppInterface {

    private final static int FPS = 60;
    private final static float FRAME_TIME = 1.0f / (float) FPS;
    private RenderWindow window;
    private int width = 800, height = 600;
    private boolean fullscreen = true;
    private String title = "TGM";
    private HashMap<SceneEnum, SceneInterface> scenes = new HashMap<SceneEnum, SceneInterface>();
    private Clock frameClock = new Clock();

    public static void main(String[] args) {
        try {
            App a = new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public App() {
        init();
        play();
    }

    private void init() {
        initSFML();
        initScreen();
        initScenes();
    }

    private void initSFML() {
        //Try to load native libs.
        SFMLNative.loadNativeLibraries();

        //If we made it here, things are looking good!
        System.out.println("SFMLNative.loadNativeLibraries() succeeded.");
        System.out.println();

        //Some SFML facts
        {
            System.out.println("Detecting basic SFML facts ...");
            System.out.println("Texture.getMaximumSize(): " + Texture.getMaximumSize());
            System.out.println("Shader.isAvailable(): " + Shader.isAvailable());
            System.out.println();
        }
    }

    private void initScreen() {
        System.out.println("Init Screen...");
        //Set OpenGL 3.0 to be the desired version
        ContextSettings settings = new ContextSettings(3, 0);

        //Create a render window
        window = new RenderWindow(new VideoMode(width, height), title, (fullscreen ? Window.FULLSCREEN : Window.DEFAULT), settings);
    }

    /*
     * Add and Initialise The Scenes
     * 
     */
    private void initScenes() {
        try {
            scenes.put(SceneEnum.MAIN, new MainScene(this));
            scenes.put(SceneEnum.NEXT, new NextTestScene(this));

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void play() {
        playScene(SceneEnum.MAIN);
    }

    /**
     * Play The Scene.
     *
     * A bit better........
     *
     * @param scene
     */
    private void playScene(SceneEnum sceneEnum) {
        if (getPlayingScene() == null) {
            SceneInterface scene = getScene(sceneEnum);
            System.out.println("Playing Scene: " + scene.getSceneName());

            scene.play();

            if (scene.getNextScene() != null) {
                playScene(scene.getNextScene());
            }
        } else {
            System.out.println("Scene [" + getPlayingScene().getSceneName() + "] is already playing....");
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
}
