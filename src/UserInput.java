import stockmarket.StockMarket;
import stockmarket.TickTask;

import java.util.Scanner;

public class UserInput {
    private static final String BLEDNY_WYBOR = "Bledny wybor";
    private static final Scanner scanner = new Scanner(System.in);

    private final StockMarket stockMarket;
    private final TickTask tickTask;

    public UserInput(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
        this.tickTask = new TickTask(stockMarket);
    }

    public void askUserInput() {
        while (true) {
            printMenu();

            String input = scanner.nextLine();

            parseUserInput(input);
        }
    }

    private void printMenu() {
        System.out.print("Giełda uruchomiona: ");
        if (tickTask.isRunning())
            System.out.println("TAK");
        else
            System.out.println("NIE");

        System.out.println("Wybierz akcje: ");
        System.out.println("#1: Uruchom/zatrzymaj giełdę");
        System.out.println("#2: Wydrukuj stan giełdy");
    }

    public void parseUserInput(String s) {
        try {
            int input = Integer.parseInt(s);
            switch (input) {
                case 1 -> {
                    Thread t = new Thread(
                            new RunMarket(
                                    tickTask,
                                    stockMarket));
                    t.start();
                    scanner.nextLine();
                    tickTask.stop();
                }
                case 2 -> System.out.println(stockMarket.stan());
                default -> System.out.println(BLEDNY_WYBOR);
            }
        } catch (NumberFormatException e) {
            System.out.println(BLEDNY_WYBOR);
        }
    }
}
