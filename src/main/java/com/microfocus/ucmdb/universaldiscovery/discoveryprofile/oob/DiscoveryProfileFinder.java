package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryJob;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryProfile;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryProfileRepository;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.*;

public class DiscoveryProfileFinder {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        }

        // load the excel
        ExcelReader jobExcelReader = new ExcelReader(args[0]);
        List<DiscoveryJob> jobList = new ArrayList<DiscoveryJob>(500);
        List<DiscoveryProfile> profileList = new ArrayList<DiscoveryProfile>(200);
        try {
            jobExcelReader.loadExcel(jobList, profileList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        System.out.println(jobList.size());
        System.out.println(profileList.size());


        DiscoveryProfileRepository repo = DiscoveryProfileRepository.getInstance();
        repo.initializeProfile(profileList);
        repo.initializeJob(jobList);

        String input = "ip_address,node,amazon_account";
        List<String> cit = new ArrayList<String>(Arrays.asList(input.split(",")));
        List<Set<String>> layeredProfiles = repo.getLayeredProfiles();
        for(Set<String> profiles : layeredProfiles){
            Iterator<String> it = profiles.iterator();
            while(it.hasNext()){
                String profileName = it.next();
                Set<String> outputCIT = repo.getOutputCIT(profileName);
                for(String s : cit){
                    if(!outputCIT.contains(s)){
                        it.remove();
                        break;
                    }
                }
            }
        }
        String rlt = "";
        for(int i = layeredProfiles.size() - 1; i >= 0 ;i--){
            Set<String> profiles = layeredProfiles.get(i);
            if(profiles.size() > 0){
                rlt += "[Layer" + (i+1) + "]: ";
                for(String s : profiles){
                    rlt += s + ", ";
                }
                rlt += "\n";
            }
        }
        System.out.println(rlt);
    }
}
