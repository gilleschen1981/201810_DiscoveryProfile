package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.HashSet;
import java.util.Set;

public class DiscoveryProfile {
    private String name;
    private String displayName;
    private String description;
    private String parentProfile;

    private Set<String> jobs;
    private Set<String> childProfiles;
    private Set<String> dependOnProfiles;

    public Set<String> getChildProfiles() {
        if(childProfiles == null){
            childProfiles = new HashSet<String>();
        }
        return childProfiles;
    }

    public void addChildProfiles(String child) {
        getChildProfiles().add(child);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getJobs() {
        if(jobs == null){
            jobs = new HashSet<String>();
        }
        return jobs;
    }

    public void addJob(String job) {
        getJobs().add(job);
    }

    public Set<String> getDependOnProfiles() {
        if(dependOnProfiles == null){
            dependOnProfiles = new HashSet<String>();
        }
        return dependOnProfiles;
    }

    public void addDependOnProfiles(String dependOn) {
        getDependOnProfiles().add(dependOn);
    }

    public String getParentProfile() {
        return parentProfile;
    }

    public void setParentProfile(String parentProfile) {
        this.parentProfile = parentProfile;
    }
}
