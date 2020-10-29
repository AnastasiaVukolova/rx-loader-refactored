package org.some.thing.rx.loader.logger;

import org.some.thing.rx.loader.data.LongStatistics;

public class LongStatisticsColoredFormatter {
    private static final Integer FIELD_WIDTH = 15;

    public static String header() {
        return String.format(
                "%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s",
                ColoredLogger.BLUE_BOLD,
                "Event name",
                ColoredLogger.YELLOW_BOLD,
                "min(ms)",
                ColoredLogger.BLUE_BOLD,
                "max(ms)",
                ColoredLogger.YELLOW_BOLD,
                "avg(ms)",
                ColoredLogger.BLUE_BOLD,
                "stdev(ms)",
                ColoredLogger.RESET
        );
    }
    public static String header_notColored() {
        return String.format(
                "%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s",
                "\033",
                "Event name",
                "\033",
                "min(ms)",
                "\033",
                "max(ms)",
                "\033",
                "avg(ms)",
                "\033",
                "stdev(ms)",
                ColoredLogger.RESET
        );
    }

    public static String toString(String eventName, LongStatistics statistics) {
        return String.format(
                "%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "d%s%-" + FIELD_WIDTH + "d%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s",
                ColoredLogger.BLUE_BOLD,
                eventName,
                ColoredLogger.YELLOW_BOLD,
                statistics.getMin() == Long.MAX_VALUE ? 0 : statistics.getMin(),
                ColoredLogger.BLUE_BOLD,
                statistics.getMax() == Long.MIN_VALUE ? 0 : statistics.getMax(),
                ColoredLogger.YELLOW_BOLD,
                String.format("%.2f", statistics.getAverage()),
                ColoredLogger.BLUE_BOLD,
                String.format("%.2f", statistics.getStandardDeviation()),
                ColoredLogger.RESET
        );
    }

    public static String toString_notColored(String eventName, LongStatistics statistics) {
        return String.format(
                "%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "d%s%-" + FIELD_WIDTH + "d%s%-" + FIELD_WIDTH + "s%s%-" + FIELD_WIDTH + "s%s",
                "\033",
                eventName,
                "\033",
                statistics.getMin() == Long.MAX_VALUE ? 0 : statistics.getMin(),
                "\033",
                statistics.getMax() == Long.MIN_VALUE ? 0 : statistics.getMax(),
                "\033",
                String.format("%.2f", statistics.getAverage()),
                "\033",
                String.format("%.2f", statistics.getStandardDeviation()),
                ColoredLogger.RESET
        );
    }
}
