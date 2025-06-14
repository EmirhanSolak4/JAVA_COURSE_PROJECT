package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.User;

public interface ICategoryRepository extends CrudRepository<User, Long> {

}
