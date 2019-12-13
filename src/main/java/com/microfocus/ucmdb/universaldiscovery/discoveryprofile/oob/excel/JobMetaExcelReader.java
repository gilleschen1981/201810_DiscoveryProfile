package com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.excel;

import com.microfocus.ucmdb.universaldiscovery.discoveryprofile.oob.model.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JobMetaExcelReader {
    private String jobMetaPath;
    static final public String jobMataName= "DiscoveryMeta.xls";

    static final public String EXCEL_SHEETNAME_DISCOVERYJOB = "DiscoveryJobs";
    static final public String EXCEL_SHEETNAME_USECASE = "UseCase";
    static final public String EXCEL_SHEETNAME_TAG = "Tag";
    static final public String EXCEL_SHEETNAME_JOBQUESTION = "JobQuestion";
    static final public String EXCEL_SHEETNAME_PARAMQUESTION = "JobParamQuestion";

    static final public String EXCEL_COLUMN_DISCOVERYJOB_JOBNAME = "Job Name";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_ADAPTERNAME = "Adapter Name";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_TAG = "Tag";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_DEPENDON = "Depend on";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_MODULENAME = "Module Name";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_INPUTCI = "Input CI";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_ISFLAT = "IsFlat";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_JOBTYPE = "JobType";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_PROTOCOL = "Protocol";
    static final public String EXCEL_COLUMN_DISCOVERYJOB_OUTPUTCI = "Output CI";

    static final public String EXCEL_COLUMN_USECASE_PARENT = "Parent";
    static final public String EXCEL_COLUMN_USECASE_NAME = "Use case Name";
    static final public String EXCEL_COLUMN_USECASE_DISPLAY = "Display";
    static final public String EXCEL_COLUMN_USECASE_CHECKBYDEFAULT = "Checked by default";

    static final public String EXCEL_COLUMN_TAG_NAME = "Tag name";
    static final public String EXCEL_COLUMN_TAG_USECASE = "Use case Name";
    static final public String EXCEL_COLUMN_TAG_CHECKED = "Checked by default";

    static final public String EXCEL_COLUMN_JOBQUESTION_TAGNAME = "Tag name";
    static final public String EXCEL_COLUMN_JOBQUESTION_QUESTION = "Question";
    static final public String EXCEL_COLUMN_JOBQUESTION_QUESTIONID = "Question Id";
    static final public String EXCEL_COLUMN_JOBQUESTION_HELPMESSAGE = "Help message";
    static final public String EXCEL_COLUMN_JOBQUESTION_QUESTIONTYPE = "Question Type";
    static final public String EXCEL_COLUMN_JOBQUESTION_CHOICE = "Choice";
    static final public String EXCEL_COLUMN_JOBQUESTION_CHOICEID = "Choice Id";
    static final public String EXCEL_COLUMN_JOBQUESTION_BYDEFAULT = "By default";
    static final public String EXCEL_COLUMN_JOBQUESTION_BEHAVIOR = "Behavior";
    static final public String EXCEL_COLUMN_JOBQUESTION_JOBNAME = "JobName";

    static final public String EXCEL_COLUMN_PARAMQUESTION_JOBNAME = "Job Name";
    static final public String EXCEL_COLUMN_PARAMQUESTION_QUESTION = "Question";
    static final public String EXCEL_COLUMN_PARAMQUESTION_QUESTIONID = "Question Id";
    static final public String EXCEL_COLUMN_PARAMQUESTION_HELPMESSAGE = "Help message";
    static final public String EXCEL_COLUMN_PARAMQUESTION_QUESTIONTYPE = "Question Type";
    static final public String EXCEL_COLUMN_PARAMQUESTION_CHOICE = "Choice";
    static final public String EXCEL_COLUMN_PARAMQUESTION_CHOICEID = "Choice Id";
    static final public String EXCEL_COLUMN_PARAMQUESTION_BYDEFAULT = "By default";
    static final public String EXCEL_COLUMN_PARAMQUESTION_PARAM = "Param";
    static final public String EXCEL_COLUMN_PARAMQUESTION_PARAMVALUE = "ParamValue";


    static final public String EXCEL_VALUE_SEPARATOR = ",";
    static final public String EXCEL_VALUE_DEPENDON_SEPARATOR = "\\|\\|";
    static final public String EXCEL_NULL_VALUE = "-";


    final private ArrayList<String> DISCOVERYJOB_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_DISCOVERYJOB_JOBNAME, EXCEL_COLUMN_DISCOVERYJOB_ADAPTERNAME,
                    EXCEL_COLUMN_DISCOVERYJOB_TAG, EXCEL_COLUMN_DISCOVERYJOB_DEPENDON, EXCEL_COLUMN_DISCOVERYJOB_MODULENAME,
                    EXCEL_COLUMN_DISCOVERYJOB_INPUTCI, EXCEL_COLUMN_DISCOVERYJOB_ISFLAT, EXCEL_COLUMN_DISCOVERYJOB_JOBTYPE,
                    EXCEL_COLUMN_DISCOVERYJOB_PROTOCOL, EXCEL_COLUMN_DISCOVERYJOB_OUTPUTCI));

    final private ArrayList<String> USECASE_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_USECASE_NAME, EXCEL_COLUMN_USECASE_PARENT,
                    EXCEL_COLUMN_USECASE_DISPLAY, EXCEL_COLUMN_USECASE_CHECKBYDEFAULT));

    final private ArrayList<String> TAG_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_TAG_NAME, EXCEL_COLUMN_TAG_USECASE, EXCEL_COLUMN_TAG_CHECKED));

    final private ArrayList<String> JOBQUESTION_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_JOBQUESTION_TAGNAME, EXCEL_COLUMN_JOBQUESTION_QUESTION,
                    EXCEL_COLUMN_JOBQUESTION_QUESTIONID, EXCEL_COLUMN_JOBQUESTION_HELPMESSAGE,
                    EXCEL_COLUMN_JOBQUESTION_QUESTIONTYPE, EXCEL_COLUMN_JOBQUESTION_CHOICE,
                    EXCEL_COLUMN_JOBQUESTION_CHOICEID, EXCEL_COLUMN_JOBQUESTION_BYDEFAULT,
                    EXCEL_COLUMN_JOBQUESTION_BEHAVIOR, EXCEL_COLUMN_JOBQUESTION_JOBNAME));

    final private ArrayList<String> JOBPARAMQUESTION_COLUMNS = new ArrayList<String>
            (Arrays.asList(EXCEL_COLUMN_PARAMQUESTION_JOBNAME, EXCEL_COLUMN_PARAMQUESTION_QUESTION,
                    EXCEL_COLUMN_PARAMQUESTION_QUESTIONID, EXCEL_COLUMN_PARAMQUESTION_HELPMESSAGE,
                    EXCEL_COLUMN_PARAMQUESTION_QUESTIONTYPE, EXCEL_COLUMN_PARAMQUESTION_CHOICE,
                    EXCEL_COLUMN_PARAMQUESTION_CHOICEID, EXCEL_COLUMN_PARAMQUESTION_BYDEFAULT,
                    EXCEL_COLUMN_PARAMQUESTION_PARAM, EXCEL_COLUMN_PARAMQUESTION_PARAMVALUE));


    public JobMetaExcelReader(String jobMetaPath) {
        this.jobMetaPath = jobMetaPath;
    }

    public void loadExcel() throws IOException, InvalidFormatException {
        DiscoveryMetaRepository repo = DiscoveryMetaRepository.getInstance();

        File jobMetaFile = new File(jobMetaPath,jobMataName);
        if(!jobMetaFile.exists()){
            System.out.println("Excel file missing: " + jobMetaPath + "\\" + jobMataName);
            System.exit(0);
        }

        Workbook wb = WorkbookFactory.create(jobMetaFile);
        DataFormatter formatter = new DataFormatter();
        for(Sheet sheet : wb) {
            if (sheet.getSheetName().equals(EXCEL_SHEETNAME_DISCOVERYJOB)) {
                Map<String, Integer> jobColumn = new HashMap<String, Integer>();
                processJobSheetTitle(sheet, jobColumn, DISCOVERYJOB_COLUMNS);
                ArrayList<String> valueList = new ArrayList<String>(10);
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    for(String colName: DISCOVERYJOB_COLUMNS){
                        String value = "";
                        Cell c = sheet.getRow(row).getCell(jobColumn.get(colName), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if(c != null){
                            value = c.getStringCellValue();
                        }
                        if("-".equals(value)){
                            value = "";
                        }
                        valueList.add(value.trim());
                    }
                    DiscoveryJobMeta jobMeta = new DiscoveryJobMeta(valueList);
                    valueList = new ArrayList<String>(10);
                    repo.addJobMeta(jobMeta);
                }
            }else if (sheet.getSheetName().equals(EXCEL_SHEETNAME_USECASE)) {
                Map<String, Integer> column = new HashMap<String, Integer>();
                processJobSheetTitle(sheet, column, USECASE_COLUMNS);
                ArrayList<String> valueList = new ArrayList<String>(3);
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    for(String colName: USECASE_COLUMNS){
                        String value = "";
                        Cell c = sheet.getRow(row).getCell(column.get(colName), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if(c != null){
                            value = formatter.formatCellValue(c);
                        }
                        valueList.add(value.trim());
                    }
                    DiscoveryUsecase usecase = new DiscoveryUsecase(valueList);
                    valueList = new ArrayList<String>(3);
                    repo.addUseCase(usecase);

                }
            }else if (sheet.getSheetName().equals(EXCEL_SHEETNAME_TAG)) {
                Map<String, Integer> column = new HashMap<String, Integer>();
                processJobSheetTitle(sheet, column, TAG_COLUMNS);
                ArrayList<String> valueList = new ArrayList<String>(3);
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    for(String colName: TAG_COLUMNS){
                        String value = "";
                        Cell c = sheet.getRow(row).getCell(column.get(colName), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if(c != null){
                            value = formatter.formatCellValue(c);
                        }
                        valueList.add(value.trim());
                    }
                    DiscoveryTag tag = new DiscoveryTag(valueList);
                    valueList = new ArrayList<String>(3);
                    repo.addTag(tag);

                }
            }else if (sheet.getSheetName().equals(EXCEL_SHEETNAME_JOBQUESTION)) {
                Map<String, Integer> column = new HashMap<String, Integer>();
                processJobSheetTitle(sheet, column, JOBQUESTION_COLUMNS);
                ArrayList<String> valueList = null;
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    valueList = new ArrayList<String>(10);
                    for(String colName: JOBQUESTION_COLUMNS){
                        String value = "";
                        Cell c = sheet.getRow(row).getCell(column.get(colName), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if(c != null){
                            value = formatter.formatCellValue(c);
                        }
                        value = value.trim();
                        if("-".equals(value)){
                            value = "";
                        }
                        valueList.add(value);
                        }
                    if(valueList.get(1).isEmpty() && valueList.get(5).isEmpty()){
                        repo.addEmptyJobQuestion(valueList.get(0));
                        continue;
                    }

                    DiscoveryJobQuestion question = repo.getJobQuestion(valueList.get(0), valueList.get(1));
                    if(question == null){
                        question = new DiscoveryJobQuestion(new ArrayList<String>(valueList.subList(0,5)));
                        repo.addJobQuestion(question);
                    }

                    DiscoveryJobQuestionChoice choice = new DiscoveryJobQuestionChoice(new ArrayList<String>(valueList.subList(5, valueList.size())));
                    question.addChoice(choice);
                }
            }else if (sheet.getSheetName().equals(EXCEL_SHEETNAME_PARAMQUESTION)) {
                Map<String, Integer> column = new HashMap<String, Integer>();
                processJobSheetTitle(sheet, column, JOBPARAMQUESTION_COLUMNS);
                ArrayList<String> valueList = null;
                for(int row = 1; row <= sheet.getLastRowNum(); row++){
                    valueList = new ArrayList<String>(10);
                    for(String colName: JOBPARAMQUESTION_COLUMNS){
                        String value = "";
                        Cell c = sheet.getRow(row).getCell(column.get(colName), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if(c != null){
                            value = formatter.formatCellValue(c);
                        }
                        value = value.trim();
                        if("-".equals(value)){
                            value = "";
                        }
                        valueList.add(value);
                    }

                    if(valueList.get(1).isEmpty() && valueList.get(5).isEmpty()){
                        repo.addEmptyParamQuestion(valueList.get(0));
                        continue;
                    }

                    DiscoveryJobParamQuestion question = repo.getParamQuestion(valueList.get(0), valueList.get(1));
                    if(question == null){
                        question = new DiscoveryJobParamQuestion(new ArrayList<String>(valueList.subList(0,5)));
                        repo.addParamQuestion(question);
                    }
                    DiscoveryJobParamQuestionChoice choice = new DiscoveryJobParamQuestionChoice(new ArrayList<String>(valueList.subList(5, valueList.size())));
                    question.addChoice(choice);
                    question.addChoice(choice);
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
