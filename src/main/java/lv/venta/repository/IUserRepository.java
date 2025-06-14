package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.User;

public interface IUserRepository extends CrudRepository<User, Long> {

}
