package io.github.ksantanac.quarkussocial.rest;

import io.github.ksantanac.quarkussocial.domain.model.Follower;
import io.github.ksantanac.quarkussocial.domain.repository.FollowerRepository;
import io.github.ksantanac.quarkussocial.domain.repository.UserRepository;
import io.github.ksantanac.quarkussocial.rest.dto.FollowerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @PUT
    public Response followerUser(@PathParam("user_id") Long userId, FollowerRequest request) {
        var user = userRepository.findById(userId);

        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        var entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);

        repository.persist(entity);

        return Response.status(Response.Status.NO_CONTENT).build();

    }




}
