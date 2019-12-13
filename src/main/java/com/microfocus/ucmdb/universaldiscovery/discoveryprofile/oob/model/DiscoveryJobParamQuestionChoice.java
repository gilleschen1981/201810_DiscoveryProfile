package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.ArrayList;

public class DiscoveryJobParamQuestionChoice {
    private String choice;
    private String choiceId;
    private String defaultValue;
    private String param;
    private String paramValue;

    public DiscoveryJobParamQuestionChoice(ArrayList<String> valueList) {
        choice = valueList.get(0);
        choiceId = valueList.get(1);
        defaultValue = valueList.get(2);
        param = valueList.get(3);
        paramValue = valueList.get(4);
        if("TRUE".equals(paramValue) || "FALSE".equals(paramValue)){
            paramValue = paramValue.toLowerCase();
        }
    }

    public String getChoice() {
        return choice;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getParam() {
        return param;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }
}
