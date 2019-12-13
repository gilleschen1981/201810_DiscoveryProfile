package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryJobParamQuestionJson implements Serializable {
    private String questionTextAlt;
    private String questionTextL10ID;
    private String helpID;
    private String questionType;
    private List<DiscoveryJobParamQuestionChoiceJson> choices;

    public DiscoveryJobParamQuestionJson(String questionTextAlt, String questionTextL10ID, String helpID, String questionType) {
        this.questionTextAlt = questionTextAlt;
        this.questionTextL10ID = questionTextL10ID;
        this.helpID = helpID;
        this.questionType = questionType;
    }

    public void addChoice(DiscoveryJobParamQuestionChoiceJson choice){
        getChoices().add(choice);
        return;
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

    public List<DiscoveryJobParamQuestionChoiceJson> getChoices() {
        if(choices == null){
            choices = new ArrayList<DiscoveryJobParamQuestionChoiceJson>();
        }
        return choices;
    }

    public void setChoices(List<DiscoveryJobParamQuestionChoiceJson> choices) {
        this.choices = choices;
    }
}
