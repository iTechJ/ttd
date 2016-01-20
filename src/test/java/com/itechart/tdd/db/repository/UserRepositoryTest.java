package com.itechart.tdd.db.repository;

import com.itechart.tdd.db.AbstractDbTest;
import com.itechart.tdd.db.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

public class UserRepositoryTest extends AbstractDbTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userIsAdded() {
        User user = new User();
        user.setName("Anton");
        user.setLastName("Nekrasov");
        Long id = userRepository.save(user).getId();
        assertNotNull(id);
    }

    @Test
    public void removeUser() {
        Long id = 3L;
        userRepository.delete(id);
    }

    @Test
    public void foundAll() {
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void foundUserByName() {
        String name = "Martin";
        List<User> users = userRepository.findByName(name);
        assertFalse(users.isEmpty());
    }

}