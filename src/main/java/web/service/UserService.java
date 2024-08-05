package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> allUser();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getById(Long id);
}
