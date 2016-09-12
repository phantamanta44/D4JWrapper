package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.data.Permission;
import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

public class GuildUserStream extends UserStream<GuildUser> {

    GuildUserStream(Stream<GuildUser> backing) {
        super(backing);
    }

    @Override
    public GuildUserStream filter(Predicate<? super GuildUser> predicate) {
        backing = backing.filter(predicate);
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super GuildUser, ? extends R> mapper) {
        return backing.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super GuildUser> mapper) {
        return backing.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super GuildUser> mapper) {
        return backing.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super GuildUser> mapper) {
        return backing.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super GuildUser, ? extends Stream<? extends R>> mapper) {
        return backing.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super GuildUser, ? extends IntStream> mapper) {
        return backing.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super GuildUser, ? extends LongStream> mapper) {
        return backing.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super GuildUser, ? extends DoubleStream> mapper) {
        return backing.flatMapToDouble(mapper);
    }

    @Override
    public GuildUserStream distinct() {
        backing = backing.distinct();
        return this;
    }

    @Override
    public GuildUserStream sorted() {
        backing = backing.sorted();
        return this;
    }

    @Override
    public GuildUserStream sorted(Comparator<? super GuildUser> comparator) {
        backing = backing.sorted(comparator);
        return this;
    }

    @Override
    public GuildUserStream peek(Consumer<? super GuildUser> action) {
        backing = backing.peek(action);
        return this;
    }

    @Override
    public GuildUserStream limit(long maxSize) {
        backing = backing.limit(maxSize);
        return this;
    }

    @Override
    public GuildUserStream skip(long n) {
        backing = backing.skip(n);
        return this;
    }

    @Override
    public void forEach(Consumer<? super GuildUser> action) {
        backing.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super GuildUser> action) {
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
    public GuildUser reduce(GuildUser identity, BinaryOperator<GuildUser> accumulator) {
        return backing.reduce(identity, accumulator);
    }

    @Override
    public Optional<GuildUser> reduce(BinaryOperator<GuildUser> accumulator) {
        return backing.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super GuildUser, U> accumulator, BinaryOperator<U> combiner) {
        return backing.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super GuildUser> accumulator, BiConsumer<R, R> combiner) {
        return backing.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super GuildUser, A, R> collector) {
        return backing.collect(collector);
    }

    @Override
    public Optional<GuildUser> min(Comparator<? super GuildUser> comparator) {
        return backing.min(comparator);
    }

    @Override
    public Optional<GuildUser> max(Comparator<? super GuildUser> comparator) {
        return backing.max(comparator);
    }

    @Override
    public long count() {
        return backing.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super GuildUser> predicate) {
        return backing.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super GuildUser> predicate) {
        return backing.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super GuildUser> predicate) {
        return backing.noneMatch(predicate);
    }

    @Override
    public Optional<GuildUser> findFirst() {
        return backing.findFirst();
    }

    @Override
    public Optional<GuildUser> findAny() {
        return backing.findAny();
    }

    @Override
    public Iterator<GuildUser> iterator() {
        return backing.iterator();
    }

    @Override
    public Spliterator<GuildUser> spliterator() {
        return backing.spliterator();
    }

    @Override
    public boolean isParallel() {
        return backing.isParallel();
    }

    @Override
    public GuildUserStream sequential() {
        backing = backing.sequential();
        return this;
    }

    @Override
    public GuildUserStream parallel() {
        backing = backing.parallel();
        return this;
    }

    @Override
    public GuildUserStream unordered() {
        backing = backing.unordered();
        return this;
    }

    @Override
    public GuildUserStream onClose(Runnable closeHandler) {
        backing.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        backing.close();
    }

    @Override
    public GuildUserStream withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public GuildUserStream after(long time) {
        super.after(time);
        return this;
    }

    public GuildUserStream withName(String name) {
        return filter(u -> u.name().equalsIgnoreCase(name));
    }

    public GuildUserStream withNickname(String nick) {
        return filter(u -> u.hasNickname() && u.displayName().equalsIgnoreCase(nick));
    }

    public GuildUserStream withPerms(Permission... perms) {
        return filter(u -> u.has(perms));
    }

    public GuildUserStream of(Guild guild) {
        return new GuildUserStream(backing.map(u -> u.of(guild)).filter(Lambdas.nonNull()));
    }

    public ChannelUserStream of(Channel channel) {
        return new ChannelUserStream(backing.map(u -> u.of(channel)).filter(Lambdas.nonNull()));
    }

}
