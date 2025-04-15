package io.github.ksantanac.quarkussocial.rest;

import io.github.ksantanac.quarkussocial.domain.repository.FollowerRepository;
import io.github.ksantanac.quarkussocial.domain.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users/{user_id}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private FollowerRepository repository;
    private UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }




}
