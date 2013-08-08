/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.rom;

import com.tgm.enums.RomType;

/**
 *
 * @author christopher
 */
public class Rom {
    
    private RomType romType;
    
    private String fileName;
    private String path;
    //
    private String name;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
        this.path = path;
    }

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
     * @return the romType
     */
    public RomType getRomType() {
        return romType;
    }

    /**
     * @param romType the romType to set
     */
    public void setRomType(RomType romType) {
        this.romType = romType;
    }
    
}
