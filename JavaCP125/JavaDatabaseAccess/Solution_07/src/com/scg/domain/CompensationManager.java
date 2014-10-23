package com.scg.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Approves or rejects compensation changes.
 *
 * @author Russ Moul
 */
public final class CompensationManager implements PropertyChangeListener, VetoableChangeListener {
    /** Maximum allowed raise, 5%. */
    private static final int MAX_NEW_RATE_PERCENT = 105;

    /** Percent multiplier. */
    private static final int TO_PERCENT = 100;

    /** This class' logger. */
    private static final Logger log = Logger.getLogger(CompensationManager.class.getName());

    /**
     * Constructor.
     */
    public CompensationManager() {
    }

    /**
     * Rejects any raise over 5%.
     *
     * @param evt a vetoable change event for the payRate property
     *
     * @throws PropertyVetoException if the change is vetoed
     */
    @Override
    public void vetoableChange(final PropertyChangeEvent evt)
        throws PropertyVetoException {
        if (StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
            final int oldValue = (Integer)evt.getOldValue();
            final int newValue = (Integer)evt.getNewValue();

            if (newValue * TO_PERCENT > oldValue * MAX_NEW_RATE_PERCENT) {
                if (log.isLoggable(Level.INFO)) {
                    final String msg =
                        String.format("REJECTED pay rate change, from %s to %s for %s",
                                      evt.getOldValue(), evt.getNewValue(),
                                      ((StaffConsultant)evt.getSource()).getName());
                    log.info(msg);
                }
                throw new PropertyVetoException("Raise denied!", evt);
            }
            if (log.isLoggable(Level.INFO)) {
                final String msg =
                    String.format("APPROVED pay rate change, from %s to %s for %s",
                                  evt.getOldValue(), evt.getNewValue(),
                                  ((StaffConsultant)evt.getSource()).getName());
                log.info(msg);
            }
        }
    }

    /**
     * Processes to final pay rate change.
     * 
     * @param evt a change event for the payRate property
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(evt.getPropertyName())) {
            log.info("Pay rate changed, from " + evt.getOldValue()
                + " to " + evt.getNewValue()
                + " for " + ((StaffConsultant)evt.getSource()).getName());
        }
    }
}
