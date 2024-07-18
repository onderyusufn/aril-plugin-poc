package com.aril.mainapp.classloader;

//import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

//@RefreshScope
public class PluginClassloader extends ClassLoader {

    private final String pluginsFolder;
    private List<JarFile> jars;
    private final Map<String, ClassLoader> classLoaderMap = new HashMap<>();

    public PluginClassloader(String pluginsFolder, ClassLoader parent) {
        super(parent);
        this.pluginsFolder = pluginsFolder;
        init(parent);
    }

    public void init(ClassLoader parent) {
        File[] jarFiles = new File(pluginsFolder).listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null) {
            jars = Collections.emptyList();
            return;
        }

        this.jars = Arrays.stream(jarFiles).map(jarFile -> {
            try {
                JarFile jarFile1 = new JarFile(jarFile);
                classLoaderMap.put(new URL("jar", null, "file:" + jarFile1.getName()).toString(), new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, parent));
                return jarFile1;
            } catch (IOException e) {
                // we've just listed them, they're here
                return null;
            }
        }).toList();
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        String className = name.replace('.', '/').concat(".class");
        List<URL> resourceUrl = getResourceUrl(className);

        if (!resourceUrl.isEmpty()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            List<StackTraceElement> list = Arrays.stream(stackTrace).filter(ste -> ste.getClassName().startsWith("com.aril.plugin")).toList();
            boolean isPluginClass = name.startsWith("com.aril.plugin");
            boolean isFromPlugin = list.isEmpty() && !isPluginClass;
            if (isFromPlugin) {
                return null;
            }

            URL url = resourceUrl.getFirst();
            byte[] bytes = getBytes(url);
            ClassLoader classLoader = classLoaderMap.get(url.toString().split("!")[0]);
            if (classLoader != null) {
                return classLoader.loadClass(name);
            }
            Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
            resolveClass(clazz);
            return clazz;
        } else {
            throw new ClassNotFoundException();
        }
    }

    @Override
    protected URL findResource(final String name) {
        List<URL> resourceUrls = getResourceUrl(name);
        return resourceUrls.isEmpty() ? null : resourceUrls.getFirst();
    }

    @Override
    protected Enumeration<URL> findResources(final String name) {
        List<URL> urls = getResourceUrl(name);
        return Collections.enumeration(urls);
    }

    private byte[] getBytes(final URL classUrl) {
        try (InputStream inputStream = classUrl.openStream()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<URL> getResourceUrl(String className) {
        List<URL> urls = new ArrayList<>();
        for (JarFile jar : jars) {
            ZipEntry entry = jar.getEntry(className);
            if (entry != null) {
                urls.add(createUrl(entry.getName(), jar.getName()));
            }
        }

        return urls;
    }

    private URL createUrl(final String className, final String jarFileName) {
        try {
            return new URL("jar", null, "file:" + jarFileName + "!/" + className);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }
}
