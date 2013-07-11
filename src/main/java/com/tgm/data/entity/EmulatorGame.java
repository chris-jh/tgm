/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.apache.log4j.Logger;

/**
 *
 * @author christopher
 */
@Entity
@Table(name = "emulator_game")
public class EmulatorGame implements EntityInterface, Serializable {

    public final static String table = "EmulatorGame"; 
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(insertable=false, updatable=false, unique = true, name = "id")
    private Integer id;
    
    @Version
    private Integer version;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rom_location", nullable = false)
    private String romLocation;
    
    @JoinColumn(name = "emulator_ref", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade=CascadeType.REFRESH)
    private Emulator emulatorRef;
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the person to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the romLocation
     */
    public String getRomLocation() {
        return romLocation;
    }

    /**
     * @param romLocation the romLocation to set
     */
    public void setRomLocation(String romLocation) {
        this.romLocation = romLocation;
    }

    /**
     * @return the emulatorRef
     */
    public Emulator getEmulatorRef() {
        return emulatorRef;
    }

    /**
     * @param emulatorRef the emulatorRef to set
     */
    public void setEmulatorRef(Emulator emulatorRef) {
        this.emulatorRef = emulatorRef;
    }

    public void fetch() {
        this.getEmulatorRef().getId();
    }
}
