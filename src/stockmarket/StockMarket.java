package stockmarket;

import stockmarket.exceptions.InvalidNumberOfSharesException;
import stockmarket.exceptions.InvalidPriceException;
import stockmarket.stock.Stock;
import stockmarket.user.Trade;
import stockmarket.user.User;
import stockmarket.util.MyRandom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StockMarket {
    private final HashMap<String, Stock> stocks = new HashMap<>();
    private final HashSet<User> users = new HashSet<>();

    public StockMarket() {
        initializeMarket();
    }

    private void addStockToMap(Stock stock) {
        stocks.put(stock.getName(), stock);
    }

    private void initializeMarket() {
        stocks.clear();
        int numberStocksToAdd = MyRandom.getRandomInt(
                StockMarketSettings.MIN_INITIAL_STOCKS,
                StockMarketSettings.MAX_INITIAL_STOCKS
        );

        int[] stockNamesIndex = MyRandom.getUniqueRandomInts(
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
                    ),
                    MyRandom.getRandomInt(
                            StockMarketSettings.MIN_INITIAL_STOCK_QUANTITY,
                            StockMarketSettings.MAX_INITIAL_STOCK_QUANTITY
                    ));

            addStockToMap(stock);
        }
    }

    public void marketTick() throws InvalidNumberOfSharesException, InvalidPriceException {
        marketFluctuate();
        performPossibleTrades();
    }

    private void performPossibleTrades() throws InvalidNumberOfSharesException, InvalidPriceException {
        for (User user : users) {
            Set<Trade> trades = user.getTrades();
            for (Trade trade : trades) {
                Stock stock = stocks.get(trade.getName());
                if (isPossibleTrade(stock, trade)) performTrade(user, trade, stock);
            }
        }

    }

    private boolean isPossibleTrade(Stock stock, Trade trade) {
        switch (trade.getTradeType()) {
            case BUY -> {
                return stock.getPrice() <= trade.getPrice();
            }
            case SELL -> {
                return stock.getPrice() >= trade.getPrice();
            }
            default -> throw new IllegalStateException("Unexpected value: " + trade.getTradeType());
        }
    }

    private void performTrade(User user, Trade trade, Stock stock) throws InvalidNumberOfSharesException, InvalidPriceException {
        double strength = MyRandom.getGaussianRandomDouble(StockMarketSettings.TRADE_VOLATILITY);
        double change = strength * ((double) trade.getQuantity() / stock.getQuantity()) * trade.getPrice();

        switch (trade.getTradeType()) {
            case BUY -> {
                stock.changePrice((int) change);
                stock.removeShares(trade.getQuantity());
            }
            case SELL -> {
                stock.changePrice((int) -change);
                stock.addShares(trade.getQuantity());
            }
            default -> throw new IllegalStateException("Unexpected value: " + trade.getTradeType());
        }
        user.removeTrade(trade);
    }


    private void marketFluctuate() throws InvalidPriceException {
        for (Stock stock : stocks.values()) {
            stock.randomTick();
        }
    }

    public String opis() {
        StringBuilder s = new StringBuilder();
        s.append("Aktualny stan rynku: ");
        s.append(System.lineSeparator());
        stocks.forEach((name, stock) -> s.append(stock.opis()));

        return s.toString();
    }

    public Set<String> getStockNames() {
        return stocks.keySet();
    }

    public boolean isValidSellTrade(Trade trade) {
        Stock stock = stocks.get(trade.getName());
        return stock.getQuantity() >= trade.getQuantity();
    }

    @Override
    public String toString() {
        return "StockMarket{" +
                "stocks=" + stocks +
                '}';
    }

    public void addUser(User user) {
        users.add(user);
    }
}
