package com.document.parser;

import com.cache.SystemCache;
import com.property.PropertiesLoader;
import com.document.to.CellInfoTo;
import com.util.Util;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;


/**
 * Created by liujiaping on 2017/6/30.
 */
public class ExcelParser {

    private static ConcurrentHashMap cacheMap;

    private static final String COLUMN_KEY = "sheet_column";

    private  ExcelParser() {
        cacheMap = new ConcurrentHashMap();
    }


    public static synchronized ExcelParser getInstance() {
        return ExcelParserInner.INSTANCE;
    }

    private static class ExcelParserInner {
        public static final ExcelParser INSTANCE = new ExcelParser();
    }

    private Sheet getSheet(MultipartFile multipartFile){
        String SHEET_KEY = this.getSheetKey();
        if(cacheMap.containsKey(SHEET_KEY)){
            return (HSSFSheet) cacheMap.get(SHEET_KEY);
        }


        POIFSFileSystem fs;
        Workbook wb =null;
        try(
            InputStream stream = multipartFile.getInputStream()
        ) {
            fs = new POIFSFileSystem(stream);
            wb = WorkbookFactory.create(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wb ==null){
            return null;
        }
        Sheet hssfSheet = wb.getSheetAt(0);

        cacheMap.put(SHEET_KEY,hssfSheet);
        return hssfSheet;
    }

    public List fileParser(MultipartFile multipartFile) {
       Sheet sheet = this.getSheet(multipartFile);
       Integer rowNum = sheet.getLastRowNum();
       List<Integer> numList = new ArrayList<>();
       int index = 1;
       while (index <= rowNum) {
           numList.add(index);
           index++;
       }
       return numList.parallelStream().map(rowIndex -> sheet.getRow(rowIndex))
               .map(hssfRow -> {
                   List<CellInfoTo> columnList = this.getColumnInfo(multipartFile);
                   Map<String, Object> itemValue = new HashMap<>();
                   columnList.stream().forEach(cellInfoTo -> {
                       String field = cellInfoTo.getCellField();
                       Cell cell = hssfRow.getCell(cellInfoTo.getCellNo());
                       itemValue.put(field, getCellValdue(cell));
                   });
                   return itemValue;
               }).collect(toList());
    }


    private List<CellInfoTo> getColumnInfo(MultipartFile multipartFile){
        if(cacheMap.containsKey(COLUMN_KEY)){
            return (List<CellInfoTo>) cacheMap.get(COLUMN_KEY);
        }
        List<CellInfoTo> cellList = new ArrayList<>();
        Sheet sheet = this.getSheet(multipartFile);
        Row header = sheet.getRow(0);
        Iterator<Cell> iterator = header.cellIterator();
        while(iterator.hasNext()) {
            Cell  cell= iterator.next();
            CellInfoTo cellInfoTo = new CellInfoTo();
            cellInfoTo.build(cell);
            cellInfoTo.setCellField(this.getCellField(cell.getStringCellValue()));
            cellList.add(cellInfoTo.getCellNo(),cellInfoTo);
        }
        cacheMap.put(COLUMN_KEY,cellList);
        return cellList;
    }

    private Object getCellValdue(Cell hssfCell) {
        String cellValue = "";
        CellType cellType = hssfCell.getCellTypeEnum();
        switch (cellType){
            case STRING:
                return hssfCell.getStringCellValue();
            case NUMERIC:
//                Double  value = hssfCell.getNumericCellValue();
//                boolean isDate = DateUtil.isValidExcelDate(value);
//                if (isDate) {
//                    return new SimpleDateFormat("YYYY-MM-DD").format(value);
//                }
                return hssfCell.getNumericCellValue();
            case BOOLEAN:
                return String.valueOf(hssfCell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private  String getSheetKey(){
        return Util.getSheetKey();
    }

    private String getCellField(String cellName){
        SystemCache cache = SystemCache.getInstance();
        if(cache.containsKey(cellName)) {
            return String.valueOf(cache.get(cellName));
        }

        PropertiesLoader loader = new PropertiesLoader();
        return loader.getProperty(cellName);
    }
}
