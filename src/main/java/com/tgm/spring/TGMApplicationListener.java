/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 *
 * @author christopher
 */
public class TGMApplicationListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CLOSED %%%%%%%%%%%%%%%%%%%%%");
    }
    
}
