/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm;

import com.tgm.interfaces.KickStartInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopher
 */
public class Boot {

    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        String config = "classpath:/META-INF/application-context.xml";

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

        context = new ClassPathXmlApplicationContext(config);
        context.registerShutdownHook();

        /*try {
            while (true) {
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    public static void exit(int exit) {
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(exit);
    }
}
