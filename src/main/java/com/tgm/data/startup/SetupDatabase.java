/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.startup;

import com.tgm.utils.OSUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author christopher
 */
public class SetupDatabase implements InitializingBean {

    @Autowired
    private BasicDataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private final String script = "resources/sql/schema.sql";
    @Autowired
    private OSUtils osUtils;

    public void afterPropertiesSet() throws Exception {
        if (!checkExists()) {
            generateDatabase();
        }
    }

    private boolean checkExists() {
        File f = new File(osUtils.getAppHomePath() + "/db/tgm-database.script");
        try {
            if (!f.exists()) {
                f = new File(osUtils.getAppHomePath() + "/db");
                f.mkdirs();
                return false;
            }
            return true;
        } finally {
            f = null;
        }
    }

    private void generateDatabase() throws Exception {
        Logger.getLogger(this.getClass()).info("Generate Database...");
        Resource rc = new ClassPathResource(script);
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        BufferedReader br = new BufferedReader(new InputStreamReader(rc.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String thisLine;
        while ((thisLine = br.readLine()) != null) {
            sb.append(thisLine.trim());
        }
        Logger.getLogger(this.getClass()).info("Generate Database...\n" + sb.toString());
        jdbcTemplateObject.execute(sb.toString());
        this.jdbcTemplateObject = null;
        rc = null;
        Logger.getLogger(this.getClass()).info("Generate Database...Done");
    }

    /**
     * @param osUtils the osUtils to set
     */
    public void setOsUtils(OSUtils osUtils) {
        this.osUtils = osUtils;
    }
}
