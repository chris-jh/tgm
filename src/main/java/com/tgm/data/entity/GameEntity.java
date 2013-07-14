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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author christopher
 */
@Entity
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name = "GameEntity.findByName", query = "SELECT a FROM GameEntity a WHERE a.name = :name"),
    @NamedQuery(name = "GameEntity.findGamesByPlatform", query = "SELECT a FROM GameEntity a WHERE a.platformRef = :platform")
})
public class GameEntity implements EntityInterface, Serializable {

    private static final String table = "Game";
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, unique = true, name = "id")
    private Integer id;
    @Version
    private Integer version;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "rom_location", nullable = false)
    private String romLocation;
    @JoinColumn(name = "platform_ref", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private PlatformEntity platformRef;
    @Column(name = "external_id", nullable = false)
    private Long externalId;
    @Column(name = "overview_text", nullable = false)
    private String overviewText;
    @Column(name = "image_box_art_path", nullable = false)
    private String imageBoxArtPath;
    @Column(name = "image_screen_shot_path", nullable = false)
    private String imageScreenShotPath;
    @Column(name = "image_art_path", nullable = false)
    private String imageArtPath;

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
    public PlatformEntity getPlatformRef() {
        return platformRef;
    }

    /**
     * @param emulatorRef the emulatorRef to set
     */
    public void setPlatformRef(PlatformEntity emulatorRef) {
        this.platformRef = emulatorRef;
    }

    public void fetch() {
        this.getPlatformRef().getId();
    }

    /**
     * @return the externalId
     */
    public Long getExternalId() {
        return externalId;
    }

    /**
     * @param externalId the externalId to set
     */
    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    /**
     * @return the overviewText
     */
    public String getOverviewText() {
        return overviewText;
    }

    /**
     * @param overviewText the overviewText to set
     */
    public void setOverviewText(String overviewText) {
        this.overviewText = overviewText;
    }

    /**
     * @return the imageBoxArtPath
     */
    public String getImageBoxArtPath() {
        return imageBoxArtPath;
    }

    /**
     * @param imageBoxArtPath the imageBoxArtPath to set
     */
    public void setImageBoxArtPath(String imageBoxArtPath) {
        this.imageBoxArtPath = imageBoxArtPath;
    }

    /**
     * @return the imageScreenShotPath
     */
    public String getImageScreenShotPath() {
        return imageScreenShotPath;
    }

    /**
     * @param imageScreenShotPath the imageScreenShotPath to set
     */
    public void setImageScreenShotPath(String imageScreenShotPath) {
        this.imageScreenShotPath = imageScreenShotPath;
    }

    /**
     * @return the imageArtPath
     */
    public String getImageArtPath() {
        return imageArtPath;
    }

    /**
     * @param imageArtPath the imageArtPath to set
     */
    public void setImageArtPath(String imageArtPath) {
        this.imageArtPath = imageArtPath;
    }
}
