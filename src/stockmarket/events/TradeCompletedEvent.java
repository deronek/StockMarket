package stockmarket.events;

import stockmarket.user.Trade;

public class TradeCompletedEvent implements Event {
    private final Trade trade;

    public TradeCompletedEvent(Trade trade) {
        this.trade = trade;
    }

    public Trade getTrade() {
        return trade;
    }
}
