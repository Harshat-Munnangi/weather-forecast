package org.spond.weather.util;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindowLogRateLimiter {

    private final int maxRequests;
    private final long timeWindowInMillis;
    private final Deque<Long> requestTimestamps;

    public SlidingWindowLogRateLimiter(int maxRequests, long timeWindowInMillis) {
        this.maxRequests = maxRequests;
        this.timeWindowInMillis = timeWindowInMillis;
        this.requestTimestamps = new LinkedList<>();
    }

    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long earliestTime = now - timeWindowInMillis;

        while (!requestTimestamps.isEmpty() && requestTimestamps.getFirst() < earliestTime) {
            requestTimestamps.pollFirst();
        }

        if (requestTimestamps.size() < maxRequests) {
            requestTimestamps.addLast(now);
            return true;
        }

        return false;
    }
}
