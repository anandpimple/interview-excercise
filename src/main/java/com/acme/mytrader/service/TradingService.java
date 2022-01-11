package com.acme.mytrader.service;

import com.acme.mytrader.model.TradeRequest;

public interface TradingService {
    void trade(final TradeRequest request);
}
