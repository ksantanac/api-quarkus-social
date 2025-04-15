package io.github.ksantanac.quarkussocial.domain.repository;

import io.github.ksantanac.quarkussocial.domain.model.Follower;
import io.github.ksantanac.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user){

        var params = Parameters.with("follower", follower).and("user", user).map();

        var query = find("follower =:follower and user =:user", params);

        // Pode retornar ou não o objeto, pq pode nao existir.
        var result = query.firstResultOptional();

        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId){
        var query = find("user.id", userId);
        return query.list();
    }


    public void deleteByFollowerAndUser(Long followerId, Long userId) {

        var params = Parameters
                .with("userId", userId)
                .and("followerId", followerId)
                .map();

        delete("follower.id =:followerId and user.id =:userId", params);

    }
}
