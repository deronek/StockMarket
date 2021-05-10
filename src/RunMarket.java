import stockmarket.StockMarket;
import stockmarket.TickTask;
import stockmarket.exceptions.InvalidPriceException;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class RunMarket implements Runnable {

    private final TickTask tickTask;
    private final PrintMarketTask printMarketTask;
    private final StockMarket stockMarket;

    public RunMarket(TickTask tickTask, StockMarket stockMarket) {
        this.tickTask = tickTask;
        this.stockMarket = stockMarket;
        printMarketTask = new PrintMarketTask(stockMarket);
    }

    @Override
    public void run() {
        try {
            tickTask.start(stockMarket);
            printMarketTask.start();
            tickTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            Throwable cause = tickTask.getCause();

            if(cause instanceof InvalidPriceException) {
                InvalidPriceException knownCause = (InvalidPriceException) cause;
                System.out.println("Giełda zostala zatrzymana przez przekroczenie granicy ceny.");
                System.out.println(
                        "Próba ustawienia ceny " + knownCause.getStockPrice() +
                        " dla udziału " + knownCause.getStockName() + '.'
                );
            }
            else {
                e.printStackTrace();
            }
        } finally {
            printMarketTask.stop();
        }
    }
}
