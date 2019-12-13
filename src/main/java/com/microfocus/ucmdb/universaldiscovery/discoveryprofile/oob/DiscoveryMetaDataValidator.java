package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel.JobMetaExcelReader;
import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryMetaRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class DiscoveryMetaDataValidator {
    static final String HELP = "Usage: java -jar [jarfile name] [data folder path]\n";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(HELP);
            System.exit(1);
        }
        JobMetaExcelReader excelReader = new JobMetaExcelReader(args[0]);
        try {
            excelReader.loadExcel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        DiscoveryMetaRepository repo = DiscoveryMetaRepository.getInstance();
        repo.enrich();
        repo.generateQuestionID();

        repo.validate();
    }
}
