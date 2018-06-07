/* 
 *
 * Copyright (C) 1999-2014 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：VIE60
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	     jwyuan    2016/1/2610:17	  Create	
 */
package com.iflytek.vie.threadpool;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jwyuan on 2016/1/26.
 * @param <E> e
 */
public class SizeBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {
    /**
     * 队列
     */
    private final BlockingQueue<E> queue;
    /**
     * 容量
     */
    private final int capacity;
    /**
     * 大小
     */
    private final AtomicInteger size = new AtomicInteger();

    /**
     * 构造器
     * @param queue 队列
     * @param capacity 容量
     */
    public SizeBlockingQueue(BlockingQueue<E> queue, int capacity) {
        assert capacity >= 0;
        this.queue = queue;
        this.capacity = capacity;
    }
    @Override
    public int size() {
        return size.get();
    }

    /**
     * 容量
     * @return int
     */
    public int capacity() {
        return this.capacity;
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> it = queue.iterator();
        return new Iterator<E>() {
            E current;

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                current = it.next();
                return current;
            }

            @Override
            public void remove() {
                // note, we can't call #remove on the iterator because we need to know
                // if it was removed or not
                if (queue.remove(current)) {
                    size.decrementAndGet();
                }
            }
        };
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public E poll() {
        E e = queue.poll();
        if (e != null) {
            size.decrementAndGet();
        }
        return e;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E e = queue.poll(timeout, unit);
        if (e != null) {
            size.decrementAndGet();
        }
        return e;
    }

    @Override
    public boolean remove(Object o) {
        boolean v = queue.remove(o);
        if (v) {
            size.decrementAndGet();
        }
        return v;
    }
    /**
     *  Forces adding an element to the queue, without doing size checks.
     * @param e e
     * @throws InterruptedException e
     */
    public void forcePut(E e) throws InterruptedException {
        size.incrementAndGet();
        try {
            queue.put(e);
        } catch (InterruptedException ie) {
            size.decrementAndGet();
            throw ie;
        }
    }


    @Override
    public boolean offer(E e) {
        int count = size.incrementAndGet();
        if (count > capacity) {
            size.decrementAndGet();
            return false;
        }
        boolean offered = queue.offer(e);
        if (!offered) {
            size.decrementAndGet();
        }
        return offered;
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        // note, not used in ThreadPoolExecutor
        throw new IllegalStateException("offer with timeout not allowed on size queue");
    }

    @Override
    public void put(E e) throws InterruptedException {
        // note, not used in ThreadPoolExecutor
        throw new IllegalStateException("put not allowed on size queue");
    }

    @Override
    public E take() throws InterruptedException {
        E e;
        try {
            e = queue.take();
            size.decrementAndGet();
        } catch (InterruptedException ie) {
            throw ie;
        }
        return e;
    }

    @Override
    public int remainingCapacity() {
        return capacity - size.get();
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        int v = queue.drainTo(c);
        size.addAndGet(-v);
        return v;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        int v = queue.drainTo(c, maxElements);
        size.addAndGet(-v);
        return v;
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) queue.toArray(a);
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }
}
