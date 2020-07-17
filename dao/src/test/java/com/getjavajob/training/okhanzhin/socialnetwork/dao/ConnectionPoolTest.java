package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import org.junit.Test;

import java.sql.Connection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    @Test
    public void getConnection() {
        ExecutorService service = Executors.newFixedThreadPool(20);
        ConnectionPool pool = ConnectionPool.getPool(2);
        AtomicInteger counter = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(20);

        Runnable task = () -> {
            Connection connection = pool.getConnection();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter.incrementAndGet();
            pool.returnConnection(connection);
            latch.countDown();
        };

        for (int i = 0; i < 20; i++) {
            service.submit(task);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(20, counter.get());
    }
}