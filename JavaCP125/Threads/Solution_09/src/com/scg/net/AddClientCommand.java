package com.scg.net;

import com.scg.domain.ClientAccount;

/**
 * The command to add a Client to a list maintained by the server.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class AddClientCommand extends Command<ClientAccount> {

    /**
     * Construct an AddClientCommand with a target.
     *
     * @param target The target of this Command.
     */
    public AddClientCommand(final ClientAccount target) {
        super(target);
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
