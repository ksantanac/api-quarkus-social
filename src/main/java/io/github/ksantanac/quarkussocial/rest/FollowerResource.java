package io.github.ksantanac.quarkussocial.rest;

import io.github.ksantanac.quarkussocial.domain.model.Follower;
import io.github.ksantanac.quarkussocial.domain.repository.FollowerRepository;
import io.github.ksantanac.quarkussocial.domain.repository.UserRepository;
import io.github.ksantanac.quarkussocial.rest.dto.FollowerRequest;
import io.github.ksantanac.quarkussocial.rest.dto.FollowerResponse;
import io.github.ksantanac.quarkussocial.rest.dto.FollowersPerUseResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

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
    @Transactional
    public Response followerUser(@PathParam("user_id") Long userId, FollowerRequest request) {

        if (userId.equals(request.getFollowerId())) {
            return Response.status(Response.Status.CONFLICT).entity("You can't follow yourself").build();
        }

        var user = userRepository.findById(userId);
        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = repository.follows(follower, user);

        if (!follows){

            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            repository.persist(entity);
        }


        return Response.status(Response.Status.NO_CONTENT).build();

    }

    @GET
    public Response listFollowers(@PathParam("user_id") Long userId){

        var user = userRepository.findById(userId);
        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        var list = repository.findByUser(userId);

        FollowersPerUseResponse responseObject = new FollowersPerUseResponse();
        responseObject.setFollowersCount(list.size());

        var followerList = list.stream()
                .map(FollowerResponse::new)
                .collect(Collectors.toList());

        responseObject.setContet(followerList);
        return Response.ok(responseObject).build();

    }


    @DELETE
    @Transactional
    public Response unfollowUser(@PathParam("user_id") Long userId, @QueryParam("followerId") Long followerId){

        var user = userRepository.findById(userId);
        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.deleteByFollowerAndUser(followerId, userId);

        return Response.status(Response.Status.NO_CONTENT).build();

    }





}
