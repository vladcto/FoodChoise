package com.example.foodchoise.callback;

public abstract class BasicCallback<O> implements Runnable, AdderRecipient {
    private Recipient<O> recipient;

    @Override
    final public void run() {
        recipient.getResult(getWork());
    }

    public abstract O getWork();

    public final Runnable addRecipient(Recipient recipient) {
        this.recipient = recipient;
        return (Runnable) this;
    }
}
