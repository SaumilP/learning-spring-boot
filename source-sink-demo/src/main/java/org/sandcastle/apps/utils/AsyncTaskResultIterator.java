package org.sandcastle.apps.utils;

import com.github.ferstl.streams.ParallelStreamSupport;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class wraps a sequence of TaskResults into an iterator upto the first TaskResult where {@code }isLastResult()} returns {@code true}
 */
public class AsyncTaskResultIterator<T extends TaskResult> implements Iterator<T>, TaskListener<T> {

    /**
     * This acts as an asynchronous buffer, so we can easily wait for the next TaskResult
     */
    private final BlockingQueue<T> blockingQueue;
    /**
     * Becomes {@code true} once {@code TaskResult.isLastResult()} is received
     */
    private boolean ended;

    public AsyncTaskResultIterator() {
        blockingQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Waits on a new TaskResult and returns it as long as the previous TaskResult did not specify {@code isLastResult()}. Afterwards no more elements can be retrieved.
     */
    @Override
    public T next() {
        if (ended) {
            throw new NoSuchElementException();
        } else {
            try {
                T next = blockingQueue.take();
                ended = next.isLastResult();
                return next;
            } catch (InterruptedException e) {
                throw new IllegalStateException("Could not retrieve next value", e);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !ended;
    }

    /**
     * Enqueue another TaskResult for retrieval
     */
    @Override
    public void onTaskResult(T result) {
        if (ended) {
            throw new IllegalStateException("Already received a TaskResult with isLastResult() == true");
        }
        try {
            blockingQueue.put(result);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Could not enqueue next value", e);
        }
    }

    /**
     * Builds a Stream that acts upon the results just when they become available
     */
    public Stream<T> unbufferedStream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(this, 0);
        return StreamSupport.stream(spliterator, false);
    }

    /**
     * Builds a Stream that acts upon the results just when they become available
     */
    public Stream<T> unbufferedParallelStream(int maxAllowedParallelism) {
        ForkJoinPool pool = new ForkJoinPool(maxAllowedParallelism);
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(this, 0);
        return ParallelStreamSupport.parallelStream(spliterator, pool);
    }

    /**
     * Buffers all results and builds a Stream around the results
     */
    public Stream<T> bufferedStream() {
        Stream.Builder<T> builder = Stream.builder();
        this.forEachRemaining(builder);
        return builder.build();
    }
}
