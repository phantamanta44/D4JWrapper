package io.github.phantamanta44.discord4j.data.wrapper;

import io.github.phantamanta44.discord4j.core.RequestQueue;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.INullaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.NullaryDeferred;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageList;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MessageStream extends WrappingStream<Message, IMessage, MessageList> {

    MessageStream(MessageList source) {
        super(source.stream().map(Wrapper::wrap), source);
    }

    @Override
    public MessageStream filter(Predicate<? super Message> predicate) {
        backing = backing.filter(predicate);
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super Message, ? extends R> mapper) {
        return backing.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Message> mapper) {
        return backing.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Message> mapper) {
        return backing.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Message> mapper) {
        return backing.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super Message, ? extends Stream<? extends R>> mapper) {
        return backing.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super Message, ? extends IntStream> mapper) {
        return backing.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super Message, ? extends LongStream> mapper) {
        return backing.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Message, ? extends DoubleStream> mapper) {
        return backing.flatMapToDouble(mapper);
    }

    @Override
    public MessageStream distinct() {
        backing = backing.distinct();
        return this;
    }

    @Override
    public MessageStream sorted() {
        backing = backing.sorted();
        return this;
    }

    @Override
    public MessageStream sorted(Comparator<? super Message> comparator) {
        backing = backing.sorted(comparator);
        return this;
    }

    @Override
    public MessageStream peek(Consumer<? super Message> action) {
        backing = backing.peek(action);
        return this;
    }

    @Override
    public MessageStream limit(long maxSize) {
        backing = backing.limit(maxSize);
        return this;
    }

    @Override
    public MessageStream skip(long n) {
        backing = backing.skip(n);
        return this;
    }

    @Override
    public void forEach(Consumer<? super Message> action) {
        backing.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super Message> action) {
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
    public Message reduce(Message identity, BinaryOperator<Message> accumulator) {
        return backing.reduce(identity, accumulator);
    }

    @Override
    public Optional<Message> reduce(BinaryOperator<Message> accumulator) {
        return backing.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Message, U> accumulator, BinaryOperator<U> combiner) {
        return backing.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Message> accumulator, BiConsumer<R, R> combiner) {
        return backing.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super Message, A, R> collector) {
        return backing.collect(collector);
    }

    @Override
    public Optional<Message> min(Comparator<? super Message> comparator) {
        return backing.min(comparator);
    }

    @Override
    public Optional<Message> max(Comparator<? super Message> comparator) {
        return backing.max(comparator);
    }

    @Override
    public long count() {
        return backing.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super Message> predicate) {
        return backing.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super Message> predicate) {
        return backing.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super Message> predicate) {
        return backing.noneMatch(predicate);
    }

    @Override
    public Optional<Message> findFirst() {
        return backing.findFirst();
    }

    @Override
    public Optional<Message> findAny() {
        return backing.findAny();
    }

    @Override
    public Iterator<Message> iterator() {
        return backing.iterator();
    }

    @Override
    public Spliterator<Message> spliterator() {
        return backing.spliterator();
    }

    @Override
    public boolean isParallel() {
        return backing.isParallel();
    }

    @Override
    public MessageStream sequential() {
        backing = backing.sequential();
        return this;
    }

    @Override
    public MessageStream parallel() {
        backing = backing.parallel();
        return this;
    }

    @Override
    public MessageStream unordered() {
        backing = backing.unordered();
        return this;
    }

    @Override
    public MessageStream onClose(Runnable closeHandler) {
        backing.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        backing.close();
    }

    @Override
    public MessageStream withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public MessageStream after(long time) {
        super.after(time);
        return this;
    }

    public INullaryPromise destroyAll() {
        List<IMessage> toDel = map(Wrapper::getBacking).collect(Collectors.toList());
        if (toDel.size() > 1)
            return RequestQueue.request(() -> source.bulkDelete(toDel));
        if (toDel.size() == 1)
            return RequestQueue.request(() -> toDel.get(0).delete());
        NullaryDeferred def = new NullaryDeferred();
        def.resolve();
        return def.promise();
    }

}
