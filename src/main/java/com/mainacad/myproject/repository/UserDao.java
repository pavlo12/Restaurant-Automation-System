package com.mainacad.myproject.repository;

import com.mainacad.myproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao  {
    User getUser(long id);

    public User getUserByLoginName(String login);

}
