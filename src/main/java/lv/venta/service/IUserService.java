package lv.venta.service;

import java.util.List;

import lv.venta.model.User;

public interface IUserService {

	List<User> getAllUsers();

	User getUserById(long id) throws Exception;

	void insertUser(User user);

	void updateUser(long id, User updatedUser) throws Exception;

	void deleteUserById(long id) throws Exception;

	void enableUser(long id, boolean enabled) throws Exception;

	List<User> getUsersByRole(String role);

}
