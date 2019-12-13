package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryTagWithQuestionJson implements Serializable {
    private String name;
    private String oob;
    private List<DiscoveryJobQuestionJson> questions;

    public DiscoveryTagWithQuestionJson(String name, String oob) {
        this.name = name;
        this.oob = oob;
        questions = new ArrayList<DiscoveryJobQuestionJson>();
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

    public List<DiscoveryJobQuestionJson> getQuestions() {
        if(questions == null){
            questions = new ArrayList<DiscoveryJobQuestionJson>();
        }
        return questions;
    }

    public void setQuestions(List<DiscoveryJobQuestionJson> questions) {
        this.questions = questions;
    }

    public void addQuestion(DiscoveryJobQuestionJson question){
        getQuestions().add(question);
    }
}
