package com.scg.net;

import com.scg.domain.Consultant;

/**
 * The command to add a Consultant to a list maintained by the server.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class AddConsultantCommand extends Command<Consultant> {

    /**
     * Construct an AddConsultantCommand with a target.
     *
     * @param target The target of this Command.
     */
    public AddConsultantCommand(final Consultant target) {
        super(target);
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        this.getReceiver().execute(this);
    }
}
