package org.echocat.kata.java.part1.exception;

public class InformationNotFoundException extends RuntimeException
{
    public InformationNotFoundException(String message)
    {
        super(message);
    }
}
