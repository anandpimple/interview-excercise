package com.acme.mytrader.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TradeRequest implements Serializable {
    private final String code;
    private final Double currentPrice;
    private final LocalDateTime receivedAt;

    protected TradeRequest(final String code,
                           final Double currentPrice,
                           final LocalDateTime receivedAt
    ) {
        this.code = code;
        this.currentPrice = currentPrice;
        this.receivedAt = receivedAt;
    }

    public static TradeRequest.Builder builder() {
        return new TradeRequest.Builder();
    }

    public String getCode() {
        return code;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public static class Builder {
        private String code;
        private Double currentPrice;
        private LocalDateTime receivedAt;

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withCurrentPrice(Double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Builder withReceivedAt(LocalDateTime receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }

        public TradeRequest build() {
            return new TradeRequest(code, currentPrice, receivedAt);
        }
    }
}
