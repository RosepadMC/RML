package net.buj.rml.events;

public abstract class CancellableEvent extends Event {
    private boolean _isCancelled = false;

    @Override
    public boolean isCancelled() {
        return _isCancelled;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    @Override
    public void cancel() {
        _isCancelled = true;
    }
}
