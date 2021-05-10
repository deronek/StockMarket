package stockmarket;

import stockmarket.exceptions.InvalidPriceException;

import java.util.HashMap;
import java.util.HashSet;

public class StockMarket {
    private final HashMap<String, Stock> stocks = new HashMap<>();
    private final HashSet<Trade> trades = new HashSet<>();

    public StockMarket() {
        intializeMarket();
    }

    private void addStockToMap(Stock stock) {
        stocks.put(stock.getName(), stock);
    }

    public Stock getStock(String name) {
        return stocks.get(name);
    }

    private void intializeMarket() {
        stocks.clear();
        int numberStocksToAdd = MyRandom.getRandomInt(
                StockMarketSettings.MIN_INITIAL_STOCKS,
                StockMarketSettings.MAX_INITIAL_STOCKS
        );

        int[] stockNamesIndex = MyRandom.getRandomInts(
                numberStocksToAdd,
                0,
                StockMarketSettings.STOCK_NAMES.length - 1
        );

        for (int i : stockNamesIndex) {
            Stock stock = new Stock(
                    StockMarketSettings.STOCK_NAMES[i],
                    MyRandom.getRandomInt(
                            StockMarketSettings.MIN_INTIAL_PRICE,
                            StockMarketSettings.MAX_INTIAL_PRICE
                    ));

            addStockToMap(stock);
        }
    }

    public void randomTick() throws InvalidPriceException {
        for (Stock stock : stocks.values()) {
            stock.randomTick();
        }
    }

    public String stan() {
        StringBuilder s = new StringBuilder();
        s.append("Aktualny stan rynku: ");
        s.append(System.lineSeparator());
        stocks.forEach((name, stock) -> s.append(stock.stan()));

        return s.toString();
    }

    public void addStock(Stock stock) {
        addStockToMap(stock);
    }

    public void addTrade(String stockName, TradeType tradeType, int price) {

    }

    @Override
    public String toString() {
        return "StockMarket{" +
                "stocks=" + stocks +
                '}';
    }
}
