package stockmarket.events;

public interface Observer {
    void onEvent(Event event);
}