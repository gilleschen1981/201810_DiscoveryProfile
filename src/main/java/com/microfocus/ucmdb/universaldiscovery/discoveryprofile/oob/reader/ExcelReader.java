package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.reader;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryJob;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.DiscoveryProfile;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class ExcelReader {
    private String jobMetaPath;
    final private String jobMataName= "BundleDesign_Final.xlsx";

    final private String EXCEL_SHEETNAME_DISCOVERYJOB = "DiscoveryJobs";
    final private String EXCEL_SHEETNAME_DISCOVERYPROFILE = "DiscoveryProfile";

    final private String EXCEL_COLUMN_DISCOVERYJOB_IPLESS = "Ipless";
    final private String EXCEL_COLUMN_DISCOVERYJOB_JOBNAME = "Job";
    final private String EXCEL_COLUMN_DISCOVERYJOB_PROFILE = "DiscoveryProfile";
    final private String EXCEL_COLUMN_DISCOVERYJOB_MODULE = "Module";
    final private String EXCEL_COLUMN_DISCOVERYJOB_ADAPTER = "Adapter";
    final private String EXCEL_COLUMN_DISCOVERYJOB_DEPENDENCY = "Dependency";
    final private String EXCEL_COLUMN_DISCOVERYJOB_INPUT = "Input CI";
    final private String EXCEL_COLUMN_DISCOVERYJOB_PROTOCOLE = "Protocole";
    final private String EXCEL_COLUMN_DISCOVERYJOB_OUTPUT = "Output CI";

    final private String EXCEL_COLUMN_DISCOVERYPROFILE_NAME = "ProfileName";
    final private String EXCEL_COLUMN_DISCOVERYPROFILE_DISPLAY = "Displayname";
    final private String EXCEL_COLUMN_DISCOVERYPROFILE_DESCRIPTION = "Description";
    final private String EXCEL_COLUMN_DISCOVERYPROFILE_PARENT = "ParentProfile";


    final private ArrayList<String> DISCOVERYJOB_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_DISCOVERYJOB_IPLESS, EXCEL_COLUMN_DISCOVERYJOB_JOBNAME, EXCEL_COLUMN_DISCOVERYJOB_PROFILE, EXCEL_COLUMN_DISCOVERYJOB_MODULE
            , EXCEL_COLUMN_DISCOVERYJOB_ADAPTER, EXCEL_COLUMN_DISCOVERYJOB_DEPENDENCY, EXCEL_COLUMN_DISCOVERYJOB_INPUT, EXCEL_COLUMN_DISCOVERYJOB_PROTOCOLE, EXCEL_COLUMN_DISCOVERYJOB_OUTPUT));

    final private ArrayList<String> DISCOVERYPROFILE_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_DISCOVERYPROFILE_NAME, EXCEL_COLUMN_DISCOVERYPROFILE_DISPLAY, EXCEL_COLUMN_DISCOVERYPROFILE_DESCRIPTION, EXCEL_COLUMN_DISCOVERYPROFILE_PARENT));


    private Map<String, Integer> jobColumn = new HashMap<String, Integer>();
    private Map<String, Integer> profileColumn = new HashMap<String, Integer>();

    public ExcelReader(String jobMetaPath) {
        this.jobMetaPath = jobMetaPath;
    }

    public void loadExcel(List<DiscoveryJob> jobList, List<DiscoveryProfile> profileList) throws IOException, InvalidFormatException {
        if(jobList == null){
            return;
        }
        if(profileList == null){
            return;
        }

        File jobMetaFile = new File(jobMetaPath,jobMataName);
        if(!jobMetaFile.exists()){
            System.out.println("Excel file missing: " + jobMetaPath + "\\" + jobMataName);
            System.exit(0);
        }

        Workbook wb = WorkbookFactory.create(jobMetaFile);
        for(Sheet sheet : wb) {
            if (sheet.getSheetName().equals(EXCEL_SHEETNAME_DISCOVERYJOB)) {
                processJobSheetTitle(sheet, jobColumn, DISCOVERYJOB_COLUMNS);
                for(int row = 1; row <= sheet.getLastRowNum(); row++){

                    // ipless
                    String ipless = null;
                    Cell c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_IPLESS), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        ipless = c.getStringCellValue();
                    }
                    // jobname
                    String jobName = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_JOBNAME), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        jobName = c.getStringCellValue();
                    }
                    // DiscoveryProfile
                    String discoveryProfile = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_PROFILE), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        discoveryProfile = c.getStringCellValue();
                    }
                    // module
                    String module = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_MODULE), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        module = c.getStringCellValue();
                    }
                    // adapter
                    String adapter = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_ADAPTER), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        adapter = c.getStringCellValue();
                    }
                    // ipless
                    String dependency = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_DEPENDENCY), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        dependency = c.getStringCellValue();
                    }
                    // input
                    String input = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_INPUT), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        input = c.getStringCellValue();
                    }
                    // protocole
                    String protocole = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_PROTOCOLE), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        protocole = c.getStringCellValue();
                    }
                    // output
                    String output = null;
                    c = sheet.getRow(row).getCell(jobColumn.get(EXCEL_COLUMN_DISCOVERYJOB_OUTPUT), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        output = c.getStringCellValue();
                    }
                    DiscoveryJob jobMeta = new DiscoveryJob(jobName, discoveryProfile, adapter, module, dependency, input, protocole, output);
                    jobList.add(jobMeta);
                }
            }

            if (sheet.getSheetName().equals(EXCEL_SHEETNAME_DISCOVERYPROFILE)) {
                processJobSheetTitle(sheet, profileColumn, DISCOVERYPROFILE_COLUMNS);
                for(int row = 1; row <= sheet.getLastRowNum(); row++) {

                    // profilename
                    String profile  = null;
                    Cell c = sheet.getRow(row).getCell(profileColumn.get(EXCEL_COLUMN_DISCOVERYPROFILE_NAME), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        profile = c.getStringCellValue();
                    }
                    // display name
                    String display = null;
                    c = sheet.getRow(row).getCell(profileColumn.get(EXCEL_COLUMN_DISCOVERYPROFILE_DISPLAY), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        display = c.getStringCellValue();
                    }
                    // description
                    String description = null;
                    c = sheet.getRow(row).getCell(profileColumn.get(EXCEL_COLUMN_DISCOVERYPROFILE_DESCRIPTION), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        description = c.getStringCellValue();
                    }
                    // parent
                    String parent = null;
                    c = sheet.getRow(row).getCell(profileColumn.get(EXCEL_COLUMN_DISCOVERYPROFILE_PARENT), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (c != null) {
                        parent = c.getStringCellValue();
                    }
                    DiscoveryProfile discoveryProfileMeta = new DiscoveryProfile();
                    discoveryProfileMeta.setName(profile);
                    discoveryProfileMeta.setDisplayName(display);
                    discoveryProfileMeta.setDescription(description);
                    discoveryProfileMeta.setParentProfile(parent);
                    profileList.add(discoveryProfileMeta);

                }
            }
        }

        return;
    }


    private void processJobSheetTitle(Sheet sheet, Map<String, Integer> columnMap, ArrayList<String> columns) {
        Row firstRow = sheet.getRow(0);
        if(firstRow != null){
            int lastColumn = firstRow.getLastCellNum();

            for (int cn = 0; cn < lastColumn; cn++) {
                Cell c = firstRow.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (c != null) {
                    String title = c.getStringCellValue();
                    if(columns.contains(title)){
                        columnMap.put(title, cn);
                    } else{
                        System.out.println("[ERROR]Job sheet title wrong, value in the file: " + title);
                    }
                }
            }
        }
    }

}
