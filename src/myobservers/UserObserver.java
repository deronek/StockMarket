package myobservers;

import stockmarket.events.Event;
import stockmarket.events.Observer;
import stockmarket.events.TradeCompletedEvent;
import stockmarket.events.TradeFailedEvent;

public class UserObserver implements Observer {
    @Override
    public void onEvent(Event event) {
        if(event instanceof TradeCompletedEvent) {
            TradeCompletedEvent knownEvent = (TradeCompletedEvent) event;
            System.out.println("Następujace zlecenie zostało wykonane poprawnie: ");
            System.out.println(knownEvent.getTrade().opis());
        }
        if(event instanceof TradeFailedEvent) {
            TradeFailedEvent knownEvent = (TradeFailedEvent) event;
            System.out.println("Następujące zlecenie nie zostało wykonane z powodu ograniczeń rynkowych i zostało wycofane: ");
            System.out.println(knownEvent.getTrade().opis());
        }
    }
}
