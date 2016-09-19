package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.math.BitUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodFilter extends MemberFilter<Method> {

    MethodFilter(FastClasspathScanner scanner) {
        super(scanner);
    }

    MethodFilter(MemberFilter<Method> parent, Predicate<Method> test) {
        super(parent, test);
    }

    @Override
    Stream<Method> accumulate() {
        Set<Method> methods = new HashSet<>();
        getScanner().scan().getNamesOfAllClasses().forEach(cn -> {
            try {
                Collections.addAll(methods, Class.forName(cn).getDeclaredMethods());
            } catch (NoClassDefFoundError | ClassNotFoundException | ExceptionInInitializerError ignored) { }
        });
        return methods.stream();
    }

    public MethodFilter mask(int mods) {
        return new MethodFilter(this, m -> BitUtils.hasFlags(m.getModifiers(), mods));
    }

    public MethodFilter mod(int... mods) {
        return mask(BitUtils.foldFlags(mods));
    }

    public MethodFilter name(String name) {
        return new MethodFilter(this, m -> m.getName().equals(name));
    }

    public MethodFilter params(Class<?>... paramTypes) {
        return new MethodFilter(this, m -> Arrays.equals(m.getParameterTypes(), paramTypes));
    }

    public MethodFilter returns(Class<?> returnType) {
        return new MethodFilter(this, m -> m.getReturnType().equals(returnType));
    }

    @SuppressWarnings("unchecked")
    public MethodFilter tagged(Class<?>... annotations) {
        return new MethodFilter(this, m -> Arrays.stream(annotations).allMatch(a -> m.isAnnotationPresent((Class<? extends Annotation>)a)));
    }

}
