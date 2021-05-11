package stockmarket;

import stockmarket.events.Event;
import stockmarket.events.MarketStoppedEvent;
import stockmarket.events.Observable;
import stockmarket.events.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TickTask implements Observable {
    private static final int INITIAL_DELAY = 0;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private final StockMarket stockMarket;
    private ScheduledFuture<?> tickerHandle;
    private final List<Observer> observers = new ArrayList<>();

    public TickTask(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void start() {
        Runnable ticker = () -> {
            try {
                stockMarket.marketTick();
            } catch (Throwable e) {
                MarketStoppedEvent event = new MarketStoppedEvent(e);
                notifyObservers(event);
                tickerHandle.cancel(true);
            }
        };

        this.tickerHandle = scheduler.scheduleAtFixedRate(
                ticker,
                INITIAL_DELAY,
                StockMarketSettings.TICK_PERIOD_SECONDS,
                TimeUnit.SECONDS);
    }

    public void stop() {
        tickerHandle.cancel(false);
    }

    public boolean isRunning() {
        if (tickerHandle == null) return false;
        else return !tickerHandle.isCancelled();
    }

    public Object get() throws InterruptedException, ExecutionException, CancellationException {
        return tickerHandle.get();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Event e) {
        for (Observer o : observers) {
            o.onEvent(e);
        }
    }
}
