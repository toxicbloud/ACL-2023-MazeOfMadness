package com.network;

/**
 * Validator struct.
 */
public class ValidatorStruct {
    /** validator. */
    private DataValidator validator;
    /** listener. */
    private DataListener listener;
    /** temporary. */
    private boolean temporary;

    /**
     * Constructor.
     * @param validator Data validator.
     * @param listener Data listener.
     * @param temporary Should the validator be removed after the first event.
     */
    public ValidatorStruct(DataValidator validator, DataListener listener, boolean temporary) {
        this.validator = validator;
        this.listener = listener;
        this.temporary = temporary;
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

    /**
     * Is the validator temporary.
     * @return true if the validator is temporary, false otherwise.
     */
    public boolean isTemporary() {
        return temporary;
    }
}
