/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.panels;

import com.tgm.enums.Platform;
import com.tgm.gui.enums.Screen;
import com.tgm.gui.interfaces.AppInterface;
import com.tgm.gui.interfaces.PanelInterface;
import com.tgm.gui.slick.Label;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author christopher
 */
public class MenuSidePanel implements PanelInterface {

    private Label menuTitle;
    private float textSpinSpeed = 0.002f;
    private float textScaleValue = 1.0f;
    private boolean pause = false;
    private Long pauseTime = 0l;
    private int dir = 0;
    //private Text title2;
    AppInterface appInterface;
    private final List<Label> menu = new ArrayList<Label>();
    private final int start = 80;
    private final int insert = 25;
    private final int margin = 7;
    private int menuSelectionIndex = 1;
    private float menuSelectionPosition = 1;
    private float menuSelectionMovement = 1;
    private int maxNumberMenuItemsDisplayed = 5;
    private int menuDisplayedIndex = 0;
    //-----------------
    Rectangle menuSelectionBackground;// = new Rectangle(1, 1, healthWidth, healthHeight);
    ShapeFill menuSelectionBackgroundFill;// = new GradientFill(0, healthHeight / 2, Color.red, healthWidth, healthHeight - 1, Color.orange, true);
    Circle menuSelectionBackgroundUp;
    Circle menuSelectionBackgroundDown;

    public void initMenu(AppInterface appInterface) throws Exception {
        this.appInterface = appInterface;
        initTitle();
        initMenu();
    }

    private void initTitle() {
        menuTitle = new Label("The Games Menu", 10, 10);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 22));
    }

    private void initMenu() throws Exception {
        Logger.getLogger(this.getClass()).info("initMenu 1");
        int h = start;
        menu.add(createText("Scan For Games", h));
        for (Platform platform : Platform.values()) {
            h += insert;

            menu.add(createText(WordUtils.capitalize(StringUtils.replace(platform.name(), "_", " ").toLowerCase()), h)); //Needs Formatting
        }
        h += insert;
        menu.add(createText("Quit", h));
        Logger.getLogger(this.getClass()).info("initMenu 2");


        menuSelectionIndex = 0;
        menuSelectionPosition = start - 2;
        menuSelectionMovement = menuSelectionPosition;
        menuDisplayedIndex = 0;

        Logger.getLogger(this.getClass()).info("initMenu 3");


        /*
        
         menuSelectionBackgroundUp = new CircleShape(5, 3);
         menuSelectionBackgroundUp.setPosition(5, menuSelectionPosition - 5);
         menuSelectionBackgroundUp.setFillColor(Color.BLUE);
         menuSelectionBackgroundUp.setOutlineColor(Color.WHITE);
         menuSelectionBackgroundUp.setOutlineThickness(1.0f);

         menuSelectionBackgroundDown = new CircleShape(6, 3);
         menuSelectionBackgroundDown.setPosition(5, menuSelectionPosition - 5);
         menuSelectionBackgroundDown.setFillColor(Color.BLUE);
         menuSelectionBackgroundDown.setOutlineColor(Color.WHITE);
         menuSelectionBackgroundDown.setOutlineThickness(1.0f);
         menuSelectionBackgroundDown.setRotation(180f);*/

    }

    private Label createText(String text, int h) {
        Label l = new Label(text, margin, h);
        l.setFont(new Font("Arial", Font.BOLD, 16));
        return l;
    }

    private void updateTitle() {
        if (pause) {
            if (System.currentTimeMillis() > pauseTime) {
                pause = false;
            }
        } else {
            if (dir == 0) {
                textScaleValue += textSpinSpeed;
                if (textScaleValue > 1.0) {
                    dir = 1;
                    pause = true;
                    pauseTime = System.currentTimeMillis() + 1000;
                }
            } else if (dir == 1) {
                textScaleValue -= textSpinSpeed;
                if (textScaleValue < -1.0) {
                    dir = 0;
                }
            }
        }
        //title.setScale(textScaleValue, title.getScale().y);
    }

    private void menuUp() {
        if ((menuSelectionIndex - 1) >= 0) {
            menuSelectionIndex -= 1;
        }

        if ((menuDisplayedIndex - 1) >= 0) {
            menuDisplayedIndex--;
        }

        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);


    }

    private void menuDown() {
        if ((menuSelectionIndex + 1) < menu.size()) {
            menuSelectionIndex += 1;
        }

        if (menuSelectionIndex >= maxNumberMenuItemsDisplayed) {
            if ((menuDisplayedIndex + maxNumberMenuItemsDisplayed) < menu.size()) {
                menuDisplayedIndex++;
            }
        }
        Logger.getLogger(this.getClass()).info("POSITION: " + menuSelectionIndex);
    }

    private void updateMenuSelection() {
        menuSelectionPosition = (start - 2) + ((menuSelectionIndex < maxNumberMenuItemsDisplayed) ? menuSelectionIndex * insert : (maxNumberMenuItemsDisplayed - 1) * insert);
        if (menuSelectionPosition < menuSelectionMovement) {
            menuSelectionMovement -= 1;
        }
        if (menuSelectionPosition > menuSelectionMovement) {
            menuSelectionMovement += 1;
        }

        menuSelectionBackground.setLocation(5, menuSelectionMovement);
        menuSelectionBackgroundUp.setLocation((menuSelectionBackground.getWidth() / 2), menuSelectionMovement - 9);
        menuSelectionBackgroundDown.setLocation((menuSelectionBackground.getWidth() / 2), menuSelectionMovement + 9);


        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            float p = start + (i * insert);
            menu.get(i + menuDisplayedIndex).setLocation(margin, p);
        }
    }

    private void renderTitle(Graphics g) {
        if (menuTitle.getTrueTypeFont() != null) {
            menuTitle.getTrueTypeFont().drawString(menuTitle.getX(), menuTitle.getY(), menuTitle.getText());
        }
        //g.drawString(menuTitle, 5, 10);
    }

    private void renderMenu(Graphics g) {
        if (menuSelectionIndex > 0) {
            g.fill(menuSelectionBackgroundUp, menuSelectionBackgroundFill);
        }
        if (menuSelectionIndex < menu.size() - 1) {
            g.fill(menuSelectionBackgroundDown, menuSelectionBackgroundFill);
        }

        g.fill(menuSelectionBackground, menuSelectionBackgroundFill);

        for (int i = 0; i < maxNumberMenuItemsDisplayed; i++) {
            if (i + menuDisplayedIndex < menu.size()) {
                Label l = menu.get(i + menuDisplayedIndex);
                if (l.getTrueTypeFont() != null) {
                    l.getTrueTypeFont().drawString(l.getX(), l.getY(), l.getText());
                } else {
                    g.drawString(l.getText(), l.getX(), l.getY());
                }
                //g.drawString(menu.get(i + menuDisplayedIndex).getText(), menu.get(i + menuDisplayedIndex).getX(), menu.get(i + menuDisplayedIndex).getY());
            }
        }

    }

    public List<Label> getMenu() {
        return menu;
    }

    private void menuSelected() {
        int quitIndex = menu.size() - 1;
        if (menuSelectionIndex == 0) {
            appInterface.getCurrentScreenInterface().updateNextScene(Screen.SCAN_FOR_GAMES);
        } else if (menuSelectionIndex == quitIndex) {
            appInterface.quit();
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        menuSelectionBackground = new Rectangle(5, menuSelectionPosition, appInterface.getWidth() / 4.1f, 20);
        menuSelectionBackgroundFill = new GradientFill(0, 10, Color.red, menuSelectionBackground.getWidth(), 19, Color.orange);

        menuSelectionBackgroundUp = new Circle(5, menuSelectionPosition - 5, 10);
        menuSelectionBackgroundDown = new Circle(5, menuSelectionPosition - 5, 10);

        menuTitle.setTrueTypeFont(new TrueTypeFont(menuTitle.getFont(), true));


        for (Label label : menu) {
            if (label.getFont() != null) {
                label.setTrueTypeFont(new TrueTypeFont(label.getFont(), true));;
            }
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        updateTitle();
        updateMenuSelection();

        if (gc.getInput().isKeyPressed(Input.KEY_RETURN)) {
            menuSelected();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            menuUp();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            menuDown();
        }

    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        renderTitle(grphcs);
        renderMenu(grphcs);
    }
}
