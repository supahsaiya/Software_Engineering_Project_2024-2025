package com.example.where2park.controller;

import com.example.where2park.model.Parking;

public class ManageAvailabilityClass {

    public void validationSuccessful() {
        System.out.println("[Validation] Availability update is valid.");
    }

    public void capacityOverflow() {
        System.err.println("[Validation Error] Attempted to exceed parking capacity.");
    }

    public void capacityUnderflow() {
        System.err.println("[Validation Error] Attempted to set availability below 0.");
    }
}
