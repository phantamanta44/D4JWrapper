package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

public class ChannelUserStream extends UserStream<ChannelUser> {

    ChannelUserStream(Stream<ChannelUser> backing) {
        super(backing);
    }

    @Override
    public ChannelUserStream filter(Predicate<? super ChannelUser> predicate) {
        backing = backing.filter(predicate);
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super ChannelUser, ? extends R> mapper) {
        return backing.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super ChannelUser> mapper) {
        return backing.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super ChannelUser> mapper) {
        return backing.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super ChannelUser> mapper) {
        return backing.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super ChannelUser, ? extends Stream<? extends R>> mapper) {
        return backing.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super ChannelUser, ? extends IntStream> mapper) {
        return backing.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super ChannelUser, ? extends LongStream> mapper) {
        return backing.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super ChannelUser, ? extends DoubleStream> mapper) {
        return backing.flatMapToDouble(mapper);
    }

    @Override
    public ChannelUserStream distinct() {
        backing = backing.distinct();
        return this;
    }

    @Override
    public ChannelUserStream sorted() {
        backing = backing.sorted();
        return this;
    }

    @Override
    public ChannelUserStream sorted(Comparator<? super ChannelUser> comparator) {
        backing = backing.sorted(comparator);
        return this;
    }

    @Override
    public ChannelUserStream peek(Consumer<? super ChannelUser> action) {
        backing = backing.peek(action);
        return this;
    }

    @Override
    public ChannelUserStream limit(long maxSize) {
        backing = backing.limit(maxSize);
        return this;
    }

    @Override
    public ChannelUserStream skip(long n) {
        backing = backing.skip(n);
        return this;
    }

    @Override
    public void forEach(Consumer<? super ChannelUser> action) {
        backing.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super ChannelUser> action) {
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
    public ChannelUser reduce(ChannelUser identity, BinaryOperator<ChannelUser> accumulator) {
        return backing.reduce(identity, accumulator);
    }

    @Override
    public Optional<ChannelUser> reduce(BinaryOperator<ChannelUser> accumulator) {
        return backing.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super ChannelUser, U> accumulator, BinaryOperator<U> combiner) {
        return backing.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super ChannelUser> accumulator, BiConsumer<R, R> combiner) {
        return backing.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super ChannelUser, A, R> collector) {
        return backing.collect(collector);
    }

    @Override
    public Optional<ChannelUser> min(Comparator<? super ChannelUser> comparator) {
        return backing.min(comparator);
    }

    @Override
    public Optional<ChannelUser> max(Comparator<? super ChannelUser> comparator) {
        return backing.max(comparator);
    }

    @Override
    public long count() {
        return backing.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super ChannelUser> predicate) {
        return backing.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super ChannelUser> predicate) {
        return backing.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super ChannelUser> predicate) {
        return backing.noneMatch(predicate);
    }

    @Override
    public Optional<ChannelUser> findFirst() {
        return backing.findFirst();
    }

    @Override
    public Optional<ChannelUser> findAny() {
        return backing.findAny();
    }

    @Override
    public Iterator<ChannelUser> iterator() {
        return backing.iterator();
    }

    @Override
    public Spliterator<ChannelUser> spliterator() {
        return backing.spliterator();
    }

    @Override
    public boolean isParallel() {
        return backing.isParallel();
    }

    @Override
    public ChannelUserStream sequential() {
        backing = backing.sequential();
        return this;
    }

    @Override
    public ChannelUserStream parallel() {
        backing = backing.parallel();
        return this;
    }

    @Override
    public ChannelUserStream unordered() {
        backing = backing.unordered();
        return this;
    }

    @Override
    public ChannelUserStream onClose(Runnable closeHandler) {
        backing.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        backing.close();
    }

    @Override
    public ChannelUserStream withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ChannelUserStream after(long time) {
        super.after(time);
        return this;
    }

    public ChannelUserStream withName(String name) {
        return filter(u -> u.name().equalsIgnoreCase(name));
    }

    public ChannelUserStream withNickname(String nick) {
        return filter(u -> u.hasNickname() && u.displayName().equalsIgnoreCase(nick));
    }

    public ChannelUserStream withPerms(Permission... perms) {
        return filter(u -> u.has(perms));
    }

    public GuildUserStream of(Guild guild) {
        return new GuildUserStream(backing.map(u -> u.of(guild)).filter(Lambdas.nonNull()));
    }

    public ChannelUserStream of(Channel channel) {
        return new ChannelUserStream(backing.map(u -> u.of(channel)).filter(Lambdas.nonNull()));
    }

}
