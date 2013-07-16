/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.search.type;

import com.tgm.scrapers.search.SearchResults;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 *
 * @author mark
 */
public abstract class SearchHTTPType implements SearchType {

    private String urlString = "";

    public SearchResults search(String game, String platform) {
        SearchResults searchResults;
        URL url;
        InputStream inputStream;
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        try {
            searchResults = new SearchResults();
            try {
                url = new URL(urlString + "?name=" + game + "&platform=" + platform);
                inputStream = url.openConnection().getInputStream();
                jaxbContext = JAXBContext.newInstance(SearchResults.class);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                searchResults = (SearchResults) jaxbUnmarshaller.unmarshal(inputStream);
            } catch (MalformedURLException mue) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", mue);
            } catch (IOException ioe) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", ioe);
            } catch (JAXBException jaxbe) {
                Logger.getLogger(this.getClass()).fatal("Unable to Search", jaxbe);
            }
            Logger.getLogger(this.getClass()).info(searchResults);
            return searchResults;
        } finally {
            searchResults = null;
            url = null;
            inputStream = null;
            jaxbContext = null;
            jaxbUnmarshaller = null;
        }
    }

    public SearchResults search(String game, long platformId) {
        SearchResults searchResults = new SearchResults();
        try {
            URL url = new URL(urlString + "?name=" + game + "&platformid=" + platformId);
            InputStream inputStream = url.openConnection().getInputStream();
            JAXBContext jaxbContext = JAXBContext.newInstance(SearchResults.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            searchResults = (SearchResults) jaxbUnmarshaller.unmarshal(inputStream);
        } catch (MalformedURLException mue) {
            System.err.println(mue);
        } catch (IOException ioe) {
            System.err.println(ioe);
        } catch (JAXBException jaxbe) {
            System.err.println(jaxbe);
        }
        System.out.println(searchResults);
        return searchResults;
    }

    public SearchResults search(String game) {
        SearchResults searchResults = new SearchResults();
        try {
            URL url = new URL(urlString + "?name=" + game);
            InputStream inputStream = url.openConnection().getInputStream();
            JAXBContext jaxbContext = JAXBContext.newInstance(SearchResults.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            searchResults = (SearchResults) jaxbUnmarshaller.unmarshal(inputStream);
        } catch (MalformedURLException mue) {
            System.err.println(mue);
        } catch (IOException ioe) {
            System.err.println(ioe);
        } catch (JAXBException jaxbe) {
            System.err.println(jaxbe);
        }
        System.out.println(searchResults);
        return searchResults;
    }
}
