package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.tools.ant.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobMetaExcelUpdater {
    private String jobMetaPath;

    public JobMetaExcelUpdater(String jobMetaPath) {
        this.jobMetaPath = jobMetaPath;
    }

    public void updateQuestionId(DiscoveryMetaRepository repo) throws IOException, InvalidFormatException {
        File jobMetaFile = new File(jobMetaPath, JobMetaExcelReader.jobMataName);
        if (!jobMetaFile.exists()) {
            System.out.println("Excel file missing: " + jobMetaPath + "\\" + JobMetaExcelReader.jobMataName);
            System.exit(0);
        }

        Workbook wb = WorkbookFactory.create(jobMetaFile);
        DataFormatter formatter = new DataFormatter();
        for (Sheet sheet : wb) {
            if (sheet.getSheetName().equals(JobMetaExcelReader.EXCEL_SHEETNAME_JOBQUESTION)) {
                ArrayList<String> valueList = null;
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    Cell c = sheet.getRow(row).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String tagName = "";
                    if(c != null){
                        tagName = formatter.formatCellValue(c);
                    }
                    c = sheet.getRow(row).getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String question = "";
                    if(c != null){
                        question = formatter.formatCellValue(c);
                    }
                    DiscoveryJobQuestion questionInstance = repo.getJobQuestion(tagName, question);
                    if(questionInstance == null){
                        if(repo.getJobQuestionMap().get(tagName) == null){
                            System.out.println("[ERROR]Question not found. Tag: " + tagName + ".  Question: " + question);
                        }
                        continue;
                    }
                    Cell updateCell = sheet.getRow(row).createCell(2);
                    updateCell.setCellValue(questionInstance.getQuestionid());

                    c = sheet.getRow(row).getCell(5, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String choice = "";
                    if(c != null){
                        choice = formatter.formatCellValue(c);
                    }
                    for(DiscoveryJobQuestionChoice tempC : questionInstance.getChoiceList()){
                        if(tempC.getChoice().equals(choice)){
                            updateCell = sheet.getRow(row).createCell(6);
                            updateCell.setCellValue(tempC.getChoiceId());
                        }
                    }
                }
            } else if (sheet.getSheetName().equals(JobMetaExcelReader.EXCEL_SHEETNAME_PARAMQUESTION)) {
                ArrayList<String> valueList = null;
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    Cell c = sheet.getRow(row).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String jobName = "";
                    if(c != null){
                        jobName = formatter.formatCellValue(c);
                    }
                    c = sheet.getRow(row).getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String question = "";
                    if(c != null){
                        question = formatter.formatCellValue(c);
                    }
                    DiscoveryJobParamQuestion questionInstance = repo.getParamQuestion(jobName, question);
                    if(questionInstance == null){
                        if(repo.getJobMetaMap().get(jobName) == null) {
                            System.out.println("[ERROR]Question not found. Job: " + jobName + ".  Question: " + question);
                        }
                        continue;
                    }
                    Cell updateCell = sheet.getRow(row).createCell(2);
                    updateCell.setCellValue(questionInstance.getQuestionId());

                    c = sheet.getRow(row).getCell(5, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    String choice = "";
                    if(c != null){
                        choice = formatter.formatCellValue(c);
                    }
                    for(DiscoveryJobParamQuestionChoice tempC : questionInstance.getChoiceList()){
                        if(tempC.getChoice().equals(choice)){
                            updateCell = sheet.getRow(row).createCell(6);
                            updateCell.setCellValue(tempC.getChoiceId());
                        }
                    }
                }
            }
        }
        File outputFile = new File(jobMetaPath, "output.xls");
        FileOutputStream stream = new FileOutputStream(outputFile);
        wb.write(stream);
    }
}
