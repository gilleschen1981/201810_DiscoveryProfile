package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGenerator {

    public void generateParameQuestions(DiscoveryMetaRepository repo) {
        for(Map.Entry<String, List<DiscoveryJobParamQuestion>> entry : repo.getParamQuestionMap().entrySet()){
            if(entry.getValue().size() <= 0){
                continue;
            }
            DiscoveryJobWithQuestionJson jobJson = generateJobJson(entry.getKey(), entry.getValue());
            ObjectMapper mapper = new ObjectMapper();
            FileWriter writer = null;
            try {
                File folder = new File("output","question");
                File questionFile = new File(folder,"JOB_" + entry.getKey() + ".json");
                writer = new FileWriter(questionFile);
                mapper.writeValue(writer, jobJson);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DiscoveryJobWithQuestionJson generateJobJson(String jobName, List<DiscoveryJobParamQuestion> questionList) {
        DiscoveryJobWithQuestionJson rlt = new DiscoveryJobWithQuestionJson(jobName, "true");
        for(DiscoveryJobParamQuestion question : questionList){
            DiscoveryJobParamQuestionJson json = new DiscoveryJobParamQuestionJson(question.getQuestion(), question.getQuestionId(), question.getHelpMessage(), question.getQuestionType().getType());
            for(DiscoveryJobParamQuestionChoice choice : question.getChoiceList()){
                DiscoveryJobParamQuestionChoiceJson choiceJson = new DiscoveryJobParamQuestionChoiceJson(choice.getChoice(), choice.getChoiceId(), choice.getDefaultValue(), choice.getParam(), choice.getParamValue());
                json.addChoice(choiceJson);
            }
            rlt.addQuestion(json);
        }
        return rlt;
    }

    public void generateJobQuestions(DiscoveryMetaRepository repo) {
        for(Map.Entry<String, List<DiscoveryJobQuestion>> entry : repo.getJobQuestionMap().entrySet()){
            if(entry.getValue().size() <= 0){
                continue;
            }
            DiscoveryTagWithQuestionJson tagJson = generateTagJson(entry.getKey(), entry.getValue());
            ObjectMapper mapper = new ObjectMapper();
            FileWriter writer = null;
            try {
                File folder = new File("output","question");
                File questionFile = new File(folder,"TAG_" + entry.getKey() + ".json");
                writer = new FileWriter(questionFile);
                mapper.writeValue(writer, tagJson);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DiscoveryTagWithQuestionJson generateTagJson(String tagName, List<DiscoveryJobQuestion> questionList) {
        DiscoveryTagWithQuestionJson rlt = new DiscoveryTagWithQuestionJson(tagName, "true");
        for(DiscoveryJobQuestion question : questionList){
            DiscoveryJobQuestionJson json = new DiscoveryJobQuestionJson(question.getQuestion(), question.getQuestionid(), question.getHelpMessage(), question.getQuestionType().getType());
            for(DiscoveryJobQuestionChoice choice : question.getChoiceList()){
                DiscoveryJobQuestionChoiceJson choiceJson = new DiscoveryJobQuestionChoiceJson(choice.getChoice(), choice.getChoiceId(), choice.getDefaultValue().getChoice(), choice.getBehavior().getBehavior(), choice.getJobList());
                json.addChoice(choiceJson);
            }
            rlt.addQuestion(json);
        }
        return rlt;
    }

    public void generateQuestionMapping(DiscoveryMetaRepository repo) {
        Map<String, String> questionMap = new HashMap<String, String>();
        for(List<DiscoveryJobQuestion> jql : repo.getJobQuestionMap().values()){
            for(DiscoveryJobQuestion jq : jql){
                if(questionMap.get(jq.getQuestionid()) == null){
                    questionMap.put(jq.getQuestionid(), jq.getQuestion());
                } else{
                    if(!questionMap.get(jq.getQuestionid()).equals(jq.getQuestion())){
                        System.out.println("[ERROR]question id conflict: " + questionMap.get(jq.getQuestionid()) + " vs " + jq.getQuestion());
                    }
                }
                for(DiscoveryJobQuestionChoice c : jq.getChoiceList()){
                    if(questionMap.get(c.getChoiceId()) == null){
                        questionMap.put(c.getChoiceId(), c.getChoice());
                    } else{
                        if(!questionMap.get(c.getChoiceId()).equals(c.getChoice())){
                            System.out.println("[ERROR]choice id conflict: " + questionMap.get(c.getChoice()) + " vs " + c.getChoice());
                        }
                    }
                }
            }
        }
        for(List<DiscoveryJobParamQuestion> pql : repo.getParamQuestionMap().values()){
            for(DiscoveryJobParamQuestion qp : pql){
                if(questionMap.get(qp.getQuestionId()) == null){
                    questionMap.put(qp.getQuestionId(), qp.getQuestion());
                } else{
                    if(!questionMap.get(qp.getQuestionId()).equals(qp.getQuestion())){
                        System.out.println("[ERROR]param question id conflict: " + questionMap.get(qp.getQuestion()) + " vs " + qp.getQuestion());
                    }
                }
                for(DiscoveryJobParamQuestionChoice c : qp.getChoiceList()){
                    if(questionMap.get(c.getChoiceId()) == null){
                        questionMap.put(c.getChoiceId(), c.getChoice());
                    } else{
                        if(!questionMap.get(c.getChoiceId()).equals(c.getChoice())){
                            System.out.println("[ERROR]param choice id conflict: " + questionMap.get(c.getChoice()) + " vs " + c.getChoice());
                        }
                    }
                }
            }
        }
        FileWriter writer = null;
        try {
            File questionFile = new File("output","questionid" + ".json");
            writer = new FileWriter(questionFile);
            for(Map.Entry<String, String> entry : questionMap.entrySet()){
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
