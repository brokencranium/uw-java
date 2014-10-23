package com.scg.net;

import java.util.Date;

/**
 * The command to create invoices for a specified month.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class CreateInvoicesCommand extends Command<Date> {
    /**
     * Construct a CreateInvoicesCommand with a target month, which should be
     * obtained by getting the desired month constant from Calendar.
     *
     * @param target
     *            the target month.
     */
    public CreateInvoicesCommand(final Date target) {
        super(target);
    }

    /**
     * Execute this command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        this.getReceiver().execute(this);
    }
}
