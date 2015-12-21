package com.goeuro.devtest;

import org.apache.http.HttpException;

/**
 * Http exception specific for Go Euro client.
 */
public class GoEuroHttpException extends HttpException {
    public GoEuroHttpException(String message) {
        super(message);
    }
}
