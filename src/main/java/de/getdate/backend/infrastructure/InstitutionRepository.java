package de.getdate.backend.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import de.getdate.backend.data.StringId;
import de.getdate.backend.data.jpa.AbstractJpaCrudRepository;
import de.getdate.backend.data.jpa.ComparisonSpecification.ComparisonType;
import de.getdate.backend.model.Institution;

@ApplicationScoped
public class InstitutionRepository extends AbstractJpaCrudRepository<Institution, StringId> {
  @Inject
  protected EntityManager entityManager;

  protected InstitutionRepository() {
    super(Institution.class);
  }

  public Institution findByName(String name) {
    return findByComparisonSpecification(ComparisonType.EQUALS, "name", name);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
