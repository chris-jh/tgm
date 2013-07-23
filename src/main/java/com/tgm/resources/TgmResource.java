/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.resources;

import com.tgm.config.PlatformConfig;
import com.tgm.enums.Platform;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import com.tgm.gui.interfaces.ScreenInterface;
import com.tgm.gui.interfaces.ComponentInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 *
 * @author christopher
 */
@Component
public class TgmResource implements InitializingBean {

    public final static String MEDIA = "media";
    private final String assertMessage = "An PlatformConfig already contains platform ";
    private final HashMap<Platform, PlatformConfig> platformConfigs = new HashMap<Platform, PlatformConfig>();
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        findAllPlatformConfigs();
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        File f = new File(destinationFile);
        if (f.exists()) {
            return;
        } else {
            f.getParentFile().mkdirs();
        }
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(f);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public <T extends ScreenInterface> T createScreenInstance(Class<T> beanClass) {
        Assert.isAssignable(ScreenInterface.class, beanClass);
        return (T) applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
    }

    public <T extends ComponentInterface> T createComponentInstance(Class<T> beanClass) {
        Assert.isAssignable(ComponentInterface.class, beanClass);
        return (T) applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
    }

    public <T extends ComponentInterface> T createComponentInstance(Class<T> beanClass, String id, float x, float y, float width, float height) {
        Assert.isAssignable(ComponentInterface.class, beanClass);
        T p = (T) applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
        p.setLocation(x, y);
        p.setSize(width, height);
        p.setId(id);
        return p;
    }

    private void findAllPlatformConfigs() {
        Logger.getLogger(this.getClass()).info("Find All Platform Configs......");

        for (Map.Entry<String, PlatformConfig> entry : applicationContext.getBeansOfType(PlatformConfig.class).entrySet()) {
            Assert.isTrue((!platformConfigs.containsKey(entry.getValue().getPlatform())), assertMessage + entry.getValue().getPlatform());
            platformConfigs.put(entry.getValue().getPlatform(), entry.getValue());
        }
        Logger.getLogger(this.getClass()).info("Find All Platform Configs......Done");

    }

    /**
     * @return the platformConfigs
     */
    public HashMap<Platform, PlatformConfig> getPlatformConfigs() {
        return platformConfigs;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
