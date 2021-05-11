package stockmarket.exceptions;

public class InvalidPriceException extends Exception {
    private final String name;
    private final int price;

    public InvalidPriceException(String message, String name, int price) {
        super(message);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
