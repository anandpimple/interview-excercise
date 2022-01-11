package com.acme.mytrader.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.mytrader.model.TradeRequest;
import com.acme.mytrader.service.TradingService;

@ExtendWith(MockitoExtension.class)
class PriceListenerImplTest {
    @Mock
    private TradingService tradingService;

    @Captor
    private ArgumentCaptor<TradeRequest> tradeRequestArgumentCaptor;

    @InjectMocks
    private PriceListenerImpl underTest;

    @Test
    void givenTradingServiceNull_whenCreatePriceListenerImpl_thenException() {
        assertThatThrownBy(() -> new PriceListenerImpl(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Trading service is required");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void givenInvalidCode_whenPriceUpdate_thenTradingProcessNotInvoked(final String code) {
        underTest.priceUpdate(code, 10);

        verify(tradingService, never()).trade(any(TradeRequest.class));
    }

    @Test
    void givenProperCode_whenPriceUpdate_thenTradingProcessInvoked() {
        doNothing().when(tradingService).trade(tradeRequestArgumentCaptor.capture());

        underTest.priceUpdate("Test", 10);

        assertThat(tradeRequestArgumentCaptor.getValue().getCode()).isEqualTo("Test");
        assertThat(tradeRequestArgumentCaptor.getValue().getCurrentPrice()).isEqualTo(10);
        assertThat(tradeRequestArgumentCaptor.getValue().getReceivedAt()).isEqualToIgnoringNanos(LocalDateTime.now());
    }


}