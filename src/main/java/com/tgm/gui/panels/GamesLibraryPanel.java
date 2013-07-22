/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.gui.components.Image;
import com.tgm.gui.components.Panel;
import com.tgm.gui.components.SolidBackground;
import com.tgm.gui.enums.Command;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class GamesLibraryPanel extends Panel {

    private Image artImage;
    private SolidBackground libraryBackgroundImage;
    private LibraryPanel recentlyPlayedPanel;
    private LibraryPanel recentlyAddedPanel;
    private LibraryPanel currentPanel;
    private int librarySelected = 0;
    private List<String> artList = new ArrayList<String>();
    private long artTimer = 0;
    private long artTime = 15000;
    private int artSelected = 0;
    private int artMax = 4;

    public GamesLibraryPanel(String id, float x, float y, float width, float height) {
        super(id, x, y, width, height);
        //

        artList.add("images/test/artTest1.jpg");
        artList.add("images/test/artTest2.jpg");
        artList.add("images/test/artTest3.jpg");
        artList.add("images/test/artTest4.jpg");
        artList.add("images/test/artTest5.jpg");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initGamePanel(gc);
        super.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        super.update(gc, i);

        updateArt();

        recentlyPlayedPanel.setFocused(false);
        recentlyAddedPanel.setFocused(false);

        if (focused) {
            currentPanel.setFocused(true);

            if (gc.getInput().isKeyPressed(Input.KEY_RETURN)) {
                gameSelected();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
                gameUp();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
                gameDown();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
                gameLeft();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
                gameRight();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
                parentScreen.command(Command.MENU_FOCUS);
            }
            gc.getInput().clearKeyPressedRecord();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        super.render(gc, g);
    }

    private void initGamePanel(GameContainer gc) {
        float panelX = 0;
        float panelY = 0;
        float panelWidth = this.getWidth();
        float panelHeight = this.getHeight() / 3f;
        float panelYMargin = panelHeight * 0.30f;

        panelY = ((this.getHeight() - panelHeight) - panelYMargin) / 3f;

        artImage = (Image) add(new Image("artImage", artList.get(0), 0 - this.getX(), 0 - this.getY(), this.getWidth() + this.getX(), this.getHeight() + this.getY()));
        artImage.setFadeColor(new Color(255, 255, 255, 255));
        artImage.setFadeAlpha(0.0f);

        libraryBackgroundImage = (SolidBackground) add(new SolidBackground("libraryBackgroundImage", new Color(255,255,255,170), panelX, panelY, panelWidth, panelHeight));

        recentlyPlayedPanel = (LibraryPanel) add(new LibraryPanel("recentlyPlayedPanel", "Recently Played", LibraryPanel.TOP, panelX, panelY, panelWidth, panelHeight));
        recentlyAddedPanel = (LibraryPanel) add(new LibraryPanel("recentlyAddedPanel", "Recently Added", LibraryPanel.TOP, panelX, panelY + recentlyPlayedPanel.getHeight(), panelWidth, panelHeight));
        currentPanel = recentlyPlayedPanel;

        libraryBackgroundImage.setHeight(recentlyPlayedPanel.getHeight() + recentlyAddedPanel.getHeight());
    }

    private void gameSelected() {
        Logger.getLogger(this.getClass()).info("PLAY GAME: " + currentPanel.getCurrentGame().getGameName());
    }

    private void gameUp() {
        librarySelected--;

        if (librarySelected < 0) {
            librarySelected = 0;
        }

        if (librarySelected == 0) {
            currentPanel = recentlyPlayedPanel;
        } else if (librarySelected == 1) {
            currentPanel = recentlyAddedPanel;
        }
    }

    private void gameDown() {
        librarySelected++;
        if (librarySelected > 2) {
            librarySelected = 1;
        }

        if (librarySelected == 0) {
            currentPanel = recentlyPlayedPanel;
        } else if (librarySelected == 1) {
            currentPanel = recentlyAddedPanel;
        }
    }

    private void gameLeft() {
        currentPanel.setGameSelectedIndex(currentPanel.getGameSelectedIndex() - 1);

        if (currentPanel.getGameSelectedIndex() < 0) {
            currentPanel.setGameSelectedIndex(0);
            parentScreen.command(Command.MENU_FOCUS);
        }
    }

    private void gameRight() {
        currentPanel.setGameSelectedIndex(currentPanel.getGameSelectedIndex() + 1);

        if (currentPanel.getGameSelectedIndex() > currentPanel.getGridWidth() - 1) {
            currentPanel.setGameSelectedIndex(currentPanel.getGridWidth() - 1);
        }
    }
    long timer1 = 0;

    private void updateArt() {
        if (System.currentTimeMillis() > artTimer) {
            artTimer = System.currentTimeMillis() + artTime;
            artSelected++;
            if (artSelected > artMax - 1) {
                artSelected = 0;
            }
            artImage.setPathAndFade(artList.get(artSelected));
        }
    }
}
