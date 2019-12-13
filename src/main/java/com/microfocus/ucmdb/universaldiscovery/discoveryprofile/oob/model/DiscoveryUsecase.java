package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model;

import java.util.ArrayList;

public class DiscoveryUsecase {
    private String usecaseName;
    private String parentUsecaseName;
    private ArrayList<String> childrenUsecases;
    private ArrayList<String> childrenTags;
    private Boolean isLeaf = false;
    private Boolean isCheckbox;

    public DiscoveryUsecase(ArrayList<String> valueList) {
        usecaseName = valueList.get(0);
        parentUsecaseName = valueList.get(1);
        isCheckbox = Boolean.valueOf(valueList.get(2));
    }

    public String getUsecaseName() {
        return usecaseName;
    }

    public void setUsecaseName(String usecaseName) {
        this.usecaseName = usecaseName;
    }

    public String getParentUsecaseName() {
        return parentUsecaseName;
    }

    public void setParentUsecaseName(String parentUsecaseName) {
        this.parentUsecaseName = parentUsecaseName;
    }

    public ArrayList<String> getChildrenUsecases() {
        if(childrenUsecases == null){
            childrenUsecases = new ArrayList<String>();
        }
        return childrenUsecases;
    }

    public void setChildrenUsecases(ArrayList<String> childrenUsecases) {
        this.childrenUsecases = childrenUsecases;
    }

    public ArrayList<String> getChildrenTags() {
        if(childrenTags == null){
            childrenTags = new ArrayList<String>();
        }
        return childrenTags;
    }

    public void setChildrenTags(ArrayList<String> childrenTags) {
        this.childrenTags = childrenTags;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Boolean getCheckbox() {
        return isCheckbox;
    }

    public void setCheckbox(Boolean checkbox) {
        isCheckbox = checkbox;
    }

    public void addTag(String tag) {
        setLeaf(true);
        getChildrenTags().add(tag);
    }

    public void addSubUsecase(String usecaseName) {
        if(isLeaf){
            System.out.println("[ERROR]Use case should be leaf: " + usecaseName);
        } else{
            getChildrenUsecases().add(usecaseName);
        }

    }

    @Override
    public String toString() {
        StringBuilder rlt = new StringBuilder();
        rlt.append(getUsecaseName());
        rlt.append("\n");
        if(isLeaf){
            for(String tag : getChildrenTags()){
                String tagOutput = DiscoveryMetaRepository.getInstance().getTagMap().get(tag).toString();
                rlt.append("\t");
                for(char c : tagOutput.toCharArray()){
                    if(c =='\n'){
                        rlt.append("\n\t");
                    } else{
                        rlt.append(c);
                    }
                }
                rlt.deleteCharAt(rlt.length() - 1);
            }
        }else{
            for(String subUsecase : getChildrenUsecases()){
                String subUsecaseOutput = DiscoveryMetaRepository.getInstance().getUsecaseMap().get(subUsecase).toString();
                rlt.append("\t");
                for(char c : subUsecaseOutput.toCharArray()){
                    if(c =='\n'){
                        rlt.append("\n\t");
                    } else{
                        rlt.append(c);
                    }
                }
                rlt.deleteCharAt(rlt.length() - 1);
            }
        }
        return rlt.toString();
    }
}
