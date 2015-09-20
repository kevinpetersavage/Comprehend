package org.comprehend.capture;

import java.util.List;

public interface NextAction<R> {
    R perform(List<List> params);
}
