package stockmarket.events;

import stockmarket.user.Trade;

public class TradeFailedEvent implements Event {
    private final Trade trade;

    public TradeFailedEvent(Trade trade) {
        this.trade = trade;
    }

    public Trade getTrade() {
        return trade;
    }
}
