package org.comprehend.tasks;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.concurrent.Callable;

public class SubmitToServiceFunction<T> implements Function<Callable<T>, ListenableFuture<T>> {
    private final ListeningExecutorService service;

    public SubmitToServiceFunction(ListeningExecutorService service) {
        this.service = service;
    }

    @Override
    public ListenableFuture<T> apply(Callable<T> callable) {
        return service.submit(callable);
    }
}
