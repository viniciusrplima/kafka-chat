package com.pacheco.app;


import com.pacheco.app.util.HashGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTests {

    @Test
    public void testHashGenerator() {
        // given
        int hashSize = 5;

        // when
        String hash = HashGenerator.generateHash(hashSize);

        // then
        assertEquals(hash.length(), hashSize);
    }

}
