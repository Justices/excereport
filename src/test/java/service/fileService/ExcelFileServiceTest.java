package service.fileService;

import com.BaseTestCase;
import com.document.parser.ExcelParser;
import com.service.file.ExcelFileService;
import com.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by liujiaping on 2017/7/2.
 */
public class ExcelFileServiceTest extends BaseTestCase {

    private ExcelParser parser;
    @Autowired
    private ExcelFileService excelFileService;

    @Before
    public void init() {
        parser = ExcelParser.getInstance();
    }

    @Test
    public void testSaveDate() throws IOException {
        FileInputStream stream = null;
        try {
            String path = "/Users/liujiaping/Downloads/零售6.05.xls";
            stream = new FileInputStream(path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List itemList = parser.fileParser(stream);
        String collectionName = "flyer_".concat(Util.getTodayString());
        excelFileService.saveDate(collectionName,itemList);
        System.out.println("save successfully");
    }
}
