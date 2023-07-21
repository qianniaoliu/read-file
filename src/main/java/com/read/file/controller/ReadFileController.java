package com.read.file.controller;

import com.read.file.scheduled.ReadFileDataScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@RestController
public class ReadFileController {

    @Autowired
    private ReadFileDataScheduled scheduled;

    @GetMapping("/get/locomotive/statistics")
    public List<String> readData() {
        return scheduled.getData();
    }
}
