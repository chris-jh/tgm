/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

/**
 *
 * @author christopher
 */
@Entity
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name = "GameEntity.findByName", query = "SELECT a FROM GameEntity a WHERE a.name = :name"),
    @NamedQuery(name = "GameEntity.findByNameAndPlatform", query = "SELECT a FROM GameEntity a WHERE a.name = :name AND a.platformRef = :platform"),
    @NamedQuery(name = "GameEntity.findGamesByPlatform", query = "SELECT a FROM GameEntity a WHERE a.platformRef = :platform"),
    @NamedQuery(name = "GameEntity.findRecentlyAddedGamesByPlatform", query = "SELECT a FROM GameEntity a WHERE a.platformRef = :platform ORDER BY a.dateAdded DESC"),
    @NamedQuery(name = "GameEntity.findRecentlyPlayedGamesByPlatform", query = "SELECT a FROM GameEntity a WHERE a.platformRef = :platform ORDER BY a.dateLastPlayed DESC")
})
public class GameEntity implements EntityInterface, Serializable {

    private static final String table = "GameEntity";
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, unique = true, name = "id")
    private Integer id;
    @Version
    private Integer version;
    @Column(name = "name", nullable = false)
    private String name;
    @JoinColumn(name = "platform_ref", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private PlatformEntity platformRef;
    @Column(name = "external_id", nullable = true)
    private Long externalId;
    @Column(name = "overview_text", nullable = true)
    private String overviewText;
    @Column(name = "image_box_art_path", nullable = true)
    private String imageBoxArtPath;
    @Column(name = "image_screen_shot_path", nullable = true)
    private String imageScreenShotPath;
    @Column(name = "image_art_path", nullable = true)
    private String imageArtPath;
    @Column(name = "date_added", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "date_last_played", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateLastPlayed;
    @Column(name = "play_count", nullable = true)
    private Integer playCount;
    @Column(name = "rating", nullable = true)
    private Integer rating;
    @OneToMany(mappedBy = "gameRef", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<GamePathEntity> gamePathList;

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

    @Override
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
     * @return the platformRef
     */
    public PlatformEntity getPlatformRef() {
        return platformRef;
    }

    /**
     * @param platformRef the platformRef to set
     */
    public void setPlatformRef(PlatformEntity platformRef) {
        this.platformRef = platformRef;
    }

    @Override
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

    /**
     * @return the dateAdded
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * @param dateAdded the dateAdded to set
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * @return the dateLastPlayed
     */
    public Date getDateLastPlayed() {
        return dateLastPlayed;
    }

    /**
     * @param dateLastPlayed the dateLastPlayed to set
     */
    public void setDateLastPlayed(Date dateLastPlayed) {
        this.dateLastPlayed = dateLastPlayed;
    }

    /**
     * @return the playCount
     */
    public Integer getPlayCount() {
        return playCount;
    }

    /**
     * @param playCount the playCount to set
     */
    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    /**
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * @return the gamePathList
     */
    public Set<GamePathEntity> getGamePathList() {
        return gamePathList;
    }

    /**
     * @param gamePathList the gamePathList to set
     */
    public void setGamePathList(Set<GamePathEntity> gamePathList) {
        this.gamePathList = gamePathList;
    }
}
