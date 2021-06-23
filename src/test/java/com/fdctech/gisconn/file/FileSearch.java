package com.fdctech.gisconn.file;


import com.fdctech.gisconn.GenericTest;
import com.fdctech.gisconn.core.file.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileSearch extends GenericTest {

    @Test
    public void getListOfFilesTest() throws IOException {
        List<File> files = FileUtil.getXmlFilesInDirectory("E:\\SmevAdapter\\messages\\742G01");
        for (File file : files)
            System.out.println(file);
    }
}
