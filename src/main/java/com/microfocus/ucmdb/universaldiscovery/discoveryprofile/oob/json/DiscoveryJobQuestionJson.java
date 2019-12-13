package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryJobQuestionJson implements Serializable {
    private String questionTextAlt;
    private String questionTextL10ID;
    private String helpID;
    private String questionType;
    private List<DiscoveryJobQuestionChoiceJson> choices;

    public DiscoveryJobQuestionJson(String questionTextAlt, String questionTextL10ID, String helpID, String questionType) {
        this.questionTextAlt = questionTextAlt;
        this.questionTextL10ID = questionTextL10ID;
        this.helpID = helpID;
        this.questionType = questionType;
        choices = new ArrayList<DiscoveryJobQuestionChoiceJson>();
    }

    public String getQuestionTextAlt() {
        return questionTextAlt;
    }

    public void setQuestionTextAlt(String questionTextAlt) {
        this.questionTextAlt = questionTextAlt;
    }

    public String getQuestionTextL10ID() {
        return questionTextL10ID;
    }

    public void setQuestionTextL10ID(String questionTextL10ID) {
        this.questionTextL10ID = questionTextL10ID;
    }

    public String getHelpID() {
        return helpID;
    }

    public void setHelpID(String helpID) {
        this.helpID = helpID;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<DiscoveryJobQuestionChoiceJson> getChoices() {
        if(choices == null){
            choices = new ArrayList<DiscoveryJobQuestionChoiceJson>();
        }
        return choices;
    }

    public void setChoices(List<DiscoveryJobQuestionChoiceJson> choices) {
        this.choices = choices;
    }

    public void addChoice(DiscoveryJobQuestionChoiceJson choice){
        getChoices().add(choice);
    }
}
