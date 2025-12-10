package com.yorkseed.backend.exception;


public class ApplicationException {
    public static class ResourceNotFound extends RuntimeException {
        public ResourceNotFound(String message) {
            super(message);
        }
    }

    public static class BadRequest extends RuntimeException {
        public BadRequest(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExists extends RuntimeException {
        public EmailAlreadyExists(String message) {
            super(message);
        }
    }
}
