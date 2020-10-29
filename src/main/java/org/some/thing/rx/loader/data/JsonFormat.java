package org.some.thing.rx.loader.data;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JsonFormat {
    private Event first;
    private Event distance;
    private Event total;


}

