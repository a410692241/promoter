package com.linayi.entity.user;


import java.io.Serializable;
import java.util.Objects;

public class Auth implements Serializable {
    private String access_token;
    private Double expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String nickname;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String privilege;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Double getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Double expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", nickname='" + nickname + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege='" + privilege + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return Objects.equals(access_token, auth.access_token) &&
                Objects.equals(expires_in, auth.expires_in) &&
                Objects.equals(refresh_token, auth.refresh_token) &&
                Objects.equals(openid, auth.openid) &&
                Objects.equals(scope, auth.scope) &&
                Objects.equals(nickname, auth.nickname) &&
                Objects.equals(province, auth.province) &&
                Objects.equals(city, auth.city) &&
                Objects.equals(country, auth.country) &&
                Objects.equals(headimgurl, auth.headimgurl) &&
                Objects.equals(privilege, auth.privilege) &&
                Objects.equals(unionid, auth.unionid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, expires_in, refresh_token, openid, scope, nickname, province, city, country, headimgurl, privilege, unionid);
    }
}
