/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopher
 */
public class Boot {

    public static void main(String[] args) {
        String config = "classpath:/resources/configs/application-context.xml";
        
        if ((args != null) && (args.length == 1)) {
            if (StringUtils.contains(args[0], "file:")) {
                config = args[0];
            } else if (StringUtils.contains(args[0], "classpath:")) {
                config = args[0];
            } else {
                config = "file:" + args[0];
            }
        } else {
            System.out.println("Use Default Config...");
        }

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);

        context.registerShutdownHook();
        try {
            while (true) {
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
