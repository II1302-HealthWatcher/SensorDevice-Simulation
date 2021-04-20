package se.kth.ii1302.healthwatcher.startup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static se.kth.ii1302.healthwatcher.startup.Main.dummy;

class MainTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void dummyTest() {
        int result = dummy();
        assertEquals(1, result, "Dummy test failed!");
    }
}