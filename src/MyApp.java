import myobservers.MarketObserver;
import myobservers.UserObserver;
import stockmarket.user.Share;
import stockmarket.StockMarket;
import stockmarket.TickTask;
import stockmarket.exceptions.InvalidTradeQuantityException;
import stockmarket.exceptions.NoSuchStockException;
import stockmarket.user.Trade;
import stockmarket.user.TradeType;
import stockmarket.user.User;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MyApp {
    private static final String BLEDNY_WYBOR = "Bledny wybor.";
    private static final String PODANO_BLEDNE_DANE = "Podano błędne dane.";
    private static final Scanner scanner = new Scanner(System.in);

    private final StockMarket stockMarket;
    private final TickTask tickTask;
    private final PrintMarketTask printMarketTask;
    private final User user;

    public MyApp() {
        this.stockMarket = new StockMarket();

        this.tickTask = new TickTask(stockMarket);
        tickTask.addObserver(new MarketObserver());

        printMarketTask = new PrintMarketTask(stockMarket);

        this.user = new User(stockMarket);
        stockMarket.addUser(user);
        user.addObserver(new UserObserver());
    }

    public static void main(String[] args) {
        MyApp myApp = new MyApp();
        myApp.askUserInput();
    }

    public void askUserInput() {
        //noinspection InfiniteLoopStatement
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            parseUserInput(input);
        }
    }

    private void printMenu() {
        System.out.println("Wybierz akcje: ");
        System.out.println("#1: Uruchom/zatrzymaj giełdę");
        System.out.println("#2: Pokaż stan giełdy");
        System.out.println("#3: Dodaj zlecenie kupna");
        System.out.println("#4: Dodaj zlecenie sprzedazy");
        System.out.println("#5: Pokaż zlecenia");
        System.out.println("#6: Pokaz posiadane udziały");
    }

    public void parseUserInput(String s) {
        try {
            int input = Integer.parseInt(s);
            switch (input) {
                case 1 -> {
                    startMarket();
                    scanner.nextLine();
                    stopMarket();
                }
                case 2 -> System.out.println(stockMarket.opis());
                case 3 -> addTrade(TradeType.BUY);
                case 4 -> addTrade(TradeType.SELL);
                case 5 -> printTrades();
                case 6 -> printShares();
                default -> System.out.println(BLEDNY_WYBOR);
            }
        } catch (NumberFormatException e) {
            System.out.println(BLEDNY_WYBOR);
        }
    }

    private void stopMarket() {
        tickTask.stop();
        printMarketTask.stop();
    }

    private void startMarket() {
        tickTask.start();
        printMarketTask.start();
    }

    private void printShares() {
        Map<String, Share> shares = user.getShares();
        for (Share share : shares.values()) {
            if (share.getQuantity() != 0) System.out.println(share.opis());
        }
    }

    private void printTrades() {
        Set<Trade> trades = user.getTrades();
        for (Trade trade : trades) {
            System.out.println(trade.opis());
        }
    }

    private void addTrade(TradeType tradeType) {
        try {
            System.out.println("Podaj nazwę emitenta: ");
            String name = scanner.nextLine();
            System.out.println("Podaj cene: ");
            int price = nextInt();
            System.out.println("Podaj ilosc akcji: ");
            int quantity = nextInt();
            Trade trade = new Trade(
                    name,
                    tradeType,
                    price,
                    quantity
            );
            user.addTrade(trade);
            System.out.println("Dodano zlecenie: ");
            System.out.println(trade.opis());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println(PODANO_BLEDNE_DANE);
        } catch (NoSuchStockException e) {
            System.out.println("Taki emitent nie istnieje.");
        } catch (InvalidTradeQuantityException e) {
            switch (e.getTradeType()) {
                case BUY -> System.out.println("Zbyt mała ilość udziałów dostępnych na rynku.");
                case SELL -> System.out.println("Zbyt mała ilość udziałów na koncie użytkownika.");
            }
        }
    }

    private int nextInt() {
        return Integer.parseInt(scanner.nextLine());
    }
}
