package org.choongang.file.controllers;

import org.choongang.commons.ExceptionRestProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class ApiFileController implements ExceptionRestProcessor {  // json방식으로 에러 응답하도록

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile[] files,
                       @RequestParam(value = "gid", required = false) String gid,
                       @RequestParam(value = "location", required = false) String location) {

    }
}
