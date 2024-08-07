package web.service;


import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserHb  userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> allUser() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public void delete(User user) {
        boolean existContact = userDao.existsById(user.getId());
        if (existContact) {
            User contact = userDao.getOne(user.getId());
            userDao.delete(contact);
        }
    }

    @Transactional
    @Override
    public void edit(User user) {
        userDao.save(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.findById(id).get();
    }
}
