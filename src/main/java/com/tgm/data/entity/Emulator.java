/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author christopher
 */
@Entity
@Table(name = "emulator")
@NamedQueries({
    @NamedQuery(name = "Emulator.findByName", query = "SELECT a FROM Emulator a WHERE a.name = :name")
})
public class Emulator implements EntityInterface, Serializable {

    public final static String table = "Emulator";
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, unique = true, name = "id")
    private Integer id;
    @Version
    private Integer version;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "emulatorRef", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<EmulatorGame> emulatorGameList;

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
     * @return the emulatorGameList
     */
    public Set<EmulatorGame> getEmulatorGameList() {
        return emulatorGameList;
    }

    /**
     * @param emulatorGameList the emulatorGameList to set
     */
    public void setEmulatorGameList(Set<EmulatorGame> emulatorGameList) {
        this.emulatorGameList = emulatorGameList;
    }

    public void fetch() {
        for (EmulatorGame emulatorGame : emulatorGameList) {
            emulatorGame.fetch();
        }
    }
}
