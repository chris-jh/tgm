/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.enums;

/**
 *
 * @author christopher
 */
public enum Platform {

    PC,
    SEGA_32X("32x", "zip"),
    SEGA_CD("iso"),
    SEGA_GAME_GEAR("gg", "zip"),
    SEGA_MASTER_SYSTEM("ms", "zip"),
    SEGA_GENESIS("gen", "zip", "md", "smd", "bin"),
    SEGA_MEGA_DRIVE("gen", "zip", "md", "smd", "bin"),
    SEGA_SATURN("ss", "zip");

    Platform(String... test) {
        fileTypes = test;
    }
    String fileTypes[];

    public String[] getFileTypes() {
        return fileTypes;
    }
}
