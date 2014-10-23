package com.scg.domain;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

/**
 * Responsible for modifying the pay rate and sick leave and vacation hours of
 * staff consultants.
 *
 * @author Russ Moul
 */
public final class HumanResourceManager {
    /** This class' logger. */
    private static final Logger log =
                   Logger.getLogger(HumanResourceManager.class.getName());

    /** Event listener list. */
    private final EventListenerList listenerList = new EventListenerList();

    /**
     * Constructor.
     */
    public HumanResourceManager() {
    }

    /**
     * Sets the pay rate for a staff consultant.
     *
     * @param c the consultant
     * @param newPayRate the new pay rate for the consultant
     */
    public void adjustPayRate(final StaffConsultant c, final int newPayRate) {
        try {
            if (log.isLoggable(Level.INFO)) {
                log.info("% change = (" + newPayRate + " - " + c.getPayRate() + ")/"
                       + c.getPayRate() + " = "
                       + ((newPayRate - c.getPayRate()) / (double)c.getPayRate()));
            }
            c.setPayRate(newPayRate);
            log.info("Approved pay adjustment for " + c.getName());
        } catch (final PropertyVetoException pve) {
            log.info("Denied pay adjustment for " + c.getName());
        }
    }


    /**
     * Sets the sick leave hours for a staff consultant.
     *
     * @param c the consultant
     * @param newSickLeaveHours the new sick leave hours for the consultant
     */
    public void adjustSickLeaveHours(final StaffConsultant c, final int newSickLeaveHours) {
        c.setSickLeaveHours(newSickLeaveHours);
    }

    /**
     * Sets the vacation hours for a staff consultant.
     *
     * @param c the consultant
     * @param newVacationHours the new vacation hours for the consultant
     */
    public void adjustVacationHours(final StaffConsultant c, final int newVacationHours) {
        c.setVacationHours(newVacationHours);
    }


    /**
     * Fires a voluntary termination event for the staff consultant.
     *
     * @param c the consultant resigning
     */
    public void acceptResignation(final Consultant c) {
        fireTerminationEvent(new TerminationEvent(this, c, true));
    }

    /**
     * Fires an involuntary termination event for the staff consultant.
     *
     * @param c the consultant being terminated
     */
    public void terminate(final Consultant c) {
        fireTerminationEvent(new TerminationEvent(this, c, false));
    }

    /**
     * Fires a exchange event.
     *
     * @param evnt the event to be fired
     */
    private void fireTerminationEvent(final TerminationEvent evnt) {
        TerminationListener[] listeners;
        listeners = listenerList.getListeners(TerminationListener.class);

        for (final TerminationListener listener : listeners) {
            if (evnt.isVoluntary()) {
                listener.voluntaryTermination(evnt);
            } else {
                listener.forcedTermination(evnt);
            }
        }
    }

    /**
     * Adds a termination listener.
     *
     * @param l the listener to add
     */
    public synchronized void addTerminationListener(final TerminationListener l) {
        listenerList.add(TerminationListener.class, l);
    }

    /**
     * Removes a termination listener.
     *
     * @param l the listener to remove
     */
    public synchronized void removeTerminationListener(
            final TerminationListener l) {
        listenerList.remove(TerminationListener.class, l);
    }
}
