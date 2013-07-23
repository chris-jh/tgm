/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.data.entity.GameEntity;
import com.tgm.gui.components.Image;
import com.tgm.gui.components.Label;
import com.tgm.gui.components.Panel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class LibraryPanel extends Panel {

    public static final int TOP = 0;
    public static final int MID = 1;
    public static final int BOT = 2;
    private Color textColour = Color.white;
    private String title = "";
    private int vAlign = TOP;
    private List<GameEntity> gameList = new ArrayList<GameEntity>();
    private HashMap<Integer, GamePanel> gamePanelMap = new HashMap<Integer, GamePanel>();
    //----------------------------
    private int gridWidth = 10;
    float textMarginOffset = 0;
    float heighOffset = 0;
    float titleHeightPercentage = 6f;
    float textHeightPercentage = 1.17f;
    float normalWidth;
    float normalHeight;
    float textWidth;
    float titleHeight;
    float textHeight;
    float textMarginTop;
    float textMarginLeft;
    int titleFontSize;
    String barImg = "";
    float gameWidth;
    float gameMargin;
    int displayed;
    //--------------------------------------
    private int gameSelectedIndex = 0;
    private Label gameTitleLabel;
    private boolean newGameSelected = false;
    //--------------------------------------

    public LibraryPanel(String id, String title, int vAlign, float x, float y, float width, float height) {
        super(id, x, y, width, height);
        this.title = title;
        this.vAlign = vAlign;

        //--------------------


        normalWidth = this.getWidth();
        normalHeight = this.getHeight();

        textWidth = normalWidth / 1.03f;
        titleHeight = normalHeight / titleHeightPercentage;
        textHeight = titleHeight / textHeightPercentage;

        switch (vAlign) {
            case TOP: {
                barImg = "images/barTop.png";
                textMarginOffset = -(titleHeight / 18);
                heighOffset = (titleHeight / 4.5f);
                break;
            }
            case MID: {
                barImg = "images/bar.png";
                textMarginOffset = (titleHeight / 9.0f);
                heighOffset = (titleHeight / 9.0f);
                break;
            }
            case BOT: {
                barImg = "images/barBottom.png";
                textMarginOffset = (titleHeight / 4.5f);
                heighOffset = (titleHeight / 18f);
                break;
            }
            default: {
                barImg = "images/bar.png";
                textMarginOffset = 0f;
                break;
            }
        }

        textMarginTop = ((titleHeight - textHeight) / 2f) + textMarginOffset;
        textMarginLeft = (normalWidth - textWidth) / 2f;

        titleFontSize = (int) (textHeight / 1.75f);

        setHeight(normalHeight - heighOffset);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initLibrary(gc);
        super.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        super.update(gc, i);
        if (newGameSelected) {
            newGameSelected = false;
            float gameX = gameMargin;
            if (gameSelectedIndex < (gridWidth - displayed)) {
                gameX -= gameSelectedIndex * gameWidth;
            } else {
                gameX -= (gridWidth - displayed) * gameWidth;
            }

            for (Map.Entry<Integer, GamePanel> entry : gamePanelMap.entrySet()) {
                entry.getValue().setMoveToX(gameX);
                gameX += gameWidth;
                entry.getValue().setSelected(false);
            }
        }

        for (Map.Entry<Integer, GamePanel> entry : gamePanelMap.entrySet()) {
            entry.getValue().setSelected(false);
        }

        if (focused) {
            gamePanelMap.get(gameSelectedIndex).setSelected(true);
            gameTitleLabel.setText(gamePanelMap.get(gameSelectedIndex).getGameName());
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        super.render(gc, g);
    }

    private void initLibrary(GameContainer gc) {

        add(new Image("barTitleBackground", barImg, 0, 0, normalWidth, titleHeight));
        add(new Label(title + "Label", title, textMarginLeft, textMarginTop, textWidth, textHeight, Label.RIGHT, "Arial", Font.BOLD, titleFontSize, textColour, true));
        gameTitleLabel = (Label) add(new Label(title + "GameLabel", "", textMarginLeft, textMarginTop, textWidth, textHeight, Label.LEFT, "Arial", Font.BOLD, titleFontSize, textColour, true));



        float gWidth = (normalWidth / 1.02f);
        float gHeight = (normalHeight - titleHeight);

        gameMargin = (normalWidth - gWidth) / 2f;

        float sR = ((float) appInterface.getWidth() / (float) appInterface.getHeight());
        float sW = (float) appInterface.getWidth() / sR;

        float pp = 1.3f / sR;

        float sWW = (float) gWidth / 6.02f;
        //displayed = ((int) ((sW / 120) * pp));
        displayed = (int) MathUtils.round(((sW / 120) * pp), 0);

        gameWidth = gWidth / displayed;
        float gameHeight = gHeight / 1.05f;

        float gameY = (titleHeight + textMarginOffset) / 1.02f;
        float gameX = gameMargin;
        for (int i = 0; i < gridWidth; i++) {
            addGamePanel(i, gameX, gameY, gameWidth, gameHeight);
            gameX += gameWidth;
        }
    }

    public void clear() {
        gameList.clear();
        for (Map.Entry<Integer, GamePanel> entry : gamePanelMap.entrySet()) {
            entry.getValue().setVisible(false);
        }
        //gamePanelMap.clear();
    }

    private void addGamePanel(int i, float gx, float gy, float gw, float gh) {
        //gamePanelMap.put(i, (GamePanel) add(new GamePanel("game" + i, "Game 1000000" + i, "images/test/gameTest" + (i + 1) + ".jpg", gx, gy, gw, gh)));
        gamePanelMap.put(i, (GamePanel) add(new GamePanel("game" + i, "", "", gx, gy, gw, gh)));
        gamePanelMap.get(i).setVisible(false);
    }

    public void addGame(GameEntity gameEntity) {
        gameList.add(gameEntity);
    }

    public void addGame(Collection<GameEntity> gameEntityCollection) {
        gameList.addAll(gameEntityCollection);
    }
    
    public void update(){
        for (int i = 0; i < gameList.size(); i++) {
            Logger.getLogger(this.getClass()).info(gameList.get(i).getName()+" - "+gameList.get(i).getImageBoxArtPath());
            gamePanelMap.get(i).setGameEntity(gameList.get(i));
            gamePanelMap.get(i).setVisible(true);
        }
    }

    /**
     * @return the gameSelectedIndex
     */
    public int getGameSelectedIndex() {
        return gameSelectedIndex;
    }

    /**
     * @param gameSelectedIndex the gameSelectedIndex to set
     */
    public void setGameSelectedIndex(int gameSelectedIndex) {
        if (this.gameSelectedIndex != gameSelectedIndex) {
            this.gameSelectedIndex = gameSelectedIndex;
            newGameSelected = true;
        }

    }

    public GamePanel getCurrentGame() {
        return gamePanelMap.get(gameSelectedIndex);
    }

    /**
     * @return the gridWidth
     */
    public int getGridWidth() {
        return gridWidth;
    }
}
