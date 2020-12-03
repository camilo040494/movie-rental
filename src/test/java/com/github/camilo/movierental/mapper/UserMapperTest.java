package com.github.camilo.movierental.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.model.User;

public class UserMapperTest {

    private static final String PASSWORD = "secreto";
    private static final String LAST_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
    public static final String EMAIL = "email@email.com";
    
    UserMapper mapper = UserMapper.INSTANCE;
    
    @Test
    void testUserToDto() {
        User user = buildUser();
        UserDto map = mapper.map(user);
        assertNotNull(map);
        assertNotNull(map.getEmail());
        assertNotNull(map.getFirstName());
        assertNotNull(map.getLastName());
        assertEquals(EMAIL, map.getEmail());
        assertEquals(FIRST_NAME, map.getFirstName());
        assertEquals(LAST_NAME, map.getLastName());
    }

    public static User buildUser() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        return user;
    }
    
    @Test
    void testDtoToUser() {
        UserDto user = buildUserDto();
        User map = mapper.map(user);
        assertNotNull(map);
        assertNotNull(map.getEmail());
        assertNotNull(map.getFirstName());
        assertNotNull(map.getLastName());
        assertEquals(EMAIL, map.getEmail());
        assertEquals(FIRST_NAME, map.getFirstName());
        assertEquals(LAST_NAME, map.getLastName());
    }

    public static UserDto buildUserDto() {
        UserDto user = new UserDto();
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setPassword(PASSWORD);
        return user;
    }

}
