package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryJobQuestionChoiceJson {
    private String choiceText;
    private String choiceTextL10Key;
    private String defaultState;
    private String behavior;
    private List<String> mappingToJobs;

    public DiscoveryJobQuestionChoiceJson(String choiceText, String choiceTextL10Key, String defaultState, String behavior, List<String> mappingToJobs) {
        this.choiceText = choiceText;
        this.choiceTextL10Key = choiceTextL10Key;
        this.defaultState = defaultState;
        this.behavior = behavior;
        this.mappingToJobs = mappingToJobs;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public String getChoiceTextL10Key() {
        return choiceTextL10Key;
    }

    public void setChoiceTextL10Key(String choiceTextL10Key) {
        this.choiceTextL10Key = choiceTextL10Key;
    }

    public String getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(String defaultState) {
        this.defaultState = defaultState;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public List<String> getMappingToJobs() {
        if(mappingToJobs == null){
            mappingToJobs = new ArrayList<String>();
        }
        return mappingToJobs;
    }

    public void setMappingToJobs(List<String> mappingToJobs) {
        this.mappingToJobs = mappingToJobs;
    }

    public void addJob(String jobName){
        getMappingToJobs().add(jobName);
    }
}
