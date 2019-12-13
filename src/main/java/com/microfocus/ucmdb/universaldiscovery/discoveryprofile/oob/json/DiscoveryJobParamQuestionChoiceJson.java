package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;

public class DiscoveryJobParamQuestionChoiceJson implements Serializable {
    private String choiceText;
    private String choiceTextL10Key;
    private String defaultState;
    private String mappingToParameterName;
    private String mappingToParameterValue;

    public DiscoveryJobParamQuestionChoiceJson(String choiceText, String choiceTextL10Key, String defaultState, String mappingToParameterName, String mappingToParameterValue) {
        this.choiceText = choiceText;
        this.choiceTextL10Key = choiceTextL10Key;
        this.defaultState = defaultState;
        this.mappingToParameterName = mappingToParameterName;
        this.mappingToParameterValue = mappingToParameterValue;
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

    public String getMappingToParameterValue() {
        return mappingToParameterValue;
    }

    public void setMappingToParameterValue(String mappingToParameterValue) {
        this.mappingToParameterValue = mappingToParameterValue;
    }

    public String getMappingToParameterName() {
        return mappingToParameterName;
    }

    public void setMappingToParameterName(String mappingToParameterName) {
        this.mappingToParameterName = mappingToParameterName;
    }
}
