package co.q64.stars.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class Scheduler {
    private Queue<Task> tasks = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getTick(), o2.getTick()));
    private Queue<Task> added = new ConcurrentLinkedQueue<>();
    private AtomicInteger tick = new AtomicInteger();

    protected @Inject Scheduler() {}

    public void tick() {
        while (true) {
            Task task = added.poll();
            if (task == null) {
                break;
            }
            tasks.add(task);
        }
        int current = tick.getAndIncrement();
        while (!tasks.isEmpty() && tasks.peek().getTick() <= current) {
            tasks.poll().getAction().run();
        }
    }

    public void run(Runnable task, int delay) {
        if (delay <= 0) {
            task.run();
            return;
        }
        added.add(new Task(task, tick.get() + delay));
    }

    @Data
    @AllArgsConstructor
    private static class Task {
        private Runnable action;
        private int tick;
    }
}
