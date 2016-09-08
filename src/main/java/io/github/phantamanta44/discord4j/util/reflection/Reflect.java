package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Reflect {

    public static TypeFilter types() {
        return new TypeFilter(new FastClasspathScanner(getClassloaderUrls()));
    }

    public static MethodFilter methods() {
        return new MethodFilter(new FastClasspathScanner(getClassloaderUrls()));
    }

    public static FieldFilter fields() {
        return new FieldFilter(new FastClasspathScanner(getClassloaderUrls()));
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
        return urls.stream().map(URL::toExternalForm).toArray(String[]::new);
    }

}
