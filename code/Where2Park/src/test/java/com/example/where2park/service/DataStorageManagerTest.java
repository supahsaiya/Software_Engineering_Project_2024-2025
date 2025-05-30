
package com.example.where2park.service;

import com.example.where2park.model.Location;
import com.example.where2park.model.Booking;
import com.example.where2park.model.Client;
import com.example.where2park.model.ParkingLot;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStorageManagerTest {

    private DataStorageManager manager;

    @BeforeEach
    public void setup() {
        manager = new DataStorageManager();
    }

    @Test
    public void testQueryStatisticalCategories() {
        List<String> categories = DataStorageManager.queryStatisticalCategories();
        assertTrue(categories.contains("Κρατήσεις"));
        assertTrue(categories.contains("Έσοδα"));
    }

    @Test
    public void testQueryStatistics() {
        String bookingsDay = DataStorageManager.queryStatistics("Κρατήσεις", "Ημέρα");
        assertTrue(bookingsDay.contains("Δευτέρα"));

        String revenueMonth = DataStorageManager.queryStatistics("Έσοδα", "Μήνας");
        assertTrue(revenueMonth.contains("Ιανουάριος"));

        String invalid = DataStorageManager.queryStatistics("ΆλληΚατηγορία", "ΆλλοΦίλτρο");
        assertTrue(invalid.contains("Δεν υπάρχουν διαθέσιμα"));
    }

    @Test
    public void testCalculateDistance() throws Exception {
        // Reflection to test private method (optional)
        var method = DataStorageManager.class.getDeclaredMethod("calculateDistance", double.class, double.class, double.class, double.class);
        method.setAccessible(true);

        double dist = (double) method.invoke(manager, 37.9838, 23.7275, 38.0, 23.7);
        assertTrue(dist > 0);
    }

    @Test
    public void testInitializeOrUpdateUserData() {
        Location loc = new Location(37.9838, 23.7275, "Athens");
        manager.initializeOrUpdateUserData(9999, "TestUser", loc);

        Client c = DataStorageManager.getClientById(9999);
        assertNotNull(c);
        assertEquals("TestUser", c.getName());
    }

    @Test
    public void testGetClientByIdNonExisting() {
        Client c = DataStorageManager.getClientById(-1);
        assertNull(c);
    }

    @Test
    public void testLoadParkings() {
        List<ParkingLot> parkings = DataStorageManager.loadParkings();
        assertNotNull(parkings);
        // Assuming your XML has at least one parking
        if (!parkings.isEmpty()) {
            assertNotNull(parkings.get(0).getName());
        }
    }

    @Test
    public void testQueryAvailableSpots() {
        int spots = DataStorageManager.queryAvailableSpots("NonExistingParking");
        assertEquals(0, spots);
    }

    @Test
    public void testQueryClientBookingEmpty() {
        List<Booking> bookings = DataStorageManager.queryClientBooking(-1);
        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testHasReview() {
        // Αν δεν έχει το αρχείο reviews.xml ή δεδομένα, το σωστό είναι false
        boolean has = DataStorageManager.hasReview(-1, "FakeParking", "2025-01-01");
        assertFalse(has);
    }

    @Test
    public void testGetParkingByName() {
        ParkingLot p = DataStorageManager.getParkingByName("NonExistingParking");
        assertNull(p);
    }

    @Test
    public void testQueryFindNearby() {
        Location loc = new Location(37.9838, 23.7275, "Athens");
        List<DataStorageManager.ParkingSpot> nearby = manager.queryFindNearby();
        assertNotNull(nearby);
        // Αν έχει δεδομένα στο XML, θα επιστρέφει κάποια spots
    }
}


/*package com.example.where2park.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataStorageManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void querySaveLocation() {
    }

    @Test
    void queryStatisticalCategories() {
    }

    @Test
    void queryStatistics() {
    }

    @Test
    void getCurrentLocation() {
    }

    @Test
    void queryFindNearby() {
    }

    @Test
    void initializeOrUpdateUserData() {
    }

    @Test
    void queryClientBooking() {
    }

    @Test
    void queryReviewDetails() {
    }

    @Test
    void querySearch() {
    }

    @Test
    void queryUpdate() {
    }

    @Test
    void hasReview() {
    }

    @Test
    void getParkingByName() {
    }

    @Test
    void getClientById() {
    }

    @Test
    void loadParkings() {
    }

    @Test
    void queryAvailableSpots() {
    }

    @Test
    void getAllParkingLots() {
    }
}

 */