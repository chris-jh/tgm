/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.resources;

import com.tgm.enums.Protocol;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public class Path implements InitializingBean{

    private String path;
    private String gameExecutor;
    private String username;
    private String password;
    private Protocol protocol = Protocol.FILE;
    
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(protocol);
        Assert.notNull(path);        
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the gameExecutor
     */
    public String getGameExecutor() {
        return gameExecutor;
    }

    /**
     * @param gameExecutor the gameExecutor to set
     */
    public void setGameExecutor(String gameExecutor) {
        this.gameExecutor = gameExecutor;
    }

    
}
