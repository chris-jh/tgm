/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.rom.goodgen;

/**
 *
 * @author christopher
 */
public class GoodGenRom extends GoodRom {
    
    private boolean japan;
    private boolean usa;
    private boolean ntscOnly;
    private String countries;
    private boolean palOnly;
    private boolean nonUsa;
    private boolean checkSum;
    private boolean basCheckSum;

    /**
     * @return the japan
     */
    public boolean isJapan() {
        return japan;
    }

    /**
     * @param japan the japan to set
     */
    public void setJapan(boolean japan) {
        this.japan = japan;
    }

    /**
     * @return the usa
     */
    public boolean isUsa() {
        return usa;
    }

    /**
     * @param usa the usa to set
     */
    public void setUsa(boolean usa) {
        this.usa = usa;
    }

    /**
     * @return the ntscOnly
     */
    public boolean isNtscOnly() {
        return ntscOnly;
    }

    /**
     * @param ntscOnly the ntscOnly to set
     */
    public void setNtscOnly(boolean ntscOnly) {
        this.ntscOnly = ntscOnly;
    }

    /**
     * @return the countries
     */
    public String getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(String countries) {
        this.countries = countries;
    }

    /**
     * @return the palOnly
     */
    public boolean isPalOnly() {
        return palOnly;
    }

    /**
     * @param palOnly the palOnly to set
     */
    public void setPalOnly(boolean palOnly) {
        this.palOnly = palOnly;
    }

    /**
     * @return the nonUsa
     */
    public boolean isNonUsa() {
        return nonUsa;
    }

    /**
     * @param nonUsa the nonUsa to set
     */
    public void setNonUsa(boolean nonUsa) {
        this.nonUsa = nonUsa;
    }

    /**
     * @return the checkSum
     */
    public boolean isCheckSum() {
        return checkSum;
    }

    /**
     * @param checkSum the checkSum to set
     */
    public void setCheckSum(boolean checkSum) {
        this.checkSum = checkSum;
    }

    /**
     * @return the basCheckSum
     */
    public boolean isBasCheckSum() {
        return basCheckSum;
    }

    /**
     * @param basCheckSum the basCheckSum to set
     */
    public void setBasCheckSum(boolean basCheckSum) {
        this.basCheckSum = basCheckSum;
    }
    
}
