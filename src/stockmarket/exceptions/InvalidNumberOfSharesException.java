package stockmarket.exceptions;

public class InvalidNumberOfSharesException extends Exception {
    private final String name;
    private final int quantity;

    public InvalidNumberOfSharesException(String message, String name, int quantity) {
        super(message);
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
