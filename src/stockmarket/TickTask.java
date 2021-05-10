package stockmarket;

import java.util.concurrent.*;

public class TickTask {
    private static final int INITIAL_DELAY = 0;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private final StockMarket stockMarket;
    private ScheduledFuture<?> tickerHandle;
    private Throwable cause;

    public TickTask(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void start(StockMarket stockMarket) {
        Runnable ticker = () -> {
            try {
                stockMarket.randomTick();
            } catch (Throwable e) {
                cause = e;
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

    public Throwable getCause() {
        return cause;
    }
}
