package com.linayi.entity.app;

public class AppInfo {
    private Double version;

    private String description;

    private String force;

    private String androidDownloadLink;

    private String iosDownloadLink;

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public String getAndroidDownloadLink() {
        return androidDownloadLink;
    }

    public void setAndroidDownloadLink(String androidDownloadLink) {
        this.androidDownloadLink = androidDownloadLink;
    }

    public String getIosDownloadLink() {
        return iosDownloadLink;
    }

    public void setIosDownloadLink(String iosDownloadLink) {
        this.iosDownloadLink = iosDownloadLink;
    }

}
