package dev.lukebemish.dynamicassetgenerator.impl;

import org.jspecify.annotations.Nullable;

public class CacheReference<T> {
    @Nullable T held = null;

    public T calcSync(ReferenceCalculator<T> transformer) {
        synchronized (this) {
            return transformer.calc(held);
        }
    }

    public void doSync(ReferenceConsumer<T> consumer) {
        synchronized (this) {
            consumer.accept(held);
        }
    }

    public @Nullable T getHeld() {
        return held;
    }

    public void setHeld(@Nullable T held) {
        synchronized (this) {
            this.held = held;
        }
    }

    @FunctionalInterface
    public interface ReferenceCalculator<T> {
        T calc(@Nullable T held);
    }

    @FunctionalInterface
    public interface ReferenceConsumer<T> {
        void accept(@Nullable T held);
    }
}
