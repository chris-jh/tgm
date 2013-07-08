package com.tgm;

import com.tgm.interfaces.SceneInterface;
import com.tgm.scene.MainScene;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;
import org.jsfml.internal.SFMLNative;
import org.jsfml.system.Clock;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;

/**
 * Hello world!
 *
 */
public class App {

    private final static int FPS = 60;
    private final static float FRAME_TIME = 1.0f / (float) FPS;
    private RenderWindow window;
    private SceneInterface currentScene;
    private MainScene mainScene;
    private int width=800, height=600;
    private boolean fullscreen = true;
    private String title = "TGM";
    

    public static void main(String[] args) {
        try {
            App a = new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public App() {
        init();
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

    private void initScenes() {
        mainScene = new MainScene();

        play(mainScene);
    }

    /**
     * This will need total re-write as not right really. but ok for test.
     * @param scene 
     */
    public void play(SceneInterface scene) {
        if (currentScene == null) {
            currentScene = scene;
            System.out.println("Playing Scene: " + currentScene.getSceneName());

            //Attempt to initialize the scene
            try {
                scene.initialize(window);
            } catch (Exception ex) {
                //Scene initialization failed, exit
                ex.printStackTrace();
                return;
            }

            //Create a clock for measuring frame time
            Clock frameClock = new Clock();

            //Enter main loop
            while (!scene.isDone()) {
                //Delegate events to the scene
                for (Event event = window.pollEvent(); event != null; event = window.pollEvent()) {
                    scene.handleEvent(event);
                }

                //Update the scene
                scene.update(frameClock.restart().asSeconds());

                //Clear the window
                window.clear();

                //Render the scene
                scene.render(window);

                //Display the scene
                window.display();
            }
            
        } else {
            System.out.println("Scene [" + currentScene.getSceneName() + "] is already playing....");
        }
    }
}
