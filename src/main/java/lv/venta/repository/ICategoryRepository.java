package lv.venta.repository;


import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Category;

public interface ICategoryRepository extends CrudRepository<Category, Long> {

}
