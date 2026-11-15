package org.route.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Money {

        public static final BigDecimal ZERO =
                new BigDecimal("0.00");

        private Money() {
        }

        public static BigDecimal scale(BigDecimal amount) {
            return amount.setScale(2, RoundingMode.HALF_UP);
        }

        public static BigDecimal of(String value) {
            return scale(new BigDecimal(value));
        }

        public static String format(BigDecimal amount) {
            DecimalFormat formatter =
                    new DecimalFormat(
                            "#,##0.00",
                            DecimalFormatSymbols.getInstance(Locale.US)
                    );

            return formatter.format(scale(amount));
        }
    }

