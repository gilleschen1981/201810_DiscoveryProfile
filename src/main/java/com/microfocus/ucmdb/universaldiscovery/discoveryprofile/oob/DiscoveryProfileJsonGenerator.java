package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryJob;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryProfile;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryProfileRepository;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryProfileJsonGenerator {
    static final String HELP = "Usage: java -jar [jarfile name] [data folder path]\n";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(HELP);
            System.exit(1);
        }

        // load the excel
        ExcelReader jobExcelReader = new ExcelReader(args[0]);
        List<DiscoveryJob> jobList = new ArrayList<DiscoveryJob>(500);
        List<DiscoveryProfile> profileList = new ArrayList<DiscoveryProfile>(200);
        try {
            jobExcelReader.loadExcel(jobList,profileList);
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

        String dependency = repo.outputDependency();
        String json = repo.outputProfileJson();


        // write to file
        try {
            File profiles = new File("output", "profileRoadmap.json");
            FileWriter writer = new FileWriter(profiles);
            writer.write(json);
            writer.close();

            for(DiscoveryProfile profile: repo.getLeafProfileList()){
                String singleProfileJson = null;
                singleProfileJson = repo.outputSingleProfileJson(profile.getName());
                File folder = new File("output","oob");
                File profileFile = new File(folder,profile.getName() + ".json");
                writer = new FileWriter(profileFile);
                writer.write(singleProfileJson);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
