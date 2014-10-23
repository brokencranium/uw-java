package com.scg.net;

import java.io.Serializable;

import com.scg.net.server.CommandProcessor;

/**
 * The superclass of all Command objects, implements the command role in the
 * Command design pattern.
 *
 * @param <T> the target type
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public abstract class Command<T> implements Serializable {
    /** The CommandProcessor that will execute this command. */
    private transient CommandProcessor receiver;

    /** The Consultant to be added. */
    private T target;

    /**
     * Construct an AbstractCommand without a target; called from subclasses.
     */
    public Command() {
    }

    /**
     * Construct an AbstractCommand with a target; called from subclasses.
     *
     * @param target the target
     */
    public Command(final T target) {
        this.target = target;
    }

    /**
     * Gets the CommandProcessor receiver for this Command.
     *
     * @return the receiver for this Command.
     */
    public final CommandProcessor getReceiver() {
        return receiver;
    }

    /**
     * Set the CommandProcessor that will execute this Command.
     *
     * @param receiver the receiver for this Command.
     */
    public final void setReceiver(final CommandProcessor receiver) {
        this.receiver = receiver;
    }

    /**
     * Return the target of this Command.
     *
     * @return the target.
     */
    public final T getTarget() {
        return target;
    }

    /**
     * A string representation of this command.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", target " + target;
    }
    
    /**
     * The method called by the receiver. This method must be implemented by
     * subclasses to send a reference to themselves to the receiver by calling
     * receiver.execute(this).
     */
    public abstract void execute();
}
