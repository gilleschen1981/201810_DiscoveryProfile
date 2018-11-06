package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuping on 7/10/2018.
 */
public class DiscoveryJob implements Serializable {
    private String jobName;
    private String adapterName;
    private String module;
    private String inputCI;
    private Boolean isChecked;
    private List<String> protocol = new ArrayList<String>();
    private List<String> jobParameterName = new ArrayList<String>();
    private List<String> jobParameterValue = new ArrayList<String>();
    private List<String> triggerQueries = new ArrayList<String>();
    private Boolean jobInvokeOnNewTrigger;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public DiscoveryJob(String jobName, String adapterName, String module, String inputCI, List<String> protocol, List<String> jobParameterName, List<String> jobParameterValue, List<String> triggerQueries, Boolean jobInvokeOnNewTrigger) {
        this.jobName = jobName;
        this.adapterName = adapterName;
        this.module = module;
        this.inputCI = inputCI;
        this.protocol = protocol;
        this.jobParameterName = jobParameterName;
        this.jobParameterValue = jobParameterValue;
        this.triggerQueries = triggerQueries;
        this.jobInvokeOnNewTrigger = jobInvokeOnNewTrigger;
        isChecked = true;
    }

    public List<String> getProtocol() {
        return protocol;
    }

    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
    }

    public List<String> getTriggerQueries() {
        return triggerQueries;
    }

    public void setTriggerQueries(List<String> triggerQueries) {
        this.triggerQueries = triggerQueries;
    }

    public List<String> getJobParameterName() {
        return jobParameterName;
    }

    public void setJobParameterName(List<String> jobParameterName) {
        this.jobParameterName = jobParameterName;
    }

    public List<String> getJobParameterValue() {
        return jobParameterValue;
    }

    public void setJobParameterValue(List<String> jobParameterValue) {
        this.jobParameterValue = jobParameterValue;
    }

    public Boolean getJobInvokeOnNewTrigger() {
        return jobInvokeOnNewTrigger;
    }

    public void setJobInvokeOnNewTrigger(Boolean jobInvokeOnNewTrigger) {
        this.jobInvokeOnNewTrigger = jobInvokeOnNewTrigger;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getInputCI() {
        return inputCI;
    }

    public void setInputCI(String inputCI) {
        this.inputCI = inputCI;
    }


    @Override
    public String toString() {
        return "DiscoveryJob: {" +
                "jobName='" + jobName + '\'' +
                ", adapterName='" + adapterName + '\'' +
                ", module='" + module + '\'' +
                ", inputCI='" + inputCI + '\'' +
                ", Protocol='" + protocol + '\'' +
                ", jobParameterName='" + jobParameterName + '\'' +
                ", jobParameterValue='" + jobParameterValue + '\'' +
                ", jobInvokeOnNewTrigger=" + jobInvokeOnNewTrigger +
                '}';
    }
}
