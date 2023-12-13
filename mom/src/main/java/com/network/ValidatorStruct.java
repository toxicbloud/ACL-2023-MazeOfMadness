package com.network;

/**
 * Validator struct.
 */
public class ValidatorStruct {
    /** validator. */
    private DataValidator validator;
    /** listener. */
    private DataListener listener;

    /**
     * Constructor.
     * @param validator Data validator.
     * @param listener Data listener.
     */
    public ValidatorStruct(DataValidator validator, DataListener listener) {
        this.validator = validator;
        this.listener = listener;
    }

    /**
     * Get the data validator.
     * @return Data validator.
     */
    public DataValidator getValidator() {
        return validator;
    }

    /**
     * Get the data listener.
     * @return Data listener.
     */
    public DataListener getListener() {
        return listener;
    }
}
