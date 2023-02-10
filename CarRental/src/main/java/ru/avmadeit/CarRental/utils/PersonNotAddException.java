package ru.avmadeit.CarRental.utils;

public class PersonNotAddException extends RuntimeException {
    public PersonNotAddException (String msg){
        super(msg);
    }
}
