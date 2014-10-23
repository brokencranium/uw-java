package com.scg.domain;

/**
 * Encapsulates the concept of a billable skill.
 */
public enum Skill {
    /** Project manager skill. */
    PROJECT_MANAGER("Project Manager", 250),
    /** Architect skill. */
    SYSTEM_ARCHITECT("System Architect", 200),
    /** Engineer skill. */
    SOFTWARE_ENGINEER("Software Engineer", 150),
    /** Tester skill. */
    SOFTWARE_TESTER("Software Tester", 100),
    /** Unknown skill. */
    UNKNOWN_SKILL("Unknown Skill", 0);

    /** Holds value of property name. */
    private String name;

    /** Holds value of property rate. */
    private int rate;

    /**
     * Creates a new instance of Skill
     *
     * @param name Skill name.
     * @param rate The billing rate.
     */
    private Skill(final String name, final int rate) {
        this.name = name;
        this.rate = rate;
    }

    /**
     * Getter for property name.
     *
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for property rate.
     *
     * @return Value of property rate.
     */
    public int getRate() {
        return this.rate;
    }
}
