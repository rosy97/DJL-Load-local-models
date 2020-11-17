package com.djl.service;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.BufferedImageFactory;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageClassifyService {

    @Bean
    public DetectedObjects imageClassify() throws IOException, ModelNotFoundException, MalformedModelException {
        String url = "file:///home/java/myshells/dog_bike_car.jpg";

        BufferedImageFactory factory = new BufferedImageFactory();
        Image image = factory.fromUrl(url);

        Criteria<Image, DetectedObjects> criteria =
                Criteria.builder().optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class,DetectedObjects.class)
                        .optFilter("backbone","resnet50")
                        .optProgress(new ProgressBar())
                        .build();
        DetectedObjects detection = null;
        try (ZooModel<Image, DetectedObjects> model = ModelZoo.loadModel(criteria)){
            try(Predictor<Image, DetectedObjects> predictor = model.newPredictor()){
                detection = predictor.predict(image);
//                System.out.println(detection);
                return detection;
            } catch (TranslateException e) {
                e.printStackTrace();
            }
        }
        return detection;
    }
}
