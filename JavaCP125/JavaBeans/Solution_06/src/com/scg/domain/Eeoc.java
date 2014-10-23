package com.scg.domain;

import java.util.logging.Logger;

import com.scg.util.Name;
/**
 * The EEOC monitors SCG's terminations.
 *
 * @author Russ Moul
 */
public final class Eeoc implements TerminationListener {
    /** This class' logger. */
    private static final Logger log = Logger.getLogger(Eeoc.class.getName());

    /** Keeps the tally of voluntary terminations. */
    private int voluntaryTerminationCount;

    /** Keeps the tally of forced terminations. */
    private int forcedTerminationCount;

    /**
     * Simply prints a message indicating that the consultant quit.
     *
     * @param evt the termination event
     */
    @Override
    public void voluntaryTermination(final TerminationEvent evt) {
        final Name name = evt.getConsultant().getName();
        log.info(name + " quit.");
        voluntaryTerminationCount++;
    }

    /**
     * Simply prints a message indicating that the consultant was fired.
     *
     * @param evt the termination event
     */
    @Override
    public void forcedTermination(final TerminationEvent evt) {
        final Name name = evt.getConsultant().getName();
        log.info(name + " was fired.");
        forcedTerminationCount++;
    }

    /**
     * Gets the forced termination count.
     *
     * @return the forced termination count
     */
    public int forcedTerminationCount() {
        return forcedTerminationCount;
    }

    /**
     * Gets the voluntary termination count.
     *
     * @return the voluntary termination count
     */
    public int voluntaryTerminationCount() {
        return voluntaryTerminationCount;
    }
}
