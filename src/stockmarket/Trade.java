package stockmarket;

public class Trade {
    private final String stockName;
    private final TradeType tradeType;
    private final int price;
    private final int quantity;

    public Trade(String stockName, TradeType tradeType, int price, int quantity) {
        this.stockName = stockName;
        this.tradeType = tradeType;
        this.price = price;
        this.quantity = quantity;
    }

    public String getStockName() {
        return stockName;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public int getPrice() {
        return price;
    }
}
