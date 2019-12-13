package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.JobMetaExcelReader;

import java.util.ArrayList;
import java.util.Arrays;

public class DiscoveryJobQuestionChoice {
    public enum DEFAULT_VALUE
    {
        Checked("Checked"),
        Unchecked("Unchecked");

        private String choice;

        DEFAULT_VALUE(String choice) {
            this.choice = choice;
        }

        public String getChoice() {
            return choice;
        }
    }

    public enum CHOICE_BEHAVIOR
    {
        Disable("Disable job"),
        NA("Job remain enabled");

        private String behavior;

        CHOICE_BEHAVIOR(String behavior) {
            this.behavior = behavior;
        }

        public String getBehavior() {
            return behavior;
        }
    }

    public DiscoveryJobQuestionChoice(ArrayList<String> valueList) {
        choice = valueList.get(0);
        choiceId = valueList.get(1);
        try {
            defaultValue = DEFAULT_VALUE.valueOf(valueList.get(2));
            behavior = CHOICE_BEHAVIOR.valueOf(valueList.get(3).split(" ")[0]);
        }catch (IllegalArgumentException e){
            System.out.println("Choice value invalid, default value: " + valueList.get(2) + ".  behavior: " + valueList.get(3));
        }

        jobList = new ArrayList<String>(Arrays.asList(valueList.get(4).split(JobMetaExcelReader.EXCEL_VALUE_SEPARATOR)));
        for(int i = 0; i < jobList.size(); i++){
            jobList.set(i, jobList.get(i).trim());
        }

    }

    private String choice;
    private String choiceId;
    private DEFAULT_VALUE defaultValue;
    private CHOICE_BEHAVIOR behavior;
    private ArrayList<String> jobList;

    public String getChoice() {
        return choice;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public DEFAULT_VALUE getDefaultValue() {
        return defaultValue;
    }

    public CHOICE_BEHAVIOR getBehavior() {
        return behavior;
    }

    public ArrayList<String> getJobList() {
        return jobList;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }
}
