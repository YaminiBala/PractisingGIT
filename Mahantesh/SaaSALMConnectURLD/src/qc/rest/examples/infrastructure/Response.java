/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.infrastructure;

import java.util.Map;

public class Response {
    private Map<String, ? extends Iterable<String>> responseHeaders = null;
    private byte[] responseData = null;
    private Exception failure = null;
    private int statusCode = 0;

    public Response(Map<String, Iterable<String>> responseHeaders, byte[] responseData, Exception failure, int statusCode) {
        this.responseHeaders = responseHeaders;
        this.responseData = responseData;
        this.failure = failure;
        this.statusCode = statusCode;
    }

    public Response() {
    }

    public Map<String, ? extends Iterable<String>> getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(Map<String, ? extends Iterable<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public byte[] getResponseData() {
        return this.responseData;
    }

    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    public Exception getFailure() {
        return this.failure;
    }

    public void setFailure(Exception failure) {
        this.failure = failure;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String toString() {
        return new String(this.responseData);
    }
}

