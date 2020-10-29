package org.some.thing.rx.loader.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Event {
    private String min;
    private String max;
    private String avg;
    private String stdev;
}
