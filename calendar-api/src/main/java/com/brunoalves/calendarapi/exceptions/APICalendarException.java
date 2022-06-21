package com.brunoalves.calendarapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APICalendarException extends RuntimeException {

    public static final String PERSON_NOT_FOUND = "No person present with id ";
    public static final String PERSON_WITH_BLANK_NAME = "Person with blank name";
    public static final String CANDIDATE_NOT_FOUND = "No Candidate present with id ";
    public static final String INTERVIEWER_NOT_FOUND = "No Interviewer present with id ";

    public APICalendarException(final String msg) {
        super(msg);
    }
}
