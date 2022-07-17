package com.ohara.librarybooktracker.domain.book;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
