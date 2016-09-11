package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class Reflect {

    public static TypeFilter types() {
        return new TypeFilter(new FastClasspathScanner(getJavaClasspathUrls()));
    }

    public static MethodFilter methods() {
        return new MethodFilter(new FastClasspathScanner(getJavaClasspathUrls()));
    }

    public static FieldFilter fields() {
        return new FieldFilter(new FastClasspathScanner(getJavaClasspathUrls()));
    }

    public static TypeFilter types(ClassLoader loader) {
        return new TypeFilter(new FastClasspathScanner(getClassloaderUrls(loader)));
    }

    public static MethodFilter methods(ClassLoader loader) {
        return new MethodFilter(new FastClasspathScanner(getClassloaderUrls(loader)));
    }

    public static FieldFilter fields(ClassLoader loader) {
        return new FieldFilter(new FastClasspathScanner(getClassloaderUrls(loader)));
    }

    public static TypeFilter types(String... packages) {
        return new TypeFilter(new FastClasspathScanner(packages));
    }

    public static MethodFilter methods(String... packages) {
        return new MethodFilter(new FastClasspathScanner(packages));
    }

    public static FieldFilter fields(String... packages) {
        return new FieldFilter(new FastClasspathScanner(packages));
    }

    public static String[] getJavaClasspathUrls() {
        Set<URL> urls = new HashSet<>();
        for (String path : System.getProperty("java.class.path").split(File.pathSeparator)) {
            try {
                urls.add(new File(path).toURI().toURL());
            } catch (Exception ignored) { }
        }
        return urls.stream().flatMap(Reflect::buildDirTree).toArray(String[]::new);
    }

    public static String[] getClassloaderUrls() {
        return getClassloaderUrls(Thread.currentThread().getContextClassLoader());
    }

    public static String[] getClassloaderUrls(ClassLoader... loaders) {
        Set<URL> urls = new HashSet<>();
        for (ClassLoader topLoader : loaders) {
            ClassLoader loader = topLoader;
            while (loader != null) {
                if (loader instanceof URLClassLoader)
                    Collections.addAll(urls, ((URLClassLoader) loader).getURLs());
                loader = loader.getParent();
            }
        }
        return urls.stream().flatMap(Reflect::buildDirTree).toArray(String[]::new);
    }

    private static Stream<String> buildDirTree(URL path) {
        try {
            List<String> packages = new ArrayList<>();
            Enumeration<JarEntry> entries = new JarFile(path.getFile()).entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() && !entry.getName().startsWith("META-INF/") && !entry.getName().startsWith("java/"))
                    packages.add(entry.getName().replace('/', '.'));
            }
            return packages.stream().map(s -> s.substring(0, s.length() - 1));
        } catch (Exception e) {
            return Stream.of();
        }
    }

}
