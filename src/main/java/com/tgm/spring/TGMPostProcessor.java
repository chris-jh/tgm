/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.spring;

import com.tgm.interfaces.KickStartInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 *
 * @author christopher
 */
public class TGMPostProcessor implements BeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof KickStartInterface) {
            try {
                ((KickStartInterface) bean).kickStart();
            } catch (Exception e) {
                Logger.getLogger(this.getClass()).fatal(e);
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
