package com.github.camilo.movierental.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.model.User;

class UserMapperTest {

    private static final String LAST_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
    private static final String EMAIL = "email";
    UserMapper mapper = UserMapper.INSTANCE;
    
    @Test
    void testUserToDto() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        UserDto map = mapper.map(user);
        assertNotNull(map);
        assertNotNull(map.getEmail());
        assertNotNull(map.getFirstName());
        assertNotNull(map.getLastName());
        assertEquals(EMAIL, map.getEmail());
        assertEquals(FIRST_NAME, map.getFirstName());
        assertEquals(LAST_NAME, map.getLastName());
    }
    
    @Test
    void testDtoToUser() {
        UserDto user = new UserDto();
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        User map = mapper.map(user);
        assertNotNull(map);
        assertNotNull(map.getEmail());
        assertNotNull(map.getFirstName());
        assertNotNull(map.getLastName());
        assertEquals(EMAIL, map.getEmail());
        assertEquals(FIRST_NAME, map.getFirstName());
        assertEquals(LAST_NAME, map.getLastName());
    }

}
