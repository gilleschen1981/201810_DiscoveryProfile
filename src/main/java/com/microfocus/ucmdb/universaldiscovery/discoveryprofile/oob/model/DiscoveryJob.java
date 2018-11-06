package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

public class DiscoveryJob {
    private String jobName;
    private String discoveryProfileName;
    private String adapterName;
    private String moduleName;
    private String dependOnProfile;
    private String inputCi;
    private String protocols;
    private String outputCis;

    public DiscoveryJob(String jobName, String discoveryProfileName, String adapterName, String moduleName, String dependOnProfile, String inputCi, String protocols, String outputCis) {
        this.jobName = jobName;
        this.discoveryProfileName = discoveryProfileName;
        this.adapterName = adapterName;
        this.moduleName = moduleName;
        this.dependOnProfile = dependOnProfile;
        this.inputCi = inputCi;
        this.protocols = protocols;
        this.outputCis = outputCis;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDiscoveryProfileName() {
        return discoveryProfileName;
    }

    public void setDiscoveryProfileName(String discoveryProfileName) {
        this.discoveryProfileName = discoveryProfileName;
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

    public String getDependOnProfile() {
        return dependOnProfile;
    }

    public void setDependOnProfile(String dependOnProfile) {
        this.dependOnProfile = dependOnProfile;
    }

    public String getInputCi() {
        return inputCi;
    }

    public void setInputCi(String inputCi) {
        this.inputCi = inputCi;
    }

    public String getProtocols() {
        return protocols;
    }

    public void setProtocols(String protocols) {
        this.protocols = protocols;
    }

    public String getOutputCis() {
        return outputCis;
    }

    public void setOutputCis(String outputCis) {
        this.outputCis = outputCis;
    }
}
