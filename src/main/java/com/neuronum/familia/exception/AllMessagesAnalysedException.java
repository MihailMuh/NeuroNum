package com.neuronum.familia.exception;

public class AllMessagesAnalysedException extends RuntimeException {
    public AllMessagesAnalysedException() {
        super("All messages already analysed!");
    }
}
