package stockmarket.exceptions;

import stockmarket.user.TradeType;

public class InvalidTradeQuantityException extends Exception {
    private final String name;
    private final TradeType tradeType;
    private final int quantity;

    public InvalidTradeQuantityException(String message, String name, TradeType tradeType, int quantity) {
        super(message);
        this.name = name;
        this.tradeType = tradeType;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public TradeType getTradeType() {
        return tradeType;
    }
}
