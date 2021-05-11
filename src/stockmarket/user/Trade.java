package stockmarket.user;

import stockmarket.stock.AbstractStock;

public class Trade extends AbstractStock {
    private final TradeType tradeType;
    private final int price;

    public Trade(String name, TradeType tradeType, int price, int quantity) {
        super(name, quantity);

        if (tradeType == null) throw new IllegalArgumentException("Typ wymiany nie moze byc null");
        if (price <= 0) throw new IllegalArgumentException("Cena musi być dodatnia");

        this.tradeType = tradeType;
        this.price = price;
    }

    public String opis() {
        StringBuilder s = new StringBuilder();

        s.append("Typ transakcji: ");
        switch(tradeType) {
            case BUY -> s.append("kupno");
            case SELL -> s.append("sprzedaż");
        }
        s.append(System.lineSeparator());

        s.append("Emitent: ");
        s.append(getName());
        s.append(System.lineSeparator());

        s.append("Cena: ");
        s.append(getPrice());
        s.append(System.lineSeparator());

        s.append("Ilość udziałów: ");
        s.append(getQuantity());
        s.append(System.lineSeparator());

        return s.toString();
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public int getPrice() {
        return price;
    }
}
