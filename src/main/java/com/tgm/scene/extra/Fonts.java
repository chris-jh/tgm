/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scene.extra;

import com.tgm.utils.SceneUtils;
import org.jsfml.graphics.Font;

/**
 *
 * @author christopher
 */
public class Fonts {

    private final Font freeSansFont = new Font();

    public void init() throws Exception {
        freeSansFont.loadFromFile(SceneUtils.readMedia("fonts/FreeSans.ttf"));
    }
    
    public Font getFreeSansFont(){
        return freeSansFont;
    }

}
