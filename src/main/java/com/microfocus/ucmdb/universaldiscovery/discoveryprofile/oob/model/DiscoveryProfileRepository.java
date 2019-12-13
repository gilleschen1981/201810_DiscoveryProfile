package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class DiscoveryProfileRepository {
    private static DiscoveryProfileRepository _instance;
    DiscoveryProfile root;
    // profile name, profile instance
    Map<String, DiscoveryProfile> profileMap;

    // jobname, job instance
    Map<String, DiscoveryJob> jobMap;

    public static DiscoveryProfileRepository getInstance(){
        if(_instance == null){
            _instance = new DiscoveryProfileRepository();
        }
        return _instance;
    }

    public Map<String, DiscoveryProfile> getProfileMap() {
        if(profileMap == null){
            profileMap = new HashMap<String, DiscoveryProfile>();
        }
        return profileMap;
    }

    public void addProfile(String profileName, DiscoveryProfile profile) {
        getProfileMap().put(profileName, profile);
    }

    public DiscoveryProfile findProfileByName(String profileName){
        return getProfileMap().get(profileName);
    }

    public Map<String, DiscoveryJob> getJobMap() {
        if(jobMap == null){
            jobMap = new HashMap<String, DiscoveryJob>();
        }
        return jobMap;
    }
    public void addJob(String jobName, DiscoveryJob job) {
        getJobMap().put(jobName, job);
    }
    public DiscoveryJob findJobByName(String jobName){
        return jobMap.get(jobName);
    }

    public DiscoveryProfileRepository() {
        root = new DiscoveryProfile();
        root.setName("Root");
        root.setDisplayName("Root");
        getProfileMap().put(root.getName(),root);
    }

    public DiscoveryProfile getRoot() {
        return root;
    }


    public void initializeProfile(List<DiscoveryProfile> profileList) {
        // fill in map
        for(DiscoveryProfile profile : profileList){
            getProfileMap().put(profile.getName(), profile);
        }

        // fill in childProfiles
        for(DiscoveryProfile profile : profileList){
            DiscoveryProfile parent = getProfileMap().get(profile.getParentProfile());
            if(parent == null){
                System.out.println("[Error]Wrong parent profile name: " + profile.getParentProfile() + ". Profile: " + profile.getName());
            }
            parent.addChildProfiles(profile.getName());
        }
    }

    public void initializeJob(List<DiscoveryJob> jobList) {
        // fill in map
        for(DiscoveryJob job : jobList){
           getJobMap().put(job.getJobName(), job);
        }

        // fill in jobs and dependon of the profile
        for(DiscoveryJob job : jobList){
            String rawName = job.getDiscoveryProfileName();
            String[] split = rawName.split("/");
            String profileName = split[split.length - 1];
            DiscoveryProfile parent = getProfileMap().get(profileName);
            if(parent == null){
                System.out.println("[Error]Wrong parent profile name: " + profileName + ". Job: " + job.getJobName());
            }

            parent.addJob(job.getJobName());


            String depend = job.getDependOnProfile();
            if(depend != null){
                DiscoveryProfile dependProfile = getProfileMap().get(depend);
                if(dependProfile == null){
                    System.out.println("[Error]Wrong depend profile name: " + depend + ". Job: " + job.getJobName());
                }
                parent.addDependOnProfiles(depend);
            }
        }
    }

    public String outputDependency() {
        String rlt = "Layer1: \n";
        // layer1
        HashSet<String> layer1 = new HashSet<String>();
        for(DiscoveryProfile profile : getLeafProfileList()){
            if(profile.getDependOnProfiles() == null || profile.getDependOnProfiles().size() == 0){
                rlt += profile.getName() + "\t";
                layer1.add(profile.getName());
            }
        }
        rlt += "\nLayer2: \n";

        // layer2
        HashSet<String> layer2 = new HashSet<String>();
        for(String layer1Profile : layer1){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer1Profile)){
                        rlt += profile.getName() + "(" + layer1Profile +  ")" + "\t";
                        layer2.add(profile.getName());
                        count ++;
                    }
                }
            }
            if(count > 0){
                rlt += "\n";
            }

        }
        rlt += "Layer3: \n";
        // Layer3
        HashSet<String> layer3 = new HashSet<String>();
        for(String layer2Profile : layer2){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer2Profile)){
                        if(!layer2.contains(profile.getName())){
                            rlt += profile.getName() + "(" + layer2Profile +  ")" + "\t";
                            layer3.add(profile.getName());
                            count++;
                        }
                    }
                }
            }
            if(count > 0){
                rlt += "\n";
            }
        }

        // Layer4
        rlt += "Layer4: \n";
        HashSet<String> layer4 = new HashSet<String>();
        for(String layer3Profile : layer3){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer3Profile)){
                        if(!layer2.contains(profile.getName()) && !layer3.contains(profile.getName())){
                            rlt += profile.getName() + "(" + layer3Profile +  ")" + "\t";
                            layer4.add(profile.getName());
                            count++;
                        }
                    }
                }
            }
            if(count > 0){
                rlt += "\n";
            }
        }


        rlt += "\nTotal: " + layer1.size() + " " + layer2.size() + " " + layer3.size() + " " + layer4.size();

        return rlt;
    }

    public ArrayList<DiscoveryProfile> getLeafProfileList() {
        ArrayList<DiscoveryProfile> rlt = new ArrayList<DiscoveryProfile>();
        for(DiscoveryProfile profile : getProfileMap().values()){
            if(profile.getChildProfiles() == null || profile.getChildProfiles().size() == 0){
                rlt.add(profile);
            }
        }
        return rlt;
    }

    public String outputProfileJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root  = generateObjectNodeFromProfile(getRoot(), mapper);
        return root.toString();
    }

    private ObjectNode generateObjectNodeFromProfile(DiscoveryProfile profile, ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("name", profile.getName());
        node.put("description", profile.getDescription());
        node.put("display", profile.getDisplayName());
        if(profile.getChildProfiles() != null && profile.getChildProfiles().size() > 0){
            ArrayNode childrenNode =  node.putArray("children");
            for(String child : profile.getChildProfiles()){
                childrenNode.add(generateObjectNodeFromProfile(getProfileMap().get(child), mapper));
            }
        }
        return node;
    }

    public String outputSingleProfileJson(String profileName) throws JsonProcessingException {
        DiscoveryProfile profile = getProfileMap().get(profileName);
        ArrayList<com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryJob> joblist = new ArrayList<com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryJob>();
        for(String jobName : profile.getJobs()){
            DiscoveryJob job = getJobMap().get(jobName);
            com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryJob jsonJob = new com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryJob(job.getJobName(), job.getAdapterName(), job.getModuleName(), job.getInputCi(), null, null, null, null, null);
            joblist.add(jsonJob);
        }
        com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryProfile jsonProfile = new com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.json.DiscoveryProfile("todo", profileName,"oob",joblist);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(jsonProfile);
    }

    public List<Set<String>>  getLayeredProfiles() {
        List<Set<String>> rlt = new ArrayList<Set<String>>(4);

        // layer1
        HashSet<String> layer1 = new HashSet<String>();
        for(DiscoveryProfile profile : getLeafProfileList()){
            if(profile.getDependOnProfiles() == null || profile.getDependOnProfiles().size() == 0){
                layer1.add(profile.getName());
            }
        }
        rlt.add(0, layer1);

        // layer2
        HashSet<String> layer2 = new HashSet<String>();
        for(String layer1Profile : layer1){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer1Profile)){
                        layer2.add(profile.getName());
                        count ++;
                    }
                }
            }
        }
        rlt.add(1, layer2);

        // Layer3
        HashSet<String> layer3 = new HashSet<String>();
        for(String layer2Profile : layer2){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer2Profile)){
                        if(!layer2.contains(profile.getName())){
                            layer3.add(profile.getName());
                            count++;
                        }
                    }
                }
            }
        }
        rlt.add(2, layer3);

        // Layer4
        HashSet<String> layer4 = new HashSet<String>();
        for(String layer3Profile : layer3){
            int count = 0;
            for(DiscoveryProfile profile : getLeafProfileList()){
                if(profile.getDependOnProfiles() != null) {
                    if (profile.getDependOnProfiles().contains(layer3Profile)){
                        if(!layer2.contains(profile.getName()) && !layer3.contains(profile.getName())){
                            layer4.add(profile.getName());
                            count++;
                        }
                    }
                }
            }
        }
        rlt.add(3, layer4);

        return rlt;
    }

    public Set<String> getOutputCIT(String profileName) {
        Set<String> rlt = new HashSet<String>();
        DiscoveryProfile profile = findProfileByName(profileName);
        for(String jobName : profile.getJobs()){
            DiscoveryJob job = findJobByName(jobName);
            String[] cit = job.getOutputCis().substring(1,job.getOutputCis().length() - 1).split(",");
            for(String s : cit){
                rlt.add(s.trim());
            }
        }
        return rlt;
    }
}
