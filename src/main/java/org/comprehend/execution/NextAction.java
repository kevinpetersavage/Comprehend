package org.comprehend.execution;

import java.util.List;

public interface NextAction<R> {
    R perform(List<List> params);
}
