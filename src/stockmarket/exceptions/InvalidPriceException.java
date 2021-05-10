package stockmarket.exceptions;

public class InvalidPriceException extends Exception {
    private final String stockName;
    private final int stockPrice;

    public InvalidPriceException(String message, String stockName, int stockPrice) {
        super(message);
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public String getStockName() {
        return stockName;
    }

    public int getStockPrice() {
        return stockPrice;
    }
}
