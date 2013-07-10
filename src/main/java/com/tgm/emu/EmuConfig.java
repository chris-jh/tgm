/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.emu;

import java.util.List;

/**
 *
 * @author christopher
 */
public class EmuConfig {
    private String name;
    private String emuPath;
    private String emuArgs;
    private List<String> romPath;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the emuPath
     */
    public String getEmuPath() {
        return emuPath;
    }

    /**
     * @param emuPath the emuPath to set
     */
    public void setEmuPath(String emuPath) {
        this.emuPath = emuPath;
    }

    /**
     * @return the emuArgs
     */
    public String getEmuArgs() {
        return emuArgs;
    }

    /**
     * @param emuArgs the emuArgs to set
     */
    public void setEmuArgs(String emuArgs) {
        this.emuArgs = emuArgs;
    }

    /**
     * @return the romPath
     */
    public List<String> getRomPath() {
        return romPath;
    }

    /**
     * @param romPath the romPath to set
     */
    public void setRomPath(List<String> romPath) {
        this.romPath = romPath;
    }
}
