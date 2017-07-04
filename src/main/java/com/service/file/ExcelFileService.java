package com.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Created by liujiaping on 2017/7/2.
 */
public interface ExcelFileService {

    void saveDate(MultipartFile multipartFile) ;
}
