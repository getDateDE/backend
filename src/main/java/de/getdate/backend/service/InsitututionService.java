package de.getdate.backend.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import de.getdate.backend.data.Specification;
import de.getdate.backend.infrastructure.InstitutionRepository;
import de.getdate.backend.model.Institution;
import de.getdate.backend.model.User;
import de.getdate.backend.search.SearchRequest;

@ApplicationScoped
public class InsitututionService {
  @Inject
  private InstitutionRepository institutionRepository;

  @Transactional
  public void clearAppointment(User user, int slotId) {

  }

}
