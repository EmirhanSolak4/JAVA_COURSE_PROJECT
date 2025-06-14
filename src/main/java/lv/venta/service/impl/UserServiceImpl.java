package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.User;
import lv.venta.repository.IUserRepository;
import lv.venta.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User getUserById(long id) throws Exception {
        return userRepo.findById(id)
                .orElseThrow(() -> new Exception("User not found with id: " + id));
    }

    @Override
    public void insertUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void updateUser(long id, User updatedUser) throws Exception {
        User existing = getUserById(id);
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setPassword(updatedUser.getPassword());
        existing.setRole(updatedUser.getRole());
        existing.setEnabled(updatedUser.isEnabled());
        userRepo.save(existing);
    }

    @Override
    public void deleteUserById(long id) throws Exception {
        if (!userRepo.existsById(id)) {
            throw new Exception("Cannot delete. User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public void enableUser(long id, boolean enabled) throws Exception {
        User user = getUserById(id);
        user.setEnabled(enabled);
        userRepo.save(user);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<User>();
        for (User u : userRepo.findAll()) {
            if (u.getRole().toString().equalsIgnoreCase(role)) {
                users.add(u);
            }
        }
        return users;
    }
}
