package com.example.where2park.controller;

import com.example.where2park.model.Location;
import com.example.where2park.service.DataStorageManager;
import com.example.where2park.service.GPSApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManageLocationClassTest {

    private ManageLocationClass manager;
    private GPSApi mockGpsApi;
    private DataStorageManager mockDb;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockGpsApi = mock(GPSApi.class);
        mockDb = mock(DataStorageManager.class);

        // Use subclassing to inject mocks
        manager = new ManageLocationClass() {
            {
                this.gpsApi = mockGpsApi;
                this.dbManager = mockDb;
            }
        };

        ManageLocationClass.instance = manager;
    }

    @Test
    void testFindLocation_returnsMockLocation() {
        Location mockLocation = new Location(12.34, 56.78, "Test Street");
        when(mockGpsApi.detectLocation()).thenReturn(mockLocation);

        Location result = manager.findLocation();
        assertNotNull(result);
        assertEquals("Test Street", result.getAddress());
    }

    @Test
    void testNewLocationAdded_savesAndProcesses() {
        Location mockLocation = new Location(1.0, 2.0, "Mocked Address");
        GPSApi mockStaticApi = Mockito.mockStatic(GPSApi.class).getMock();
        when(GPSApi.geocode("Mocked Address")).thenReturn(mockLocation);

        when(mockDb.queryFindNearby()).thenReturn(Collections.emptyList());

        manager.dbManager = mockDb;

        ManageLocationClass.instance = manager; // needed by static method
        ManageLocationClass.newLocationAdded("Mocked Address");

        verify(mockDb).initializeOrUpdateUserData(eq(1), anyString(), eq(mockLocation));
        verify(mockDb).queryFindNearby();

        assertEquals(mockLocation, manager.getConfirmedLocation());
    }
}
