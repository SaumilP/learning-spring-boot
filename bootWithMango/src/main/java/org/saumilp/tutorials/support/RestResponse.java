package org.saumilp.tutorials.support;

/**
 * Created by PATEL1 on 07/06/2015.
 */
public class RestResponse {
    private String message;
    private String errorMessage;

    public RestResponse(String message, String errorMsg) {
        this.message = message;
        this.errorMessage = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
