package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.JobMetaExcelReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DiscoveryMetaRepository {
    private static String OUTPUT_FOLDER = "output";
    private static DiscoveryMetaRepository _instance;
    public static DiscoveryMetaRepository getInstance(){
        if(_instance == null){
            _instance = new DiscoveryMetaRepository();
        }
        return _instance;
    }

    private Map<String, DiscoveryJobMeta> jobMetaMap;
    private DiscoveryUsecase rootUsecase;
    private Map<String, DiscoveryTag> tagMap;
    private Map<String, DiscoveryUsecase> usecaseMap;
    // tagName, questionList
    private Map<String, List<DiscoveryJobQuestion>> jobQuestionMap;
    // jobName, questionList
    private Map<String, List<DiscoveryJobParamQuestion>> paramQuestionMap;

    private DiscoveryMetaRepository() {
        jobMetaMap = new HashMap<String, DiscoveryJobMeta>();
        rootUsecase = null;
        tagMap = new HashMap<String, DiscoveryTag>();
        usecaseMap = new HashMap<String, DiscoveryUsecase>();
        jobQuestionMap = new HashMap<String, List<DiscoveryJobQuestion>>();
    }

    public Map<String, DiscoveryJobMeta> getJobMetaMap() {
        if(jobMetaMap == null){
            jobMetaMap = new HashMap<String, DiscoveryJobMeta>();
        }
        return jobMetaMap;
    }

    public void setJobMetaMap(Map<String, DiscoveryJobMeta> jobMetaMap) {
        this.jobMetaMap = jobMetaMap;
    }

    public DiscoveryUsecase getRootUsecase() {
        return rootUsecase;
    }

    public void setRootUsecase(DiscoveryUsecase rootUsecase) {
        this.rootUsecase = rootUsecase;
    }

    public Map<String, DiscoveryTag> getTagMap() {
        if(tagMap == null){
            tagMap = new HashMap<String, DiscoveryTag>();
        }
        return tagMap;
    }

    public void setTagMap(Map<String, DiscoveryTag> tagMap) {
        this.tagMap = tagMap;
    }

    public Map<String, DiscoveryUsecase> getUsecaseMap() {
        if(usecaseMap == null){
            usecaseMap = new HashMap<String, DiscoveryUsecase>();
        }
        return usecaseMap;
    }

    public void setUsecaseMap(Map<String, DiscoveryUsecase> usecaseMap) {
        this.usecaseMap = usecaseMap;
    }

    public void addJobMeta(DiscoveryJobMeta jobMeta) {
        getJobMetaMap().put(jobMeta.getJobName(), jobMeta);
    }

    public void addUseCase(DiscoveryUsecase usecase) {
        getUsecaseMap().put(usecase.getUsecaseName(), usecase);
    }

    public void addTag(DiscoveryTag tag) {
        getTagMap().put(tag.getTagName(), tag);
    }

    public Map<String, List<DiscoveryJobQuestion>> getJobQuestionMap() {
        if(jobQuestionMap == null)
        {
            jobQuestionMap = new HashMap<String, List<DiscoveryJobQuestion>>();
        }
        return jobQuestionMap;
    }

    public void addJobQuestion(DiscoveryJobQuestion question) {
        List<DiscoveryJobQuestion> questionlist = getJobQuestionMap().getOrDefault(question.getTagName(), new ArrayList<DiscoveryJobQuestion>());
        for(DiscoveryJobQuestion q : questionlist){
            if(q.getQuestion().equals(question.getQuestion())){
                return;
            }
        }
        questionlist.add(question);
        getJobQuestionMap().put(question.getTagName(), questionlist);
    }


    public void addEmptyJobQuestion(String tag) {
        if(getJobQuestionMap().get(tag) != null){
            return;
        }
        getJobQuestionMap().put(tag, new ArrayList<DiscoveryJobQuestion>());
    }

    public DiscoveryJobQuestion getJobQuestion(String tag, String question){
        List<DiscoveryJobQuestion> jl = getJobQuestionMap().get(tag);
        if( jl == null ){
            return null;
        }

        for(DiscoveryJobQuestion q : jl){
            if(q.getQuestion().equals(question)){
                return q;
            }
        }
        return null;
    }

    public Map<String, List<DiscoveryJobParamQuestion>> getParamQuestionMap() {
        if(paramQuestionMap == null){
            paramQuestionMap = new HashMap<String, List<DiscoveryJobParamQuestion>>();
        }
        return paramQuestionMap;
    }

    public void addParamQuestion(DiscoveryJobParamQuestion question) {
        List<DiscoveryJobParamQuestion> questionList = getParamQuestionMap().getOrDefault(question.getJobName(), new ArrayList<DiscoveryJobParamQuestion>());
        for(DiscoveryJobParamQuestion q : questionList){
            if(q.getQuestion().equals(question.getQuestion())){
                return;
            }
        }
        questionList.add(question);
        getParamQuestionMap().put(question.getJobName(), questionList);
    }
    public void addEmptyParamQuestion(String jobName) {
        if(getParamQuestionMap().get(jobName) != null){
            return;
        }
        getParamQuestionMap().put(jobName, new ArrayList<DiscoveryJobParamQuestion>());
    }

    public DiscoveryJobParamQuestion getParamQuestion(String jobName, String question){
        List<DiscoveryJobParamQuestion> questionList = getParamQuestionMap().get(jobName);
        if(questionList == null){
            return null;
        }
        for(DiscoveryJobParamQuestion q : questionList){
            if(q.getQuestion().equals(question)){
                return q;
            }
        }
        return null;
    }

    public void enrich() {
        // fill the tag and leaf use case
        for(Map.Entry<String, DiscoveryJobMeta> entry : getJobMetaMap().entrySet()){
            for(String tag : entry.getValue().getTags()){
                if(JobMetaExcelReader.EXCEL_NULL_VALUE.equals(tag)){
                    continue;
                }
                DiscoveryTag discoveryTag = getTagMap().get(tag);
                if(discoveryTag == null){
                    System.out.println("[ERROR]Tag missing: " + tag + ". Job name: " + entry.getKey());
                } else{
                    discoveryTag.addJob(entry.getKey());
                }
            }
        }
        // fill the use case
        for(Map.Entry<String, DiscoveryTag> entry : getTagMap().entrySet()){
            String parent = entry.getValue().getParentUsecaseName();
            if(JobMetaExcelReader.EXCEL_NULL_VALUE.equals(parent)){
                continue;
            }
            DiscoveryUsecase discoveryUsecase = getUsecaseMap().get(parent);
            if(discoveryUsecase == null){
                System.out.println("[ERROR]Usecase missing: " + parent);
            } else{
                discoveryUsecase.addTag(entry.getKey());
            }
        }
        for(Map.Entry<String, DiscoveryUsecase> entry : getUsecaseMap().entrySet()){
            String parent = entry.getValue().getParentUsecaseName();
            if(JobMetaExcelReader.EXCEL_NULL_VALUE.equals(parent)){
                continue;
            }
            DiscoveryUsecase discoveryUsecase = getUsecaseMap().get(parent);
            if(discoveryUsecase == null){
                System.out.println("[ERROR]Usecase missing: " + parent);
            } else{
                discoveryUsecase.addSubUsecase(entry.getKey());
            }
        }

        // set the use case root
        setRootUsecase(getUsecaseMap().get("Discovery"));

        // tag level and dependency tag
        for(int layer = 1; layer <=5; layer++){
            for(DiscoveryTag discoveryTag : getTagMap().values()){
                Set<String> dependJobSet = new HashSet<String>();
                for(String jobName : discoveryTag.getJobList()){
                    DiscoveryJobMeta job = getJobMetaMap().get(jobName);
                    if(job != null){
                        if(job.getDependOn() != null && job.getDependOn().size() > 0){
                            String dependOnJobName = job.getDependOn().get(0);
                            dependJobSet.add(job.getDependOn().get(0));
                        }
                    }
                }
                // level 1
                if(dependJobSet.contains(JobMetaExcelReader.EXCEL_NULL_VALUE)){
                    discoveryTag.setTagLevel(1);
                } else{
                    int minLevel = 5;
                    String minTag = null;
                    for(String dependJob : dependJobSet){
                        DiscoveryJobMeta tJob = getJobMetaMap().get(dependJob);
                        if(tJob == null){
                            System.out.println("[ERROR]job missing: " + dependJob + ". source tag: " + discoveryTag.getTagName());
                        } else{
                            ArrayList<String> tTags = tJob.getTags();
                            if(tTags != null && tTags.size() > 0){
                                DiscoveryTag tTag = getTagMap().get(tTags.get(0));
                                if(tTag != null){
                                    if(tTag.getTagLevel() < minLevel){
                                        minLevel = tTag.getTagLevel();
                                        minTag = tTag.getTagName();
                                    }
                                }
                            }
                        }

                    }
                    discoveryTag.setTagLevel(minLevel + 1);
                    discoveryTag.addDependTag(minTag);

                }
            }
        }
    }

    public void printJobTree() {
        try {
            File outputFolder = new File(OUTPUT_FOLDER);
            if(!outputFolder.exists()){
                outputFolder.mkdir();
            }
            File treeOutput = new File(outputFolder, "jobTree.txt");
            FileWriter writer = new FileWriter(treeOutput);
            writer.write(getRootUsecase().toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generateQuestionID() {
        for(Map.Entry<String, List<DiscoveryJobQuestion>> entry : getJobQuestionMap().entrySet()){
            String tagName = entry.getKey();
            List<DiscoveryJobQuestion> jl = entry.getValue();
            for(int i = 0; i < jl.size(); i++){
                String id = "QUESTION_" + tagName.toUpperCase() + "_" + i;
                DiscoveryJobQuestion question = jl.get(i);
                question.setQuestionid(id);
                for(int ci = 0; ci < question.getChoiceList().size(); ci++){
                    DiscoveryJobQuestionChoice choice = question.getChoiceList().get(ci);
                    if("No".equals(choice.getChoice())){
                        choice.setChoiceId("QUESTION_CHOICE_NO");
                    }else if("Yes".equals(choice.getChoice())){
                        choice.setChoiceId("QUESTION_CHOICE_YES");
                    }else{
                        String cid = id + "_" + "CHOICE_" + ci;
                        choice.setChoiceId(cid);
                    }
                }
            }
        }

        for(Map.Entry<String, List<DiscoveryJobParamQuestion>> entry : getParamQuestionMap().entrySet()){
            String jobName = entry.getKey();
            List<DiscoveryJobParamQuestion> ql = entry.getValue();
            for(int i = 0; i < ql.size(); i++){
                String id = "QUESTION_" + jobName.toUpperCase() + "_" + i;
                DiscoveryJobParamQuestion question = ql.get(i);
                question.setQuestionId(id);
                for(int ci = 0; ci < question.getChoiceList().size(); ci++){
                    DiscoveryJobParamQuestionChoice choice = question.getChoiceList().get(ci);
                    if("No".equals(choice.getChoice())){
                        choice.setChoiceId("QUESTION_CHOICE_NO");
                    }else if("Yes".equals(choice.getChoice())){
                        choice.setChoiceId("QUESTION_CHOICE_YES");
                    }else{
                        String cid = id + "_" + "CHOICE_" + ci;
                        choice.setChoiceId(cid);
                    }
                }
            }
        }
    }

    public void validate() {
        // jobmeta validation
        Set<String> tagSet = getTagMap().keySet();
        Set<String> jobSet = getJobMetaMap().keySet();
        for(DiscoveryJobMeta jm : getJobMetaMap().values()){
            // job's tag must exist
            for(String t : jm.getTags()){
                if(!tagSet.contains(t)){
                    System.out.println("[ERROR]JobMeta error, job: " + jm.getJobName() + ". Tag inexist: " + t);
                }
            }
            // dependon job must exist
            for(String d : jm.getDependOn()){
                if(!jobSet.contains(d)){
                    System.out.println("[ERROR]JobMeta error, job: " + jm.getJobName() + ". Dependon inexist: " + d);
                }
            }
        }

        // job question validation
        // tag must exist
        for(String jobTag : getJobQuestionMap().keySet()){
            if(!tagSet.contains(jobTag)){
                System.out.println("[ERROR]JobQuestion error, tag inexist: " + jobTag);
            }
        }
        // all tags must have a questionlist (can be empty but not null)
        for(String tag: tagSet){
            if(!getJobQuestionMap().keySet().contains(tag)){
                System.out.println("[ERROR]JobQuestion missing for the tag: " + tag);
            }
        }
        // all questions should have a unique id
        Set<String> questionidSet = new HashSet<String>();
        for(List<DiscoveryJobQuestion> ql : getJobQuestionMap().values()){
            for(DiscoveryJobQuestion question : ql){
                if(questionidSet.contains(question.getQuestionid())){
                    System.out.println("[ERROR]JobQuestion id conflict. Question: " + question.getQuestion());
                } else{
                    questionidSet.add(question.getQuestionid());
                }
            }
        }
        // DropDownlist, Yesno, radiobox questions should have only 1 checked choice
        for(List<DiscoveryJobQuestion> ql : getJobQuestionMap().values()){
            for(DiscoveryJobQuestion question : ql){
                if(question.getQuestionType().equals(DiscoveryJobQuestion.JOB_QUESTION_TYPE.DropDownList)
                        ||question.getQuestionType().equals(DiscoveryJobQuestion.JOB_QUESTION_TYPE.RadioBox)){
                    boolean hasCheckedValue = false;
                    for(DiscoveryJobQuestionChoice c : question.getChoiceList()){
                        if(c.getDefaultValue().equals(DiscoveryJobQuestionChoice.DEFAULT_VALUE.Checked)){
                            if(hasCheckedValue == true){
                                System.out.println("[ERROR]JobQuestion has contradict default value: " + question.getQuestion());
                                break;
                            } else{
                                hasCheckedValue = true;
                            }
                        }
                    }
                }
                if(question.getQuestionType().equals(DiscoveryJobQuestion.JOB_QUESTION_TYPE.YesNo)){
                    if(question.getChoiceList().size() != 2){
                        System.out.println("[ERROR]JobQuestion has contradict default value: " + question.getQuestion());
                        break;
                    }
                    boolean hasYes = false;
                    boolean hasNo = false;
                    for(DiscoveryJobQuestionChoice c : question.getChoiceList()){
                        if(c.getDefaultValue().equals(DiscoveryJobQuestionChoice.DEFAULT_VALUE.Checked)){
                            hasYes = true;
                        }
                        if(c.getDefaultValue().equals(DiscoveryJobQuestionChoice.DEFAULT_VALUE.Unchecked)){
                            hasNo = true;
                        }
                    }
                    if(!hasYes || !hasNo){
                        System.out.println("[ERROR]JobQuestion has contradict default value: " + question.getQuestion());
                    }
                }
            }
        }

        // jobname must exist and belong to the right tag
        for(Map.Entry<String, List<DiscoveryJobQuestion>> entry : getJobQuestionMap().entrySet()){
            for(DiscoveryJobQuestion question : entry.getValue()){
                for(DiscoveryJobQuestionChoice choice : question.getChoiceList()){
                    for(String jobName : choice.getJobList()){
                        DiscoveryJobMeta jobMeta = getJobMetaMap().get(jobName);
                        if(jobMeta == null){
                            System.out.println("[ERROR]JobQuestion has inexist jobName: " + jobName);
                            break;
                        }
                        if(!jobMeta.getTags().contains(entry.getKey())){
                            System.out.println("[ERROR]JobQuestion has jobName with the wrong tag: " + jobName);
                            break;
                        }
                    }
                }
            }
        }

        // param question: job must exist
        for(String jobName : getParamQuestionMap().keySet()){
            if(!jobSet.contains(jobName)){
                System.out.println("[ERROR]JobParamQuestion has inexist jobName: " + jobName);
                break;
            }
        }
    }

}
