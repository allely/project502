package org.choongang.file;

import org.choongang.file.service.FileInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ThumbnailTest {
    @Autowired
    private FileInfoService infoService;

    @Test
    void getThumbTest() {
        String[] data = infoService.getThumb(602L, 350, 350);
        System.out.println(Arrays.toString(data));
    }
}
