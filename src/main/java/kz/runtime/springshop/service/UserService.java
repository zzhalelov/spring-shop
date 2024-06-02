package kz.runtime.springshop.service;

import kz.runtime.springshop.model.User;

public interface UserService {
    void create(User user);

    User getUser();
}