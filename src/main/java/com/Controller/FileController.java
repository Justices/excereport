package com.Controller;

import com.service.file.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


/**
 * Created by liujiaping on 2017/7/4.
 */

@Controller
public class FileController {

    @Autowired
    private ExcelFileService excelFileService;


    @RequestMapping(value="file",method = RequestMethod.GET)
    @ResponseBody
    public String file() {
        return "file name ";
    }

    @RequestMapping(value="uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("filePart") MultipartFile multipartFile) throws IOException {
        if(isFileInValid(multipartFile)){
            return "File is not valid ";
        }

        excelFileService.saveDate(multipartFile);

        return "success";
    }

    private boolean isFileInValid(MultipartFile file) {
        if(file.isEmpty()) {
            return true;
        }

        if(file.getSize()==0){
            return true;
        }

        String fileName = file.getOriginalFilename();

        if(fileName.endsWith(".xls") ||
                fileName.endsWith(".xlsx")||
                fileName.endsWith(".xps")){
            return false;
        }
        return true;

    }

}
