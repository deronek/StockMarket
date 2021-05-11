package myobservers;

import stockmarket.exceptions.InvalidPriceException;
import stockmarket.events.Event;
import stockmarket.events.MarketStoppedEvent;
import stockmarket.events.Observer;

public class MarketObserver implements Observer {
    @Override
    public void onEvent(Event event) {
        if(event instanceof MarketStoppedEvent) {
            MarketStoppedEvent knownEvent = (MarketStoppedEvent) event;
            Throwable cause = knownEvent.getCause();
            if(cause instanceof InvalidPriceException) {
                InvalidPriceException knownCause = (InvalidPriceException) cause;
                System.out.println("Giełda zostala zatrzymana przez przekroczenie granicy ceny.");
                System.out.println(
                        "Próba ustawienia ceny " + knownCause.getPrice() +
                                " dla udziału " + knownCause.getName() + '.'
                );
            }
            else throw new RuntimeException("Giełda zatrzymana przez nieznany wyjątek", cause);
        }
    }
}
