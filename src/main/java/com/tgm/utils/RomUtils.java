/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import com.tgm.data.rom.goodgen.GoodGenRom;
import com.tgm.data.rom.Rom;
import com.tgm.parsers.GoodGenRomParser;

/**
 *
 * @author christopher
 */
public class RomUtils {

    public static Rom parseRom(Rom rom) {
        switch (rom.getRomType()) {
            case UNKNOWN: {
                return rom;
            }
            case GOOD_GEN:
            case GOOD_GEN_3_0:
            case GOOD_GEN_3_2_1: {
                return GoodGenRomParser.parse((GoodGenRom)rom);
            }
        }
        return rom;
    }
    
    
    
    
    /*public static String findGameNameFromFileName(String fName) {
     String name = fName;
     StringBuilder sb = new StringBuilder();
     try {
     String words[] = StringUtils.split(fName, " ");
     int i = 0;
     for (String string : words) {
     if (i==0){
     sb.append(string);
     } else {
                    
     }
                
     i++;
     if (i < words.length){
     sb.append(" ");
     }
                
     }
            
            
     return name;
     } finally {
     name = null;
     }


     }*/
}
