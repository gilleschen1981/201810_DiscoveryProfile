package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuping on 7/10/2018.
 */
public class DiscoveryProfile implements Serializable {
    private String URMId;
    private String name;
    private String type;
    private List<DiscoveryJob> jobList = new ArrayList<DiscoveryJob>();

    public DiscoveryProfile(String URMId, String name, String type, List<DiscoveryJob> jobList) {
        this.URMId = URMId;
        this.name = name;
        // oob, clone, customized
        this.type = type;
        this.jobList = jobList;
    }

    public String getURMId() {
        return URMId;
    }

    public void setURMId(String URMId) {
        this.URMId = URMId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DiscoveryJob> getJobList() {
        return jobList;
    }

    public void setJobList(List<DiscoveryJob> jobList) {
        this.jobList = jobList;
    }

    @Override
    public String toString() {
        return "DiscoveryProfile: {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", jobList=" + jobList +
                '}';
    }
}

