package org.fastddd.core.event.bus;

public class EventBusFactory {

    private static EventBus eventBus = DefaultEventBus.get();

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static void setEventBus(EventBus eventBus) {
        EventBusFactory.eventBus = eventBus;
    }
}
