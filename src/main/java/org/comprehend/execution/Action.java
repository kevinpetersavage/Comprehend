package org.comprehend.execution;

import java.util.List;

public interface Action<R> {
    R perform(List<List> params);
}
