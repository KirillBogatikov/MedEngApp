package ru.medeng.models.order;

public enum Status {
    Created(0),
    Processing(1),
    Booked(2),
    Ready(3),
    Received(4),
    Canceled(5);

    private final int i;

    Status(int i) {
        this.i = i;
    }

    public int getCode() {
        return i;
    }
}
