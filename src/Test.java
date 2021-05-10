import stockmarket.Stock;
import stockmarket.StockMarketSettings;
import stockmarket.StockMarket;

public class Test {

    public static void printMenu() {
        System.out.println("Wybierz akcje: ");
        System.out.println("#1: Drukuj stan giełdy co " + StockMarketSettings.TICK_PERIOD_SECONDS + " sekund");
    }

    public static void main (String[] args) {
        StockMarket stockMarket = new StockMarket();

        //printMenu();
        UserInput userInput = new UserInput(stockMarket);
        userInput.askUserInput();

        return;
    }
}
