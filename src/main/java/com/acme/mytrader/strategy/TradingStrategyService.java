package com.acme.mytrader.strategy;

import java.util.Optional;

public interface TradingStrategyService {
    /**
     * This service is expected to return only one strategy for given code and strategy range is matching the price.
     *
     * @param code  Code for asset/stock/security
     * @param price price
     * @return Optional of TradingStrategy or Optional of Empty
     */
    Optional<TradingStrategy> findOneTradingStrategyByCodeAndPrice(final String code, final Double price);

    /**
     * To add new strategy to the system
     *
     * @param strategy
     */
    void addStrategy(final TradingStrategy strategy);
}
