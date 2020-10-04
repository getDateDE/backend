package de.getdate.backend.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import de.getdate.backend.data.StringId;
import de.getdate.backend.data.jpa.AbstractJpaCrudRepository;
import de.getdate.backend.model.Slot;

@ApplicationScoped
public class SlotRepository extends AbstractJpaCrudRepository<Slot, StringId> {
  @Inject
  protected EntityManager entityManager;

  protected SlotRepository() {
    super(Slot.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
