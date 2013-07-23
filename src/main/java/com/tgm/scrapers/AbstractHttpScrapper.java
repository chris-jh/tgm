/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers;

import com.tgm.data.entity.EntityInterface;
import com.tgm.scrapers.interfaces.ResultInterface;
import com.tgm.scrapers.interfaces.ScrapperInterface;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public abstract class AbstractHttpScrapper<E extends ResultInterface> implements ScrapperInterface<E> {

    private Class<E> resultClass;
    @Autowired(required = false)
    protected ApplicationContext context;

    public AbstractHttpScrapper(Class<E> resultClass) {
        Assert.isAssignable(ResultInterface.class, resultClass);
        this.resultClass = resultClass;
    }

    @Override
    public E performSearch(String searchString) {
        E results;
        URL url;
        InputStream inputStream;
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        try {
            if (context != null) {
                results = context.getAutowireCapableBeanFactory().createBean(resultClass);
            } else {
                results = (E) Class.forName(resultClass.getName()).newInstance();
            }
            try {
                url = new URL(searchString);
                inputStream = url.openConnection().getInputStream();
                jaxbContext = JAXBContext.newInstance(resultClass);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                results = (E) jaxbUnmarshaller.unmarshal(inputStream);
            } catch (MalformedURLException mue) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", mue);
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", ioe);
            } catch (JAXBException jaxbe) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", jaxbe);
            }
            Logger.getLogger(this.getClass()).info(results);
            return results;
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AbstractHttpScrapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AbstractHttpScrapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AbstractHttpScrapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            results = null;
            url = null;
            inputStream = null;
            jaxbContext = null;
            jaxbUnmarshaller = null;
        }
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
