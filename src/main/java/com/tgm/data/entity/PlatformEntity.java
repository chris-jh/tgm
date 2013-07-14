/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.entity;

import com.tgm.enums.Platform;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author christopher
 */
@Entity
@Table(name = "platform")
@NamedQueries({
    @NamedQuery(name = "PlatformEntity.findByName", query = "SELECT a FROM PlatformEntity a WHERE a.name = :name")
})
public class PlatformEntity implements EntityInterface, Serializable {

    public final static String table = "PlatformEntity";
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, unique = true, name = "id")
    private Integer id;
    @Version
    private Integer version;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private Platform name;
    @OneToMany(mappedBy = "platformRef", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<GameEntity> gameList;

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
    public Platform getName() {
        return name;
    }

    /**
     * @param name the person to set
     */
    public void setName(Platform name) {
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
     * @return the gameList
     */
    public Set<GameEntity> getGameList() {
        return gameList;
    }

    /**
     * @param gameList the gameList to set
     */
    public void setGameList(Set<GameEntity> gameList) {
        this.gameList = gameList;
    }

    public void fetch() {
        for (GameEntity game : gameList) {
            game.fetch();
        }
    }
}
