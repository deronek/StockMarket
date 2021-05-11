package stockmarket.stock;

import stockmarket.StockMarketSettings;
import stockmarket.exceptions.InvalidPriceException;
import stockmarket.util.MyRandom;

import java.util.Objects;

public class Stock extends AbstractStock {
    private int price;
    private int change;

    public Stock(String name, int price, int quantity) {
        super(name, quantity);
        try {
            setPrice(price);
        } catch (InvalidPriceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public int getPrice() {
        return price;
    }

    public int getChange() {
        return change;
    }

    public void randomTick() throws InvalidPriceException {
        double maxFluctiation = price * StockMarketSettings.RANDOM_TICK_FLUCTUATION;
        double change = MyRandom.getGaussianRandomDouble(maxFluctiation);
        changePrice((int) change);
    }

    private void setPrice(int price) throws InvalidPriceException {
        if (price > StockMarketSettings.MAX_PRICE || price < StockMarketSettings.MIN_PRICE) {
            throw new InvalidPriceException("Próba ustawienia ceny poza zakresem", getName(), price);
        }
        this.price = price;
    }

    public void changePrice(int change) throws InvalidPriceException {
        int newPrice = price + change;
        setPrice(newPrice);
        this.change = change;
    }

    public String opis() {
        String changeFormatted = '(' + (change > 0 ? "+" : "") + change + ')';
        return String.format("Nazwa: %-8sCena: %-6d%-10sIlość dostępnych udziałów: %-6d%n", getName(), getPrice(), changeFormatted, getQuantity());
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + getName() + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        if (!super.equals(o)) return false;
        Stock stock = (Stock) o;
        return getPrice() == stock.getPrice() && getChange() == stock.getChange();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrice(), getChange());
    }
}
