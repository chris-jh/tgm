/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.gui.components.Image;
import com.tgm.gui.components.Panel;
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
    private LibraryPanel recentlyPlayedPanel;
    private LibraryPanel recentlyAddedPanel;
    private LibraryPanel topTenPanel;
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
        topTenPanel.setFocused(false);

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
            gc.getInput().clearKeyPressedRecord();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        super.render(gc, g);
    }

    private void initGamePanel(GameContainer gc) {
        float panelX = 0;
        float panelWidth = this.getWidth();
        float panelHeight = this.getHeight() / 3f;

        artImage = (Image) add(new Image("artImage", artList.get(0), 0, 0, this.getWidth(), this.getHeight()));
        artImage.setFadeColor(new Color(255, 255, 255, 255));
        artImage.setFadeAlpha(0.4f);
        artImage.setFixedRatio(true);

        
        recentlyPlayedPanel = (LibraryPanel) add(new LibraryPanel("recentlyPlayedPanel", "Recently Played", LibraryPanel.TOP, panelX, 0, panelWidth, panelHeight));
        recentlyAddedPanel = (LibraryPanel) add(new LibraryPanel("recentlyAddedPanel", "Recently Added", LibraryPanel.MID, panelX, recentlyPlayedPanel.getHeight(), panelWidth, panelHeight));
        topTenPanel = (LibraryPanel) add(new LibraryPanel("topTenPanel", "Top Ten", LibraryPanel.MID, panelX, recentlyPlayedPanel.getHeight() + recentlyAddedPanel.getHeight(), panelWidth, panelHeight));
        currentPanel = recentlyPlayedPanel;
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
        } else if (librarySelected == 2) {
            currentPanel = topTenPanel;
        }
    }

    private void gameDown() {
        librarySelected++;
        if (librarySelected > 3) {
            librarySelected = 2;
        }

        if (librarySelected == 0) {
            currentPanel = recentlyPlayedPanel;
        } else if (librarySelected == 1) {
            currentPanel = recentlyAddedPanel;
        } else if (librarySelected == 2) {
            currentPanel = topTenPanel;
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
