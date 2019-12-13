package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.ArrayList;

public class DiscoveryJobQuestion {
    public enum JOB_QUESTION_TYPE
    {
        YesNo("YesNo"),
        DropDownList("DropDownList"),
        CheckboxList("CheckboxList"),
        RadioBox("RadioBox"),
        EditBox("EditBox");

        private String type;

        JOB_QUESTION_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public DiscoveryJobQuestion(ArrayList<String> valueList) {
        tagName = valueList.get(0);
        question = valueList.get(1);
        questionid = valueList.get(2);
        helpMessage = valueList.get(3);
        try {
            questionType = JOB_QUESTION_TYPE.valueOf(valueList.get(4));
        }catch (IllegalArgumentException e){
            System.out.println("QuestionType value invalid: " + valueList.get(4));
        }
    }

    private String tagName;
    private String question;
    private String questionid;
    private String helpMessage;
    private JOB_QUESTION_TYPE questionType;
    private ArrayList<DiscoveryJobQuestionChoice> choiceList;

    public String getTagName() {
        return tagName;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionid() {
        return questionid;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public JOB_QUESTION_TYPE getQuestionType() {
        return questionType;
    }

    public ArrayList<DiscoveryJobQuestionChoice> getChoiceList() {
        if(choiceList == null){
            choiceList = new ArrayList<DiscoveryJobQuestionChoice>();
        }
        return choiceList;
    }

    public void addChoice(DiscoveryJobQuestionChoice choice) {
        for(DiscoveryJobQuestionChoice c : getChoiceList()){
            if(c.getChoice().equals(choice.getChoice())){
                return;
            }
        }
        getChoiceList().add(choice);
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }
}
