package de.getdate.backend.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.Form;
import de.getdate.backend.data.StringId;
import de.getdate.backend.dto.AddEmployeeRequest;
import de.getdate.backend.dto.CollectionDto;
import de.getdate.backend.dto.InstitutionDto;
import de.getdate.backend.dto.SlotDto;
import de.getdate.backend.infrastructure.InstitutionRepository;
import de.getdate.backend.model.Address;
import de.getdate.backend.model.Institution;
import de.getdate.backend.model.User;
import de.getdate.backend.search.SearchParams;
import de.getdate.backend.search.SearchUtility;
import de.getdate.backend.service.InsitututionService;
import de.getdate.backend.service.UserService;

@Path("/api/institute")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstitutionController {
  @Inject
  protected InstitutionRepository institutionRepository;

  @Inject
  protected InsitututionService institutionService;

  @Inject
  protected UserService userService;

  @POST
  @Path("/")
  @Transactional
  public Response createInstitute(InstitutionDto institutionDto) {
    if (institutionRepository.findByName(institutionDto.getName()) != null)
      return Response.status(Status.BAD_REQUEST).build();

    Address address = new Address();
    address.setCountry(institutionDto.getAddress().getCountry());
    address.setCity(institutionDto.getAddress().getCity());
    address.setZipCode(institutionDto.getAddress().getZipCode());
    address.setStreet(institutionDto.getAddress().getStreet());
    address.setHouseNumber(institutionDto.getAddress().getHouseNumber());
    address.setLatitude(institutionDto.getAddress().getLatitude());
    address.setLongtitude(institutionDto.getAddress().getLongitude());

    Institution institution = new Institution();
    institution.setName(institutionDto.getName());
    institution.setPhone(institutionDto.getPhone());
    institution.setAddress(address);
    institution.setDescription(institutionDto.getDescription());

    institutionRepository.save(institution);
    return Response.ok().build();
  }

  @GET
  @Path("{id}")
  public Response getById(@PathParam("id") String id) {
    return SearchUtility.<Institution, StringId>getSingleResponse(institutionRepository,
        InstitutionDto::fromSingle, StringId.valueOf(id));
  }

  @GET
  @Path("/")
  public Response search(@Form SearchParams searchParams) {
    if (!isSpecificSearch(searchParams)) {
      return SearchUtility.getAllResponse(institutionRepository, InstitutionDto::fromList,
          searchParams);
    } else {
      return SearchUtility.getSpecificResponse(institutionRepository, InstitutionDto::fromList,
          searchParams);
    }
  }

  @GET
  @Path("/{id}/slots")
  public Response searchSlot(@Form SearchParams searchParams, @PathParam("id") String id) {
    Institution institution = institutionRepository.findById(StringId.valueOf(id));
    if (institution == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    return Response.ok(
        new CollectionDto<>(SlotDto.from(institution.getSlots()), institution.getSlots().size()))
        .build();
  }

  @POST
  @Path("/{id}/employees")
  public Response addEmployee(@Context SecurityContext securityContext,
      AddEmployeeRequest addEmployeeRequest) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();

    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    if (user.getInstitution() == null)
      return Response.status(401).build();

    Institution institution = user.getInstitution();
    institution.addEmployee(addEmployeeRequest.getName());
    return Response.ok().build();
  }

  private boolean isSpecificSearch(SearchParams searchParams) {
    return StringUtils.isNotBlank(searchParams.getQuery());
  }
}
