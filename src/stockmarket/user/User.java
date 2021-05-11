package stockmarket.user;

import stockmarket.stock.Share;
import stockmarket.StockMarket;
import stockmarket.exceptions.InvalidNumberOfSharesException;
import stockmarket.exceptions.InvalidTradeQuantityException;
import stockmarket.exceptions.NoSuchStockException;
import stockmarket.events.Observable;
import stockmarket.events.Observer;
import stockmarket.events.*;

import java.util.*;

public class User implements Observable {
    private final StockMarket stockMarket;
    private final UUID uuid;
    private final HashMap<String, Share> shares = new HashMap<>();
    private final HashSet<Trade> trades = new HashSet<>();
    private final List<Observer> observers = new ArrayList<>();

    public User(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
        this.uuid = UUID.randomUUID();
        for (String s : stockMarket.getStockNames()) {
            shares.put(s, new Share(s));
        }
    }

    public Set<Trade> getTrades() {
        return new HashSet<>(trades);
    }

    public Map<String, Share> getShares() {
        return new HashMap<>(shares);
    }

    public void removeTrade(Trade trade) {
        try {
            Share share = shares.get(trade.getName());

            switch (trade.getTradeType()) {
                case BUY -> share.addShares(trade.getQuantity());
                case SELL -> share.removeShares(trade.getQuantity());
            }
            notifyObservers(new TradeCompletedEvent(trade));
        } catch (InvalidNumberOfSharesException e) {
            notifyObservers(new TradeFailedEvent(trade));
        } finally {
            trades.remove(trade);
        }

    }

    public void addTrade(Trade trade) throws NoSuchStockException, InvalidTradeQuantityException {
        if (!shares.containsKey(trade.getName()))
            throw new NoSuchStockException("Próba dodania zlecenia dla nieistniejącego emitenta", trade.getName());
        switch (trade.getTradeType()) {
            case BUY -> {
                if (!stockMarket.isValidSellTrade(trade))
                    throw new InvalidTradeQuantityException(
                            "Próba dodania zlecenia sprzedaży dla nieprawidłowej ilości udziałów na rynku",
                            trade.getName(),
                            trade.getTradeType(),
                            trade.getQuantity());
            }
            case SELL -> {
                if (!isValidBuyTrade(trade))
                    throw new InvalidTradeQuantityException(
                            "Próba dodania zlecenia kupna dla nieprawidłowej ilości udziałów użytkownika",
                            trade.getName(),
                            trade.getTradeType(),
                            trade.getQuantity());
            }

            default -> throw new IllegalStateException("Unexpected value: " + trade.getTradeType());
        }
        trades.add(trade);
    }

    private boolean isValidBuyTrade(Trade trade) {
        Share share = shares.get(trade.getName());
        return share.getQuantity() >= trade.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
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
