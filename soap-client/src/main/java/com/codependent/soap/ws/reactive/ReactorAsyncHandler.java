package com.codependent.soap.ws.reactive;

import reactor.core.publisher.MonoSink;

import javax.xml.ws.AsyncHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ReactorAsyncHandler {
    public static <T> AsyncHandler<T> into(MonoSink<T> sink) {
        return res -> {
            try {
                sink.success(res.get(1, MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                sink.error(e);
            }
        };
    }
}
