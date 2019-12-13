package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryJobParamQuestion {
    private String jobName;
    private String question;
    private String questionId;
    private String helpMessage;
    private DiscoveryJobQuestion.JOB_QUESTION_TYPE questionType;
    private List<DiscoveryJobParamQuestionChoice> choiceList;

    public DiscoveryJobParamQuestion(ArrayList<String> valueList) {
        jobName = valueList.get(0);
        question = valueList.get(1);
        questionId = valueList.get(2);
        helpMessage = valueList.get(3);
        try {
            questionType = DiscoveryJobQuestion.JOB_QUESTION_TYPE.valueOf(valueList.get(4));
        }catch (IllegalArgumentException e){
            System.out.println("QuestionType value invalid: " + valueList.get(4));
        }
    }

    public String getJobName() {
        return jobName;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public DiscoveryJobQuestion.JOB_QUESTION_TYPE getQuestionType() {
        return questionType;
    }

    public List<DiscoveryJobParamQuestionChoice> getChoiceList() {
        if(choiceList == null){
            choiceList = new ArrayList<DiscoveryJobParamQuestionChoice>();
        }
        return choiceList;
    }

    public void addChoice(DiscoveryJobParamQuestionChoice choice) {
        for(DiscoveryJobParamQuestionChoice c : getChoiceList()){
            if(c.getChoice().equals(choice.getChoice())){
                return;
            }
        }
        getChoiceList().add(choice);
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
