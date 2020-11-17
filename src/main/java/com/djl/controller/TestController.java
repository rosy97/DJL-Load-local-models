package com.djl.controller;

import ai.djl.MalformedModelException;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.djl.service.ImageClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    ImageClassifyService imageClassifyService;

    @RequestMapping("/test")
    public String test() throws MalformedModelException, ModelNotFoundException, IOException {
        DetectedObjects detectedObjects = imageClassifyService.imageClassify();
        return detectedObjects.toString();
    }
}
