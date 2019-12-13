package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.ArrayList;

public class DiscoveryTag {
    private String tagName;
    private String parentUsecaseName;
    private Boolean isCheckedByDefault;
    private ArrayList<String> jobList;
    private ArrayList<String> dependencyTags;
    private Integer tagLevel = 0;

    public DiscoveryTag(ArrayList<String> valueList) {
        tagName = valueList.get(0);
        parentUsecaseName = valueList.get(1);
        isCheckedByDefault = Boolean.valueOf(valueList.get(2));
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getParentUsecaseName() {
        return parentUsecaseName;
    }

    public void setParentUsecaseName(String parentUsecaseName) {
        this.parentUsecaseName = parentUsecaseName;
    }

    @Override
    public String toString() {
        StringBuilder rlt = new StringBuilder();
        rlt.append(getTagName());
        rlt.append("(");
        rlt.append(getTagLevel());
        rlt.append(")");
        rlt.append("\n");
        for(String job : getJobList()) {
            rlt.append("\t");
            rlt.append(job);
            rlt.append("\n");
        }
        return rlt.toString();
    }

    public Boolean getCheckedByDefault() {
        return isCheckedByDefault;
    }

    public void setCheckedByDefault(Boolean checkedByDefault) {
        isCheckedByDefault = checkedByDefault;
    }

    public ArrayList<String> getJobList() {
        if(jobList == null){
            jobList = new ArrayList<String>();
        }
        return jobList;
    }

    public void setJobList(ArrayList<String> jobList) {
        this.jobList = jobList;
    }

    public ArrayList<String> getDependencyTags() {
        if(dependencyTags == null){
            dependencyTags = new ArrayList<String>();
        }
        return dependencyTags;
    }

    public void setDependencyTags(ArrayList<String> dependencyTags) {
        this.dependencyTags = dependencyTags;
    }

    public Integer getTagLevel() {
        return tagLevel;
    }

    public void setTagLevel(Integer tagLevel) {
        this.tagLevel = tagLevel;
    }

    public void addJob(String jobName) {
        getJobList().add(jobName);
    }

    public void addDependTag(String tag) {
        getDependencyTags().add(tag);
    }
}
