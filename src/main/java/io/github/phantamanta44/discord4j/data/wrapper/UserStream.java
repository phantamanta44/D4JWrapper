package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.util.function.Lambdas;
import sx.blah.discord.handle.obj.IUser;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class UserStream<T extends User> extends WrappingStream<T, IUser, Collection<IUser>> {

    UserStream(Collection<IUser> source) {
        super(source.stream().map(Wrapper::wrap), source);
    }

    UserStream(Stream<T> backing) {
        super(backing, null);
    }

    @Override
    public UserStream<T> filter(Predicate<? super T> predicate) {
        backing = backing.filter(predicate);
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return backing.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return backing.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return backing.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return backing.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return backing.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return backing.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return backing.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return backing.flatMapToDouble(mapper);
    }

    @Override
    public UserStream<T> distinct() {
        backing = backing.distinct();
        return this;
    }

    @Override
    public UserStream<T> sorted() {
        backing = backing.sorted();
        return this;
    }

    @Override
    public UserStream<T> sorted(Comparator<? super T> comparator) {
        backing = backing.sorted();
        return this;
    }

    @Override
    public UserStream<T> peek(Consumer<? super T> action) {
        backing = backing.peek(action);
        return this;
    }

    @Override
    public UserStream<T> limit(long maxSize) {
        backing = backing.limit(maxSize);
        return this;
    }

    @Override
    public UserStream<T> skip(long n) {
        backing = backing.skip(n);
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        backing.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        backing.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return backing.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return backing.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return backing.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return backing.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return backing.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return backing.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return backing.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return backing.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return backing.max(comparator);
    }

    @Override
    public long count() {
        return backing.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return backing.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return backing.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return backing.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return backing.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return backing.findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return backing.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return backing.spliterator();
    }

    @Override
    public boolean isParallel() {
        return backing.isParallel();
    }

    @Override
    public UserStream<T> sequential() {
        backing = backing.sequential();
        return this;
    }

    @Override
    public UserStream<T> parallel() {
        backing = backing.parallel();
        return this;
    }

    @Override
    public UserStream<T> unordered() {
        backing = backing.unordered();
        return this;
    }

    @Override
    public UserStream<T> onClose(Runnable closeHandler) {
        backing.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        backing.close();
    }

    @Override
    public UserStream<T> withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public UserStream<T> after(long time) {
        super.after(time);
        return this;
    }

    public UserStream<T> withName(String name) {
        return filter(u -> u.name().equalsIgnoreCase(name));
    }

    public GuildUserStream of(Guild guild) {
        return new GuildUserStream(backing.map(u -> u.of(guild)).filter(Lambdas.nonNull()));
    }

}
