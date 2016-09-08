package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class MemberFilter<M> {

    private final MemberFilter<M> parent;
    private final Predicate<M> test;
    private final FastClasspathScanner scanner;

    private Set<M> results;

    MemberFilter(FastClasspathScanner scanner) {
        this.parent = null;
        this.test = Lambdas.acceptAll();
        this.scanner = scanner;
    }

    MemberFilter(MemberFilter<M> parent, Predicate<M> test) {
        this.parent = parent;
        this.test = test;
        this.scanner = null;
    }

    public Set<M> find() {
        if (results == null)
            results = accumulate().filter(this::test).collect(Collectors.toSet());
        return results;
    }

    public boolean match() {
        return find().isEmpty();
    }

    FastClasspathScanner getScanner() {
        return scanner != null ? scanner : parent.getScanner();
    }

    abstract Stream<M> accumulate();

    boolean test(M member) {
        return test.test(member) && (parent == null || parent.test(member));
    }

}
