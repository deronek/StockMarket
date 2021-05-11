package stockmarket.stock;

import stockmarket.exceptions.InvalidNumberOfSharesException;

import java.util.Objects;

public abstract class AbstractStock {
    private final String name;
    private int quantity;

    protected AbstractStock(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Nazwa nie moze byc pusta");
        this.name = name;
    }

    protected AbstractStock(String name, int quantity) {
        this(name);
        try {
            setQuantity(quantity);
        } catch (InvalidNumberOfSharesException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    private void setQuantity(int quantity) throws InvalidNumberOfSharesException {
        if(quantity < 0) throw new InvalidNumberOfSharesException("Proba ustawienia liczby udzialow poza zakresem", name, quantity);

        this.quantity = quantity;
    }

    public void addShares(int quantity) throws InvalidNumberOfSharesException {
        setQuantity(getQuantity() + quantity);
    }

    public void removeShares(int quantity) throws InvalidNumberOfSharesException {
        setQuantity(getQuantity() - quantity);
    }

    public abstract String opis();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractStock)) return false;
        AbstractStock that = (AbstractStock) o;
        return getQuantity() == that.getQuantity() && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity());
    }
}
