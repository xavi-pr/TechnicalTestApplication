package com.example.technicaltest.exceptions;

public class FieldDontMatchColumnException extends RuntimeException {
    public FieldDontMatchColumnException(String message) {
        super(message);
    }
}
