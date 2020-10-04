package de.getdate.backend.controller;

import javax.annotation.processing.Generated;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import de.getdate.backend.dto.UserRegistrationResult;
import de.getdate.backend.dto.LoginRequest;
import de.getdate.backend.dto.RegisterRequest;
import de.getdate.backend.dto.SlotDto;
import de.getdate.backend.dto.UpdateRequest;
import de.getdate.backend.dto.UserDto;
import de.getdate.backend.model.User;
import de.getdate.backend.service.UserService;
import io.quarkus.security.jpa.Roles;

@Path("/api/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
  @Inject
  private UserService userService;

  @GET
  @Path("/slots")
  public Response getSlots(@Context SecurityContext securityContext) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    User user = userService.getUser(securityContext.getUserPrincipal().getName());

    return Response.ok(SlotDto.from(user.getSlots())).build();
  }

  @POST
  @Path("/register")
  public Response register(RegisterRequest registerRequest) {
    UserRegistrationResult.Type res = userService.register(registerRequest);
    if (res == UserRegistrationResult.Type.ALREADY_EXISTS)
      return Response.status(Status.BAD_REQUEST).entity(res).build();
    return Response.ok(res).build();
  }

  @POST
  @Path("/login")
  public Response login(@Context SecurityContext securityContext) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    return Response.ok().build();
  }

  @GET
  public Response getUser(@Context SecurityContext securityContext) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    return Response.ok(UserDto.from(user)).build();
  }

  @PUT
  public Response updateUser(@Context SecurityContext securityContext, UpdateRequest updateRequest) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    userService.updateUser(user, updateRequest);
    return Response.ok().build();
  }


  @DELETE
  public Response deleteUser(@Context SecurityContext securityContext) {
    if (securityContext.getUserPrincipal() == null)
      return Response.status(401).build();
    User user = userService.getUser(securityContext.getUserPrincipal().getName());
    userService.removeUser(user);
    return Response.ok().build();
  }

}
