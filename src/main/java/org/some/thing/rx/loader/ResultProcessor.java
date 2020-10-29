package org.some.thing.rx.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.some.thing.rx.loader.data.LongStatistics;
import org.some.thing.rx.loader.data.Timing;
import org.some.thing.rx.loader.logger.ColoredLogger;
import org.some.thing.rx.loader.logger.LongStatisticsColoredFormatter;

import java.util.LongSummaryStatistics;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ResultProcessor {
    private final boolean debugOutput;

    public void processResult(Map<Integer, Timing> result, long seconds) {
        LongStatistics firstAnswerStat = result.values().stream()
                .filter(res -> !res.isFailed() && res.isMeasured())
                .map(Timing::getTimings)
                .map(LongSummaryStatistics::getMin)
                .collect(
                        LongStatistics::new,
                        LongStatistics::accept,
                        LongStatistics::combine
                );

        final Long errors = result.values().stream()
                .filter(Timing::isFailed)
                .count();

        LongStatistics answerDistanceStat = result.values().stream()
                .filter(res -> !res.isFailed() && res.isMeasured())
                .flatMap(res -> res.getDistances().stream())
                .filter(res -> res > 0.0d)//yeah, i know, i would think about that, but for now it just could not be real
                .collect(
                        LongStatistics::new,
                        LongStatistics::accept,
                        LongStatistics::combine
                );

        LongStatistics answerStat = result.values().stream()
                .filter(res -> !res.isFailed() && res.isFinished() && res.isMeasured())
                .map(Timing::getTimings)
                .map(LongSummaryStatistics::getMax)
                .collect(
                        LongStatistics::new,
                        LongStatistics::accept,
                        LongStatistics::combine
                );

        LongStatistics eventStat = result.values().stream()
                .filter(res -> !res.isFailed() && res.isMeasured())
                .map(Timing::eventCount)
                .collect(
                        LongStatistics::new,
                        LongStatistics::accept,
                        LongStatistics::combine
                );
            ColoredLogger.logNotColored(LongStatisticsColoredFormatter.header_notColored());
            ColoredLogger.logNotColored(LongStatisticsColoredFormatter.toString_notColored("First", firstAnswerStat));
            ColoredLogger.logNotColored(LongStatisticsColoredFormatter.toString_notColored("Distance", answerDistanceStat));
            ColoredLogger.logNotColored(LongStatisticsColoredFormatter.toString_notColored("Total", answerStat));
            ColoredLogger.logNotColored(ColoredLogger.GREEN_BOLD, "--------------------------------------------------------------------");
            ColoredLogger.logNotColored(LongStatisticsColoredFormatter.toString_notColored("Event count", eventStat));
            ColoredLogger.logNotColored(ColoredLogger.GREEN_BOLD, "Total requests sent: " + result.size());
            ColoredLogger.logNotColored(ColoredLogger.GREEN_BOLD, "Total requests finished: " + answerStat.getCount());
            ColoredLogger.logNotColored(ColoredLogger.GREEN_BOLD, "Requests per second:\t" + (double) answerStat.getCount() / seconds);
            ColoredLogger.logNotColored(ColoredLogger.RED_UNDERLINED, "Total errors: " + errors);

            result.values().stream().filter(res -> res.isFailed()).forEach(res -> log.debug("Connection exception: ", res.getException()));
    }
}
