package io.github.ksantanac.quarkussocial.rest;

import io.github.ksantanac.quarkussocial.domain.model.Post;
import io.github.ksantanac.quarkussocial.domain.model.User;
import io.github.ksantanac.quarkussocial.domain.repository.FollowerRepository;
import io.github.ksantanac.quarkussocial.domain.repository.PostRepository;
import io.github.ksantanac.quarkussocial.domain.repository.UserRepository;
import io.github.ksantanac.quarkussocial.rest.dto.CreatePostRequest;
import io.github.ksantanac.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;
    private PostRepository repository;
    private FollowerRepository followerRepository;

    @Inject
    public PostResource(
            UserRepository userRepository,
            PostRepository repository,
            FollowerRepository followerRepository
    ) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.followerRepository = followerRepository;

    }


    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){

        User user = userRepository.findById(userId);
        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPost(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId){

        User user = userRepository.findById(userId);
        if (user == null) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(followerId);

        // CASO NAO SIGA O USUARIO NAO PODE VER OS POSTS
        var follows = followerRepository.follows(follower, user);
        if (!follows) {
            return Response.status(Response.Status.FORBIDDEN).entity("You can't see these posts.").build();
        }

        // ESQUECEU DE PASSAR O HEADER
        if (followerId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("You forgot the header followerId").build();
        }

        if (follower == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Inexistent FollowerId").build();
        }

        var query =  repository.find("user", Sort.by("dateTime", Sort.Direction.Descending) , user);
        var list = query.list();

        var postResponseList = list.stream()
            .map(post -> PostResponse.fromEntity(post))
            .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }

}
