package com.linayi.entity.community;

public class CommunitySupermarket {
    private Long communitySupermarketId;

    private Integer communityId;

    private Integer supermarketId;

    private Integer rank;

    public Long getCommunitySupermarketId() {
        return communitySupermarketId;
    }

    public void setCommunitySupermarketId(Long communitySupermarketId) {
        this.communitySupermarketId = communitySupermarketId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(Integer supermarketId) {
        this.supermarketId = supermarketId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}