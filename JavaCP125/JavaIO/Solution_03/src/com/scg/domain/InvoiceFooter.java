package com.scg.domain;

import java.util.Locale;

/**
 * Footer for Small Consulting Group Invoices.
 *
 * @author Russ Moul
 */
final class InvoiceFooter {
    /** Page break. */
    private static final String PAGE_BREAK =
        "===============================================================================";

    /**  The page number. */
    private int pageNumber;

    /** Business name. */
    private String businessName;


    /**
     * Construct an InvoiceFooter.
     *
     * @param businessName name of buisness to include in footer
     */
    public InvoiceFooter(final String businessName) {
        this.businessName = businessName;
    }

    /**
     * Increment the current page number by one.
     */
    public void incrementPageNumber() {
        pageNumber++;
    }

    /**
     * Print the formatted footer.
     *
     * @return Formatted footer string.
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "%n%n%n%-69s Page: %3d%n%s%n",
                             businessName, pageNumber, PAGE_BREAK);
    }

}
