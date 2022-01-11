package com.acme.mytrader.service;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.model.TradeRequest;
import com.acme.mytrader.strategy.TradingStrategy;
import com.acme.mytrader.strategy.TradingStrategyService;

public class TradeServiceImpl implements TradingService {
    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    private final TradingStrategyService tradingStrategyService;
    private final ExecutionService executionService;

    public TradeServiceImpl(final TradingStrategyService tradingStrategyService,
                            final ExecutionService executionService) {
        this.tradingStrategyService = requireNonNull(tradingStrategyService, "Trading strategy service is required");
        this.executionService = requireNonNull(executionService, "Execution service is required");
    }

    @Override
    public void trade(final TradeRequest request) {
        logger.info("Received Trade request for asset code {} with price {} at {}", request.getCode(), request.getCurrentPrice(), request.getReceivedAt());

        tradingStrategyService
            .findOneTradingStrategyByCodeAndPrice(request.getCode(), request.getCurrentPrice())
            .ifPresentOrElse(
                strategy -> processTrade(request, strategy),
                () -> processForStrategyNotFound(request)
            );
    }

    private void processTrade(final TradeRequest tradeRequest,
                              final TradingStrategy strategy) {
        //Not used Java 12 switch case to support JDK 8 and above
        switch (strategy.getTradingType()) {
            case BUY: {
                executionService.buy(strategy.getCode(), tradeRequest.getCurrentPrice(), strategy.getLotSize());
                break;
            }
            case SELL: {
                executionService.sell(strategy.getCode(), tradeRequest.getCurrentPrice(), strategy.getLotSize());
                break;
            }
        }
    }

    private void processForStrategyNotFound(final TradeRequest tradeRequest) {
        logger.info("Not strategy fount for code {} with price {} at {}", tradeRequest.getCode(), tradeRequest.getCurrentPrice(), tradeRequest.getReceivedAt());
    }
}
