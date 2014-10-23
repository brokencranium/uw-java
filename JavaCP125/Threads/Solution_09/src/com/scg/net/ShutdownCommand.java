package com.scg.net;

/**
 * This Command will cause the CommandProcessor to shutdown the server.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class ShutdownCommand extends Command<Object> {
    /**
     * Construct an ShutdownCommand.
     */
    public ShutdownCommand() {
        super();
    }

    /**
     * The method called by the receiver. This method must be implemented by
     * subclasses to send a reference to themselves to the receiver by calling
     * receiver.execute(this).
     */
    @Override
    public void execute() {
        this.getReceiver().execute(this);
    }
}
