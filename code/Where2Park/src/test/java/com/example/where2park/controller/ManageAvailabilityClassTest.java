package com.example.where2park.controller;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ManageAvailabilityClassTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void validationSuccessful_shouldPrintCorrectMessage() {
        ManageAvailabilityClass obj = new ManageAvailabilityClass();
        obj.validationSuccessful();
        assertEquals("[Validation] Availability update is valid.", outContent.toString().trim());
    }

    @Test
    void capacityOverflow_shouldPrintCorrectError() {
        ManageAvailabilityClass obj = new ManageAvailabilityClass();
        obj.capacityOverflow();
        assertEquals("[Validation Error] Attempted to exceed parking capacity.", errContent.toString().trim());
    }

    @Test
    void capacityUnderflow_shouldPrintCorrectError() {
        ManageAvailabilityClass obj = new ManageAvailabilityClass();
        obj.capacityUnderflow();
        assertEquals("[Validation Error] Attempted to set availability below 0.", errContent.toString().trim());
    }
}
