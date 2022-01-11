package com.acme.mytrader.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.model.TradeRequest;
import com.acme.mytrader.strategy.TradingStrategy;
import com.acme.mytrader.strategy.TradingStrategyService;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @Mock
    private TradingStrategyService tradingStrategyService;

    @Mock
    private ExecutionService executionService;

    @Mock
    private TradeRequest tradeRequest;

    @Mock
    private TradingStrategy strategy;

    @InjectMocks
    private TradeServiceImpl underTest;

    @Test
    void givenTradingStrategyServiceNull_whenCreateTradeServiceImpl_thenException() {
        assertThatThrownBy(() -> new TradeServiceImpl(null, executionService))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Trading strategy service is required");
    }

    @Test
    void givenExecutionServiceNull_whenCreateTradeServiceImpl_thenException() {
        assertThatThrownBy(() -> new TradeServiceImpl(tradingStrategyService, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Execution service is required");
    }

    @Test
    void givenEmptyStrategy_whenFindOneTradingStrategyByCodeAndPrice_thenNoBuyOrSellInvoked() {
        when(tradeRequest.getCode()).thenReturn("Code");
        when(tradeRequest.getCurrentPrice()).thenReturn(10D);

        when(tradingStrategyService.findOneTradingStrategyByCodeAndPrice("Code", 10D)).thenReturn(Optional.empty());

        underTest.trade(tradeRequest);

        verifyNoMoreInteractions(executionService);
    }

    @Test
    void givenBuyStrategy_whenFindOneTradingStrategyByCodeAndPrice_thenNoBuyOrSellInvoked() {
        when(tradeRequest.getCode()).thenReturn("Code");
        when(tradeRequest.getCurrentPrice()).thenReturn(10D);

        when(strategy.getTradingType()).thenReturn(TradingStrategy.TradingType.BUY);
        when(strategy.getCode()).thenReturn("Code");
        when(strategy.getLotSize()).thenReturn(100);

        when(tradingStrategyService.findOneTradingStrategyByCodeAndPrice("Code", 10D)).thenReturn(Optional.of(strategy));

        underTest.trade(tradeRequest);

        verify(executionService).buy("Code", 10D, 100);
        verifyNoMoreInteractions(executionService);
    }

    @Test
    void givenSellStrategy_whenFindOneTradingStrategyByCodeAndPrice_thenNoBuyOrSellInvoked() {
        when(tradeRequest.getCode()).thenReturn("Code");
        when(tradeRequest.getCurrentPrice()).thenReturn(10D);

        when(strategy.getTradingType()).thenReturn(TradingStrategy.TradingType.SELL);
        when(strategy.getCode()).thenReturn("Code");
        when(strategy.getLotSize()).thenReturn(100);

        when(tradingStrategyService.findOneTradingStrategyByCodeAndPrice("Code", 10D)).thenReturn(Optional.of(strategy));

        underTest.trade(tradeRequest);

        verify(executionService).sell("Code", 10D, 100);
        verifyNoMoreInteractions(executionService);
    }

}
