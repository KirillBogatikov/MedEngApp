package ru.medeng.tools;

import java.io.IOException;

public interface Producer<O, E extends Throwable> {
    public O get() throws E, IOException;
}
