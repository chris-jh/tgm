/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import com.tgm.gui.utils.ScreenUtils;
import org.apache.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class Image extends AbstractComponent {

    private String path;
    private boolean update = false;
    org.newdawn.slick.Image background;
    private Color fadeColor;
    private boolean fade = false;
    private boolean fadeIn = false;
    private boolean fadeOut = false;
    private boolean fadeUpdate = false;
    private long fadeSpeed = 25;
    private long fadeTime = 0;
    private float fadeAlphaIndex = 0.5f;
    private float fadeAlpha = 0.5f;
    private int fadePos = 0;
    private boolean fixedRatio = false;
    private float fixedHeight = 0;
    private float fixedY = 0;

    public Image(String id, String path, float x, float y, float width, float height) {
        this.id = id;
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fixedHeight = height;
        this.fixedY = 0;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        if (!path.equals("")) {
            background = new org.newdawn.slick.Image(ScreenUtils.readMediaAsString(path));
            updateFixedHeight();
        }
        initialised = true;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (isVisible()) {
            if (fadeUpdate) {
                if (fadePos == 0) {
                    fadeOut();
                    fadePos = 1;
                } else if (fadePos == 1) {
                    if (!fade) {
                        update = true;
                        fadePos = 2;
                    }
                } else if (fadePos == 2) {
                    fadeIn();
                    fadePos = 3;
                } else if (fadePos == 3) {
                    if (!fade) {
                        fadePos = 0;
                        fadeUpdate = false;
                    }
                }

            }

            if (update) {
                if (!path.equals("")) {
                    background = new org.newdawn.slick.Image(ScreenUtils.readMediaAsString(path));
                    updateFixedHeight();

                }
                update = false;
            }
            if (fade) {
                if (System.currentTimeMillis() > fadeTime) {
                    fadeTime = System.currentTimeMillis() + fadeSpeed;
                    if (fadeOut) {
                        fadeAlphaIndex += 0.05;
                        if (fadeAlphaIndex > 1.0) {
                            fadeAlphaIndex = 1.0f;
                            fade = false;
                            fadeOut = false;
                        }
                    } else if (fadeIn) {
                        fadeAlphaIndex -= 0.05;
                        if (fadeAlphaIndex < fadeAlpha) {
                            fadeAlphaIndex = fadeAlpha;
                            fade = false;
                            fadeIn = false;
                        }
                    }
                }

            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (isVisible()) {
            if (background != null) {
                background.draw(getX(), getY() + fixedY, getWidth(), fixedHeight);

                if (fadeColor != null) {
                    Color old = g.getColor();
                    fadeColor.a = fadeAlphaIndex;
                    g.setColor(fadeColor);
                    g.fillRect(getX(), getY()+fixedY, getWidth(), fixedHeight);
                    g.setColor(old);
                }
            }
        }
    }

    private void updateFixedHeight() {
        if (fixedRatio) {
            if (background != null) {
                float ff = 1.0f;
                if (background.getWidth() < background.getHeight()) {
                    ff = (float)background.getWidth() / (float)background.getHeight();
                } else {
                    ff = (float)background.getHeight() / (float)background.getWidth();
                }

                fixedHeight = getWidth() * ff;
                fixedY = (getHeight() - fixedHeight) / 2f;                
            }
        }
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        if (!this.path.equals(path)) {
            this.path = path;
            update = true;
        }
    }

    public void setPathAndFade(String path) {
        if (!this.path.equals(path)) {
            this.path = path;
            fadePos = 0;
            fadeUpdate = true;
        }
    }

    /**
     * @param filterColor the filterColor to set
     */
    public void setFadeColor(Color fadeColor) {
        this.fadeColor = fadeColor;
    }

    /**
     * @return the fade
     */
    public boolean isFade() {
        return fade;
    }

    public void fadeOut() {
        if ((!this.fadeOut) && (!this.fade)) {
            this.fadeOut = true;
            this.fade = true;
        }
    }

    public void fadeIn() {
        if ((!this.fadeIn) && (!this.fade)) {
            this.fadeIn = true;
            this.fade = true;
        }
    }

    /**
     * @param fadeAplha the fadeAplha to set
     */
    public void setFadeAlpha(float fadeAlpha) {
        this.fadeAlpha = fadeAlpha;
        this.fadeAlphaIndex = this.fadeAlpha;
    }

    /**
     * @param fixedRatio the fixedRatio to set
     */
    public void setFixedRatio(boolean fixedRatio) {
        this.fixedRatio = fixedRatio;
        updateFixedHeight();
    }
}
