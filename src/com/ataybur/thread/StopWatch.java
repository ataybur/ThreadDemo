package com.ataybur.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class StopWatch {

    private volatile long beginTime;
    private final AtomicLong lastLap = new AtomicLong();
    private final AtomicReference<List<Long>> laps = new AtomicReference<List<Long>>();

    public synchronized void start() {
        reset();
        beginTime = System.nanoTime();
        lastLap.set(beginTime);
    }

    public long timeElapsed() {
        return toMilliseconds(timeElapsed(beginTime));
    }

    public void lap() {
        long lap = timeElapsed(lastLap.longValue());
        lastLap.addAndGet(lap);
        getLaps().add(toMilliseconds(lap));
    }

    public synchronized void reset() {
        beginTime = 0;
        lastLap.set(0);
        if (laps.get() != null) {
            laps.get().clear();
        }
    }

    public List<Long> getLaps() {
        laps.compareAndSet(null, Collections.synchronizedList(new ArrayList<Long>()));
        return laps.get();
    }
    
    public void printLaps() {
        getLaps().forEach(System.out::println);
    }

    private long toMilliseconds(long nano) {
        return nano / 1000000;
    }

    private long timeElapsed(long start) {
        return System.nanoTime() - start;
    }
}
