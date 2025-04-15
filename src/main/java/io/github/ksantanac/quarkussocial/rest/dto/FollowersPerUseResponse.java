package io.github.ksantanac.quarkussocial.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowersPerUseResponse {

    private Integer followersCount;
    private List<FollowerResponse> contet;


}
