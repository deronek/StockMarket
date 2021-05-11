package stockmarket.stock;

public class Share extends AbstractStock {
    public Share(String name) {
        super(name);
    }

    @Override
    public String opis() {
        if(getQuantity() == 0) return null;

        return String.format("Nazwa: %-8sIlość posiadanych udziałów: %-6d%n", getName(), getQuantity());
    }
}
