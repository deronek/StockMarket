import stockmarket.StockMarketSettings;
import stockmarket.StockMarket;

import java.util.concurrent.*;

public class PrintMarketTask {
    private static final int INITIAL_DELAY = 0;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private final StockMarket stockMarket;
    private ScheduledFuture<?> tickerHandle;

    public PrintMarketTask(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void start() {
        Runnable ticker = () -> {
            System.out.println(stockMarket.stan());
        };

        this.tickerHandle = scheduler.scheduleAtFixedRate(
                ticker,
                INITIAL_DELAY,
                StockMarketSettings.TICK_PERIOD_SECONDS,
                TimeUnit.SECONDS);
    }

    public void stop() {
        tickerHandle.cancel(true);
    }

    public boolean isRunning() {
        if (tickerHandle == null) return false;
        else return !tickerHandle.isCancelled();
    }

    public Object get() throws InterruptedException, ExecutionException {
        return tickerHandle.get();
    }
}
