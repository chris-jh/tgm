/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.utils;

import com.tgm.resources.TgmResource;
import java.io.File;
import java.nio.file.Path;
import org.apache.log4j.Logger;

/**
 *
 * @author christopher
 */
public class ScreenUtils {
    
    public static Path readMedia(String media) {
        File f = new File(TgmResource.MEDIA + "/" + media);
        Logger.getLogger(ScreenUtils.class).info("LOAD RESOURCE: [" + TgmResource.MEDIA + "/" + media + "] = " + f.getAbsolutePath());
        return f.toPath();
    }
    
    public static String readMediaAsString(String media) {
        File f = new File(TgmResource.MEDIA + "/" + media);
        if (!f.exists()){
            f = new File(media);
        }
        Logger.getLogger(ScreenUtils.class).info("LOAD RESOURCE: [" + TgmResource.MEDIA + "/" + media + "] = " + f.getAbsolutePath());
        return f.getAbsolutePath();
    }
}
