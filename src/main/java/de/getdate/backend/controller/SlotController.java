package de.getdate.backend.controller;

import static de.getdate.backend.dto.AppointmentCreationResult.Type.ALREADY_OCCUPIED;
import static de.getdate.backend.dto.AppointmentCreationResult.Type.SUCESSFULLY_CREATED;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import de.getdate.backend.data.StringId;
import de.getdate.backend.dto.AppointmentCreationResult.Type;
import de.getdate.backend.dto.SlotCreationRequest;
import de.getdate.backend.infrastructure.SlotRepository;
import de.getdate.backend.model.Institution;
import de.getdate.backend.model.Slot;
import de.getdate.backend.model.User;
import de.getdate.backend.service.InsitututionService;
import de.getdate.backend.service.UserService;

@Path("/api/slot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SlotController {
  @Inject
  protected InsitututionService insitututeService;

  @Inject
  protected SlotRepository slotRepository;

  @Inject
  protected UserService userService;

  @Transactional
  @Path("/{slotId}/make")
  @POST
  public Response makeAppointment(@Context SecurityContext securityContext,
      @PathParam("slotId") String slotId) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();

    Slot slot = slotRepository.findById(StringId.valueOf(slotId));
    if (slot == null) {
      return Response.status(Status.NOT_FOUND).entity(Type.NOT_AVAILABLE).build();
    }

    if (slot.isOccupied()) {
      return Response.status(Status.BAD_REQUEST).entity(ALREADY_OCCUPIED).build();
    }

    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    slot.setUser(user);
    slotRepository.update(slot);

    return Response.status(Status.CREATED).entity(SUCESSFULLY_CREATED).build();
  }

  @Transactional
  @DELETE
  @Path("/{slotId}/clear")
  public Response clearAppointment(@Context SecurityContext securityContext,
      @PathParam("slotId") String slotId) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    Slot slot = slotRepository.findById(StringId.valueOf(slotId));
    User user = userService.getUser(securityContext.getUserPrincipal().getName());

    if (slot.getUser().getId() != user.getId())
      return Response.status(401).build();

    slot.setUser(null);
    slotRepository.update(slot);

    return Response.ok().build();
  }

  @POST
  @Path("/")
  @Transactional
  public Response createSlot(@Context SecurityContext securityContext, SlotCreationRequest slotCreationRequest) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    if (user.getInstitution() == null)
      return Response.status(401).build();
    Institution institution = user.getInstitution();

    Slot slot = new Slot();
    slot.setDateTimestamp(slotCreationRequest.getDateTimestamp());
    slot.setDurationInMinutes(slotCreationRequest.getDurationInMinutes());
    slot.setEmployee(slotCreationRequest.getEmployee());
    slot.setInstitution(institution);

    slotRepository.save(slot);
    return Response.ok().build();
  }
}
