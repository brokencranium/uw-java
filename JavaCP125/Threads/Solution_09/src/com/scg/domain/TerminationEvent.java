package com.scg.domain;

import java.util.EventObject;

/**
 * Event used to notify listeners of a Consultant's termination.
 *
 * @author Russ Moul
 */
public final class TerminationEvent extends EventObject {
    /** The serialVersionUID. */
    private static final long serialVersionUID = -3948350360510657564L;

    /** Was it a voluntary termination. */
    private final boolean voluntary;

    /** Consultant terminated. */
    private final Consultant consultant;

    /**
     * Constructor.
     *
     * @param source the event source
     * @param consultant the consultant being terminated
     * @param voluntary was the termination voluntary
     */
    public TerminationEvent(final Object source, final Consultant consultant,
                                                 final boolean voluntary) {
        super(source);
        this.voluntary = voluntary;
        this.consultant = consultant;
    }

    /**
     * Gets the voluntary termination status.
     *
     * @return true if a voluntary termination
     */
    public boolean isVoluntary() {
        return voluntary;
    }

    /**
     * Gets the consultant that was terminated.
     *
     * @return the consultant that was terminated
     */
    public Consultant getConsultant() {
        return consultant;
    }
}
