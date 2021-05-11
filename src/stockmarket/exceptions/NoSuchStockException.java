package stockmarket.exceptions;

public class NoSuchStockException extends Exception {
    private final String name;

    public NoSuchStockException(String message, String name) {
        super(message);
        this.name = name;
    }
}
