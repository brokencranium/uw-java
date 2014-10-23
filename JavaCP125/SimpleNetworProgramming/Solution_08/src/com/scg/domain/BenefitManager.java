package com.scg.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

/**
 * Logs changes in benefits.
 *
 * @author Russ Moul
 */
public final class BenefitManager implements PropertyChangeListener {
    /** This class' logger. */
    private static final Logger log = Logger.getLogger(BenefitManager.class.getName());

    /**
     * Constructor.
     */
    public BenefitManager() {
    }

    /**
     * Logs the change.
     *
     * @param evt a property change event for the sickLeaveHours or
     *            vacationHours property
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final String propName = evt.getPropertyName();
        final int oldValue = (Integer) evt.getOldValue();
        final int newValue = (Integer) evt.getNewValue();

        log.info(propName + " changed from " + oldValue + " to " + newValue
               + " for " + ((StaffConsultant)evt.getSource()).getName());
    }
}
