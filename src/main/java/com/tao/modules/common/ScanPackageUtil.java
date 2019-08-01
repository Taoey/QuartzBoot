package com.tao.modules.common;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 注解扫描工具类
 */
public class ScanPackageUtil {

    /**
     * 扫描包下的所有类
     * @param classPackage
     * @return
     */
    public static List<String> scanClass(String classPackage) {
        List<String> result = new ArrayList();
        try{
            URL url = null;
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(classPackage.replace(".", "/"));
            while(dirs.hasMoreElements()) {
                url = dirs.nextElement();
                if("file".equals(url.getProtocol())) {
                    result.addAll(scanPath(classPackage, url.getFile()));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private static List<String> scanPath(String classPackage, String packagePath) {
        List<String> result = new ArrayList();
        File dir = new File(packagePath);
        if(!dir.exists() || !dir.isDirectory()) return result;
        File[] fiels = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".class");
            }
        });
        for(File file : fiels) {
            result.add(classPackage + "." + file.getName().replaceFirst("\\.class$", ""));
        }
        return result;
    }
}
