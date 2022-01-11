package com.acme.mytrader.price;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.mytrader.model.TradeRequest;
import com.acme.mytrader.service.TradingService;

public class PriceListenerImpl implements PriceListener {
    private static final Logger logger = LoggerFactory.getLogger(PriceListenerImpl.class);

    private final TradingService tradingService;

    public PriceListenerImpl(final TradingService tradingService) {
        this.tradingService = requireNonNull(tradingService, "Trading service is required");
    }

    // As this method is listening for price updates, it should wrap the update and send it to trading service with required
    // extra information.
    @Override
    public void priceUpdate(String security, double price) {
        if (null == security || security.isBlank()) {
            logger.warn("Received invalid code");
            return;
        }

        final LocalDateTime time = LocalDateTime.now();
        logger.info("Received price update for security {} with price {}", security, price);
        //Below call should be asynchronous. I.e. Through executor service (i.e. Thread) or if using Spring, then declare trade method as async.
        tradingService.trade(TradeRequest.builder().withCode(security).withCurrentPrice(price).withReceivedAt(time).build());
    }
}