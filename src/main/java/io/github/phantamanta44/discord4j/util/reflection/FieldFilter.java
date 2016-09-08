package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.CollUtils;
import io.github.phantamanta44.discord4j.util.BitUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
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
        Set<Class> types = new HashSet<>();
        getScanner().matchAllClasses(types::add).scan();
        return types.stream().flatMap(t -> Arrays.stream(t.getDeclaredFields()));
    }

    public FieldFilter mask(int mods) {
        return new FieldFilter(this, m -> BitUtils.hasFlags(m.getModifiers(), mods));
    }

    public FieldFilter mod(int... mods) {
        return mask(BitUtils.foldFlags(mods));
    }

    public FieldFilter name(String name) {
        return new FieldFilter(this, m -> m.getName().equals(name));
    }

    public FieldFilter tagged(Class<?>... annotations) {
        return new FieldFilter(this, m -> CollUtils.containsAll(m.getAnnotations(), (Object[])annotations));
    }

    public FieldFilter type(Class<?> fieldType) {
        return new FieldFilter(this, f -> f.getType().equals(fieldType));
    }

}
