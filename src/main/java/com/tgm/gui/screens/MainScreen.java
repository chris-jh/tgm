/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.screens;

import com.tgm.Boot;
import com.tgm.gui.enums.Command;
import com.tgm.gui.panels.MenuSidePanel;
import com.tgm.interfaces.ScannerInterface;
import org.apache.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class MainScreen extends AbstractScreen {

    @Autowired
    private MenuSidePanel menu;
    @Autowired
    private ScannerInterface scanner;

    {
        screenName = "Main Scene";
    }

    @Override
    public void configure() {
        menu.setAppInterface(appInterface);
        menu.setParentScreen(this);
        menu.setSize(appInterface.getWidth() / 4.1f, appInterface.getHeight());
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        if (!menu.isInitialised()) {
            menu.init(gc);
        }
        initBackground();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        menu.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        drawBackground();
        menu.render(gc, grphcs);
    }

    @Override
    public void reset() {
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(ScannerInterface scanner) {
        this.scanner = scanner;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(MenuSidePanel menu) {
        this.menu = menu;
    }

    @Override
    public void command(Command command) {
        Logger.getLogger(this.getClass()).info("SCREEN COMMAND: " + command);

        switch (command) {
            case SCAN_FOR_GAMES: {
                scanner.scan();
                break;
            }
            case QUIT: {
                Boot.exit(0);
            }
        }
        Logger.getLogger(this.getClass()).info("SCREEN COMMAND: " + command + " DONE");

    }
}
