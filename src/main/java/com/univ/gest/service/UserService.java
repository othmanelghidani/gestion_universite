package com.univ.gest.service;

import com.univ.gest.domain.entity.User;
import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
}
