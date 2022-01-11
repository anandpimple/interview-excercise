package com.acme.mytrader.strategy;

import java.io.Serializable;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements Serializable {
    private final Double triggerStartRange;
    private final Double triggerEndRange;
    private final Integer lotSize;
    private final TradingType tradingType;
    private final String code;
    private TradingStrategy(final Builder builder) {

        this.triggerStartRange = builder.triggerStartRange;
        this.triggerEndRange = builder.triggerEndRange;
        this.lotSize = builder.lotSize;
        this.tradingType = builder.tradingType;
        this.code = builder.code;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Double getTriggerStartRange() {
        return triggerStartRange;
    }

    public Double getTriggerEndRange() {
        return triggerEndRange;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public TradingType getTradingType() {
        return tradingType;
    }

    public String getCode() {
        return code;
    }

    public enum TradingType {
        BUY, SELL
    }

    public static class Builder {
        private Double triggerStartRange;
        private Double triggerEndRange;
        private Integer lotSize;
        private TradingType tradingType;
        private String code;

        private Builder() {

        }

        public Builder withTriggerStartRange(Double triggerStartRange) {
            this.triggerStartRange = triggerStartRange;
            return this;
        }

        public Builder withTriggerEndRange(Double triggerEndRange) {
            this.triggerEndRange = triggerEndRange;
            return this;
        }

        public Builder withLotSize(Integer lotSize) {
            this.lotSize = lotSize;
            return this;
        }

        public Builder withTradingType(TradingType tradingType) {
            this.tradingType = tradingType;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public TradingStrategy build() {
            return new TradingStrategy(this);
        }

    }
}
