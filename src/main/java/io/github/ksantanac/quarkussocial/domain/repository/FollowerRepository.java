package io.github.ksantanac.quarkussocial.domain.repository;

import io.github.ksantanac.quarkussocial.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {}
