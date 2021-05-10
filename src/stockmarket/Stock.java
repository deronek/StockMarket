package stockmarket;

import stockmarket.exceptions.InvalidPriceException;

public class Stock {
    private final String name;
    private int price;
    private int change;
    private int quantity;

    public Stock(String name, int price) {
        try {
            if (name == null) throw new IllegalArgumentException("Nazwa nie może być pusta");
            this.name = name;
            setPrice(price);
        } catch (InvalidPriceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    private void setPrice(int price) throws InvalidPriceException {
        if (price > StockMarketSettings.MAX_PRICE || price < StockMarketSettings.MIN_PRICE) {
            throw new InvalidPriceException("Próba ustawienia ceny poza zakresem", name, price);
        }
        this.price = price;
    }

    public int getChange() {
        return change;
    }

    private void setChange(int change) {
        this.change = change;
    }

    public void randomTick() throws InvalidPriceException {
        double maxFluctiation = price * StockMarketSettings.RANDOM_TICK_FLUCTUATION;
        double change = MyRandom.getGaussianRandomDouble(maxFluctiation);
        setPrice(getPrice() + (int) change);
        setChange((int) change);
    }

    public String stan() {
        String changeFormatted = (change > 0 ? "+" : "") + change;
        return String.format("Nazwa: %-8sCena: %-6d(%s)%n", name, price, changeFormatted);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
