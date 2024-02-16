package com.jun.platform.register.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class AppJarClassLoader extends URLClassLoader {
    public AppJarClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public AppJarClassLoader(URL[] urls) {
        super(urls);
    }

    public AppJarClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public AppJarClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public AppJarClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }
}
