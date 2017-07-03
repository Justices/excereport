package com.document.to;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by liujiaping on 2017/7/1.
 */
@Data
public class CellInfoTo {

    private String cellName;

    private String cellValue;

    private Integer cellType;

    private String cellField;

    private Integer cellNo;

    public  void build(Cell cell) {
        if(cell.getRowIndex()==0) {
            cellName = cell.getStringCellValue();
        }else{
            cellName = cellField;
        }
        cellType = cell.getCellType();
        cellNo = cell.getColumnIndex();
        cellValue = cell.getStringCellValue();
    }
}
