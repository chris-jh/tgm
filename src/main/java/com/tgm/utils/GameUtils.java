/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import com.tgm.enums.Platform;
import com.tgm.scanner.PlatformScanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author christopher
 */
public class GameUtils {

    @Async
    public static void processGameFiles(PlatformScanner scanner, Platform platform, String mountedPath, final String[] filterExt, boolean mounted) throws Exception {
        DirectoryStream<java.nio.file.Path> stream = null;
        File f;
        Path dir;
        DirectoryStream.Filter<Path> filter;
        String ff;
        try {
            f = new File(mountedPath);
            filter = new DirectoryStream.Filter<Path>() {
                public boolean accept(Path file) throws IOException {
                    boolean a = false;
                    for (String ext : filterExt) {
                        if (file.toFile().isDirectory()) {
                            a = true;
                        } else {
                            if (file.toString().toLowerCase().endsWith("." + ext.toLowerCase())) {
                                a = true;
                                break;
                            }
                        }
                    }
                    return a;
                }
            };

            dir = f.toPath();
            stream = Files.newDirectoryStream(dir, filter);
            for (Path file : stream) {
                if (file.toFile().isDirectory()) {
                    processGameFiles(scanner, platform, file.toString(), filterExt, mounted);
                } else {
                    ff = null;
                    if (mounted) {
                        ff = file.toString().substring(OSUtils.getTempPath().length());
                    } else {
                        ff = file.toString();
                    }
                    scanner.processGame(platform, file.getFileName().toString(), ff);
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }
        } finally {
            ff = null;
            f = null;
            dir = null;
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
            }
            stream = null;
            filter = null;
        }
    }
}
