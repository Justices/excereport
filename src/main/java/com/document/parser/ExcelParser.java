package com.document.parser;

import com.cache.SystemCache;
import com.property.PropertiesLoader;
import com.document.to.CellInfoTo;
import com.util.Util;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

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

    private HSSFSheet getSheet(InputStream stream){
        String SHEET_KEY = this.getSheetKey();
        if(cacheMap.containsKey(SHEET_KEY)){
            return (HSSFSheet) cacheMap.get(SHEET_KEY);
        }

        POIFSFileSystem fs;
        HSSFWorkbook wb =null;
        try{
            fs = new POIFSFileSystem(stream);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wb ==null){
            return null;
        }
        HSSFSheet hssfSheet = wb.getSheetAt(0);

        cacheMap.put(SHEET_KEY,hssfSheet);
        return hssfSheet;
    }

    public List fileParser(InputStream stream) {
       try {
           HSSFSheet sheet = this.getSheet(stream);
           Integer rowNum = sheet.getLastRowNum();
           List<Integer> numList = new ArrayList<>();
           int index = 1;
           while (index <= rowNum) {
               numList.add(index);
               index++;
           }
           return numList.parallelStream().map(rowIndex -> sheet.getRow(rowIndex))
                   .map(hssfRow -> {
                       List<CellInfoTo> columnList = this.getColumnInfo(stream);
                       Map<String, String> itemValue = new HashMap<>();
                       columnList.stream().forEach(cellInfoTo -> {
                           String field = cellInfoTo.getCellField();
                           HSSFCell cell = hssfRow.getCell(cellInfoTo.getCellNo());
                           itemValue.put(field, getCellValdue(cell));
                       });
                       return itemValue;
                   }).collect(toList());
       }finally {
           if(stream!=null){
               try {
                   stream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

    }


    private List<CellInfoTo> getColumnInfo(InputStream stream){
        if(cacheMap.containsKey(COLUMN_KEY)){
            return (List<CellInfoTo>) cacheMap.get(COLUMN_KEY);
        }
        List<CellInfoTo> cellList = new ArrayList<>();
        HSSFSheet sheet = this.getSheet(stream);
        HSSFRow header = sheet.getRow(0);
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

    private String getCellValdue(HSSFCell hssfCell) {
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
                return String.valueOf(hssfCell.getNumericCellValue());
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

    public static void main(String[] args) {
        FileInputStream stream = null;
        try {
             String path = "/Users/liujiaping/Downloads/零售6.05.xls";
             stream = new FileInputStream(path);
            List itemList = getInstance().fileParser(stream);
            itemList.parallelStream().forEach(
                    item-> System.out.println(item)
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  finally {
            try {
                if(stream !=null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
