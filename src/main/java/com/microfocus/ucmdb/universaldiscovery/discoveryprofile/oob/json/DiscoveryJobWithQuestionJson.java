package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryJobWithQuestionJson implements Serializable {
    private String name;
    private String oob;
    List<DiscoveryJobParamQuestionJson> questions;

    public DiscoveryJobWithQuestionJson(String name, String oob) {
        this.name = name;
        this.oob = oob;
        questions = new ArrayList<DiscoveryJobParamQuestionJson>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOob() {
        return oob;
    }

    public void setOob(String oob) {
        this.oob = oob;
    }

    public List<DiscoveryJobParamQuestionJson> getQuestions() {
        if(questions == null){
            questions = new ArrayList<DiscoveryJobParamQuestionJson>();
        }
        return questions;
    }

    public void setQuestions(List<DiscoveryJobParamQuestionJson> questions) {
        this.questions = questions;
    }

    public void addQuestion(DiscoveryJobParamQuestionJson question){
        getQuestions().add(question);
        return;
    }
}
