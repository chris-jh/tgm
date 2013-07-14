/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene;

import com.tgm.App;
import com.tgm.interfaces.AppInterface;
import com.tgm.scanner.PlatformScanner;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;
import org.jsfml.window.event.Event;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class ScanForGamesScene extends AbstractScene {

    @Autowired
    private PlatformScanner scanner;
    private Text startText = new Text("Start...", App.fonts.getFreeSansFont());

    int leftMargin =0;
    {
        sceneName = "Scan For Games";
    }

    @Override
    public void initialize(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        screenWidth = appInterface.getRenderTarget().getSize().x;
        screenHeight = appInterface.getRenderTarget().getSize().y;
        leftMargin = screenWidth /3;
        
        startText.setStyle(Text.BOLD);
        startText.setColor(new Color(255, 255, 255, 192));
        startText.setCharacterSize(12);
        startText.setPosition(leftMargin, 100);
    }

    @Override
    public void reset() {
    }

    @Override
    public void handleEvent(Event event) {
        App.menu.menuHandleEvent(event, this);
        switch (event.type) {
            case CLOSED: {
                quit();
                break;
            }
        }
    }

    @Override
    public void update(float dt) {
        App.menu.update();
    }

    @Override
    public void render() {
        App.menu.render();
        appInterface.getRenderTarget().draw(startText);
    }

    /**
     * @return the scanner
     */
    public PlatformScanner getScanner() {
        return scanner;
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(PlatformScanner scanner) {
        this.scanner = scanner;
    }
}
