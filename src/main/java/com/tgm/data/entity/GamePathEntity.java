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
@Table(name = "game_path")
@NamedQueries({
    @NamedQuery(name = "GamePathEntity.findByFileName", query = "SELECT a FROM GamePathEntity a WHERE a.fileName = :name"),
    @NamedQuery(name = "GamePathEntity.findByGame", query = "SELECT a FROM GamePathEntity a WHERE a.gameRef = :game"),

})
public class GamePathEntity implements EntityInterface, Serializable {

    private static final String table = "GamePathEntity";
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, unique = true, name = "id")
    private Integer id;
    @Version
    private Integer version;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "game_path", nullable = false)
    private String gamePath;
    @JoinColumn(name = "game_ref", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private GameEntity gameRef;

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
    public GameEntity getGameRef() {
        return gameRef;
    }

    /**
     * @param platformRef the platformRef to set
     */
    public void setGameRef(GameEntity gameRef) {
        this.gameRef = gameRef;
    }

    @Override
    public void fetch() {
        this.getGameRef().getId();
    }

    /**
     * @return the gamePath
     */
    public String getGamePath() {
        return gamePath;
    }

    /**
     * @param gamePath the gamePath to set
     */
    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

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
}
