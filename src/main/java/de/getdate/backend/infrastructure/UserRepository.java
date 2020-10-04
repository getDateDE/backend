package de.getdate.backend.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import de.getdate.backend.data.StringId;
import de.getdate.backend.data.jpa.AbstractJpaCrudRepository;
import de.getdate.backend.data.jpa.ComparisonSpecification.ComparisonType;
import de.getdate.backend.model.User;

@ApplicationScoped
public class UserRepository extends AbstractJpaCrudRepository<User, StringId> {
  @Inject
  EntityManager entityManager;

  protected UserRepository() {
    super(User.class);
  }

  public User findByEmail(String email) {
    return findByComparisonSpecification(ComparisonType.EQUALS, "email", email);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
