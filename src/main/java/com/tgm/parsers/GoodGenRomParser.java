/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.parsers;

import com.tgm.data.rom.goodgen.GoodGenRom;
import com.tgm.data.rom.goodgen.GoodRom;

/**
 *
 * @author christopher
 */
public class GoodGenRomParser extends GoodRomParser {

    //Ayrton Senna's Super Monaco GP II (JE) [!].gen 
    //Micro Machines - Turbo Tournament '96 (V1.1) (E) (J-Cart) [c][!].gen
    //PGA Tour Golf II (UE) (REV00) [c][!].gen
    //Pete Sampras Tennis (UE) (REV00) (J-Cart) [c][!].gen
    //Quack Shot Starring Donald Duck (W) (REV01) [a1][c][!].gen
    //Sega Sports 1 (Wimbledon, Ult.Soccer, Super Monaco) (E) [!].gen
    
    public static GoodGenRom parse(GoodGenRom rom) {
        rom = (GoodGenRom)parse((GoodRom)rom);
        return rom;
    }
    
    
    
}
