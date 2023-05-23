package net.buj.rml.events;

import java.util.*;

public final class EventLoop {
    private List<Runnable> queue = new ArrayList<>();

    public synchronized void runOnMainThread(Runnable runnable) {
        queue.add(runnable);
    }

    public void runQueuedFns() {
        List<Runnable> list = new ArrayList<>(queue);
        queue.clear();
        for (Runnable r : list) {
            r.run();
        }
    }

    private static class EventListenerHandle<T extends Event> {
        public EventListener<T> listener;
        public Event.Priority priority;
    }

    public static interface EventListener<T extends Event> {
        public void run(T event);
    }

    private Map<Class<? extends Event>, List<EventListenerHandle<? extends Event>>> events = new HashMap<>();

    @SuppressWarnings("unchecked") // I know what I'm doing
    public <T extends Event> T emit(T event) {
        Class<? extends Event> klass = event.getClass();
        List<EventListenerHandle<? extends Event>> list = events.getOrDefault(klass, new ArrayList<>());
        List<EventListener<T>>[] listeners = new ArrayList[Event.Priority.values().length];
        for (int i = 0; i < 5; i++) {
            listeners[i] = new ArrayList<>();
        }
        for (EventListenerHandle<? extends Event> handler : list) {
            listeners[handler.priority.ordinal()].add((EventListener<T>) handler.listener);
            if (event.isCancelled()) return event;
        }
        for (int i = 0; i < 5; i++) {
            for (EventListener<T> handler : listeners[i]) {
                handler.run(event);
                if (event.isCancelled()) return event;
            }
        }
        return event;
    }

    public <T extends Event> void listen(Class<T> klass, EventListener<T> listener) {
        listen(klass, listener, Event.Priority.NORMAL);
    }

    public <T extends Event> void listen(Class<T> klass, EventListener<T> listener, Event.Priority priority) {
        List<EventListenerHandle<? extends Event>> list = events.getOrDefault(klass, new ArrayList<>());
        EventListenerHandle<T> handle = new EventListenerHandle<>();
        handle.priority = priority;
        handle.listener = listener;
        list.add(handle);
        events.putIfAbsent(klass, list);
    }
}
