package de.getdate.backend.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<T> {
  public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

}
