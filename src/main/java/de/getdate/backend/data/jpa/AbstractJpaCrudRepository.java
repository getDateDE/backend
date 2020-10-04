package de.getdate.backend.data.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.CrudRepository;
import de.getdate.backend.data.ReflectionUtil;
import de.getdate.backend.data.Specification;
import de.getdate.backend.data.jpa.ComparisonSpecification.ComparisonType;

public abstract class AbstractJpaCrudRepository<T extends BaseEntity<ID>, ID>
    implements CrudRepository<T, ID> {
  private static final Specification[] EMPTY_SPECIFICATIONS = new Specification[0];

  protected Class<T> entityClass;
  protected CriteriaBuilder criteriaBuilder;

  protected AbstractJpaCrudRepository(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  public void save(T entity) {
    getEntityManager().persist(entity);
  }

  @Override
  public T findById(ID id) {
    return (T) getEntityManager().find(entityClass, id);
  }

  @Override
  public T findBy(Specification<T>... specifications) {
    var users = findAll(1, 1, specifications);
    return users.size() == 0 ? null : users.get(0);
  }

  public T findByComparisonSpecification(ComparisonType comparisonType, String columnName,
      Object expected) {
    return findBy(new ComparisonSpecification<T>(comparisonType, columnName, expected));
  }

  // @formatter:off
  @Override
  public List<T> findAll(int page, int pageSize) {
    return findAll(page, pageSize, EMPTY_SPECIFICATIONS);
  }

  @Override
  public List<T> findAll(int page, int pageSize, Specification<T>... specifications) {
    var query = buildSelectQuery(specifications);

    int selectOffset = calculatePageOffset(page, pageSize);

    var results = query
      .setFirstResult(selectOffset)
      .setMaxResults(pageSize)
      .getResultList();

    return results;
  }

  private TypedQuery<T> buildSelectQuery(Specification<T>... specifications) {
    var selectQuery = createEntityQuery();

    var root = selectQuery.from(entityClass);
    selectQuery.select(root);
    if (specifications != null && specifications.length > 0) {
      selectQuery.where(createChainedPredicate(root, specifications));
     }

    return getEntityManager().createQuery(selectQuery);
  }

  @Override
  public T update(T entity) {
    return getEntityManager().merge(entity);
  }

  @Override
  public void delete(T entity) {
    getEntityManager().remove(entity);
  }

  @Override
  public void deleteById(ID id) {
    var criteriaDelete = criteriaBuilder().createCriteriaDelete(entityClass);
    var root = criteriaDelete.from(entityClass);

    getEntityManager().createQuery(
      criteriaDelete.where(
        criteriaBuilder().equal(root.get("id"), id)
      )
    ).executeUpdate();
  }

  private int calculatePageOffset(int page, int pageSize) {
    return (page - 1) * pageSize;
  }

  @Override
  public long count(Specification<T>[] specifications) {
    CriteriaQuery<Long> countQuery = criteriaBuilder().createQuery(Long.class);
    Root<T> countRoot = countQuery.from(entityClass);

    countQuery.select(criteriaBuilder().count(countRoot));

    if (specifications != null && specifications.length > 0) {
      countQuery.where(createChainedPredicate(countRoot, specifications));
    }

    return getEntityManager().createQuery(
      countQuery
    ).getSingleResult().longValue();
  }

  @Override
  public long count() {
    return count(EMPTY_SPECIFICATIONS);
  }
  // @formatter:on

  private Predicate createChainedPredicate(Root<T> root, Specification<T>... specifications) {
    Predicate[] predicates = new Predicate[specifications.length];
    for (int i = 0; i < predicates.length; i++) {
      predicates[i] = specifications[i].toPredicate(root, criteriaBuilder());
    }
    return criteriaBuilder().and(predicates);
  }

  protected CriteriaQuery<T> createEntityQuery() {
    return criteriaBuilder().createQuery(entityClass);
  }

  protected CriteriaBuilder criteriaBuilder() {
    if (criteriaBuilder == null) {
      criteriaBuilder = getEntityManager().getCriteriaBuilder();
    }

    return criteriaBuilder;
  }

  protected abstract EntityManager getEntityManager();

}
