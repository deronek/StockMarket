package stockmarket.events;

public class MarketStoppedEvent implements Event {
    private final Throwable cause;

    public MarketStoppedEvent(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }
}
