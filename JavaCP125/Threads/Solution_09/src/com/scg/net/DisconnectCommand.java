package com.scg.net;

/**
 * The command to disconnect, this command has no target.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class DisconnectCommand extends Command<Object> {

    /**
     * Construct an DisconnectCommand.
     */
    public DisconnectCommand() {
        super();
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
