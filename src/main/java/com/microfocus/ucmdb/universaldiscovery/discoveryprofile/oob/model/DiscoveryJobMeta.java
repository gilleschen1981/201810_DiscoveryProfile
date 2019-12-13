package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.JobMetaExcelReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DiscoveryJobMeta implements Serializable {
    private String jobName;
    private String adapterName;
    private String moduleName;
    private String inputCIT;
    private ArrayList<String> tags;
    private ArrayList<String> dependOn;
    private ArrayList<String> protocols;
    private ArrayList<String> outputCIT;
    private Boolean isFlat;
    // Dynamic || Workflow
    private String jobType;

    public DiscoveryJobMeta(ArrayList<String> valueList) {
        jobName = valueList.get(0);
        adapterName = valueList.get(1);
        if(valueList.get(2).isEmpty()){
            tags = new ArrayList<String>();
        } else{
            tags = new ArrayList<String>(Arrays.asList(valueList.get(2).split(JobMetaExcelReader.EXCEL_VALUE_SEPARATOR)));
        }

        if(valueList.get(3).isEmpty()){
            dependOn = new ArrayList<String>();
        } else{
            dependOn = new ArrayList<String>(Arrays.asList(valueList.get(3).split(JobMetaExcelReader.EXCEL_VALUE_DEPENDON_SEPARATOR)));
        }

        moduleName = valueList.get(4);
        inputCIT = valueList.get(5);
        isFlat = Boolean.valueOf(valueList.get(6));
        jobType = valueList.get(7);
        protocols = new ArrayList<String>(Arrays.asList(valueList.get(8).split(JobMetaExcelReader.EXCEL_VALUE_SEPARATOR)));
        outputCIT = new ArrayList<String>(Arrays.asList(valueList.get(9).split(JobMetaExcelReader.EXCEL_VALUE_SEPARATOR)));
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getInputCIT() {
        return inputCIT;
    }

    public void setInputCIT(String inputCIT) {
        this.inputCIT = inputCIT;
    }

    public ArrayList<String> getTags() {
        if(tags == null){
            tags = new ArrayList<String>();
        }
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getDependOn() {
        if(dependOn == null){
            dependOn = new ArrayList<String>();
        }
        return dependOn;
    }

    public void setDependOn(ArrayList<String> dependOn) {
        this.dependOn = dependOn;
    }

    public ArrayList<String> getProtocols() {
        if(protocols == null){
            protocols = new ArrayList<String>();
        }
        return protocols;
    }

    public void setProtocols(ArrayList<String> protocols) {
        this.protocols = protocols;
    }

    public ArrayList<String> getOutputCIT() {
        if(outputCIT == null){
            outputCIT = new ArrayList<String>();
        }
        return outputCIT;
    }

    public void setOutputCIT(ArrayList<String> outputCIT) {
        this.outputCIT = outputCIT;
    }

    public Boolean getFlat() {
        return isFlat;
    }

    public void setFlat(Boolean flat) {
        isFlat = flat;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
