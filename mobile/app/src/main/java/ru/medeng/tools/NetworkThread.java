package ru.medeng.tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

public class NetworkThread extends Thread {
    private static final String TAG = NetworkThread.class.getSimpleName();

    private Object mutex = new Object();
    private volatile Handler handler;

    public NetworkThread() {
        start();
        synchronized (mutex) {
            try {
                mutex.wait();
            } catch (InterruptedException e) {
                Log.d(TAG, "Failed to wait handler", e);
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void run() {
        Looper.prepare();
        handler = new Handler();
        synchronized (mutex) {
            mutex.notifyAll();
        }
        Looper.loop();
    }

    public <O, E extends Throwable> O await(Producer<O, E> p) throws E {
        final Object mutex = new Object();
        AtomicReference<Throwable> throwable = new AtomicReference<>();
        AtomicReference<O> out = new AtomicReference<>();

        handler.post(() -> {
            try {
                out.set(p.get());
            } catch(Throwable t) {
                throwable.set(t);
            }

            synchronized (mutex) {
                mutex.notifyAll();
            }
        });

        synchronized (mutex) {
            try {
                mutex.wait();
            } catch (InterruptedException e) {
                Log.d(TAG, "Failed to await function", e);
            }
        }

        if (throwable.get() != null) {
            throw (E)throwable.get();
        }

        return out.get();
    }
}
