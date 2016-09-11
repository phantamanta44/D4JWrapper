package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.BitUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldFilter extends MemberFilter<Field> {

    FieldFilter(FastClasspathScanner scanner) {
        super(scanner);
    }

    FieldFilter(MemberFilter<Field> parent, Predicate<Field> test) {
        super(parent, test);
    }

    @Override
    Stream<Field> accumulate() {
        Set<Field> fields = new HashSet<>();
        getScanner().scan().getNamesOfAllClasses().forEach(cn -> {
            try {
                Collections.addAll(fields, Class.forName(cn).getDeclaredFields());
            } catch (NoClassDefFoundError | ClassNotFoundException | ExceptionInInitializerError ignored) { }
        });
        return fields.stream();
    }

    public FieldFilter mask(int mods) {
        return new FieldFilter(this, f -> BitUtils.hasFlags(f.getModifiers(), mods));
    }

    public FieldFilter mod(int... mods) {
        return mask(BitUtils.foldFlags(mods));
    }

    public FieldFilter name(String name) {
        return new FieldFilter(this, f -> f.getName().equals(name));
    }

    @SuppressWarnings("unchecked")
    public FieldFilter tagged(Class<?>... annotations) {
        return new FieldFilter(this, f -> Arrays.stream(annotations).allMatch(a -> f.isAnnotationPresent((Class<? extends Annotation>)a)));
    }

    public FieldFilter type(Class<?> fieldType) {
        return new FieldFilter(this, f -> f.getType().equals(fieldType));
    }

}
