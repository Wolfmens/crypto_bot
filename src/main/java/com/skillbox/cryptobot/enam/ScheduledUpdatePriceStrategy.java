package com.skillbox.cryptobot.enam;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public enum ScheduledUpdatePriceStrategy {
    UPDATE_PRICE {
        @Override
        public Duration getDuration(Long value, TimeUnit timeUnit) {
            long millis = timeUnit.toMillis(value);

            return Duration.ofMillis(millis);
        }
    },
    NOTICE_PRICE {
        @Override
        public Duration getDuration(Long value, TimeUnit timeUnit) {
            long millis = timeUnit.toMillis(value);

            return Duration.ofMillis(millis);
        }
    };

    public abstract Duration getDuration(Long value, TimeUnit timeUnit);

}
