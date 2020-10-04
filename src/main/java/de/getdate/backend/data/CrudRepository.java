package de.getdate.backend.data;

import java.util.List;

public interface CrudRepository<T extends BaseEntity<ID>, ID> {
  void save(T entity);

  T findById(ID id);

  T findBy(Specification<T>... specifications);

  List<T> findAll(int page, int pageSize);

  List<T> findAll(int page, int pageSize, Specification<T>... specifications);

  T update(T entity);

  void delete(T entity);

  void deleteById(ID id);

  long count(Specification<T>[] specifications);

  long count();

}
