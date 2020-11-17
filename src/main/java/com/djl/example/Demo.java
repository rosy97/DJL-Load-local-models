package com.djl.example;

import java.awt.image.*;
import java.io.IOException;

import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.util.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import ai.djl.training.util.*;
public class Demo {

    public static void main(String[] args) throws IOException, ModelException, TranslateException {
        //1
        DownloadUtils.download(//traced_resnet18.pt.gz
                "https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/resnet/0.0.1/traced_resnet18.pt.gz",
                "build/pytorch_models/resnet18/resnet18.pt", new ProgressBar());

        DownloadUtils.download(
                "https://djl-ai.s3.amazonaws.com/mlrepo/model/cv/image_classification/ai/djl/pytorch/synset.txt",
                "build/pytorch_models/resnet18/synset.txt", new ProgressBar());

        //2.
        Pipeline pipeline = new Pipeline();
        pipeline.add(new Resize(256))
                .add(new CenterCrop(224, 224))
                .add(new ToTensor())
                .add(new Normalize(
                        new float[] {0.485f, 0.456f, 0.406f},
                        new float[] {0.229f, 0.224f, 0.225f}));

        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                .setPipeline(pipeline)
                .optApplySoftmax(true)
                .build();

        //3
        // Search for models in the build/pytorch_models folder
        System.setProperty("ai.djl.repository.zoo.location", "build/pytorch_models/resnet18");

        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                // only search the model in local directory
                // "ai.djl.localmodelzoo:{name of the model}"
                .optArtifactId("ai.djl.localmodelzoo:resnet18")
                .optTranslator(translator)
                .optProgress(new ProgressBar()).build();

        ZooModel model = ModelZoo.loadModel(criteria);

        System.out.println("sssssssssssssssssss");
        System.out.println("Application: "+criteria.getApplication() //UNDEFINED
                +"\n"+"Engine: "+criteria.getEngine() //null
                +"\nGroupId: "+criteria.getGroupId() //ai.djl.localmodelzoo
                +"\nModelName: "+criteria.getModelName() //null
                +"\nModelZoo: "+criteria.getModelZoo() //null
                +"\n"+criteria.getInputClass() //interface ai.djl.modality.cv.Image
                +"\n"+criteria.getOutputClass() //class ai.djl.modality.Classifications
                +"\nArtifactId: "+criteria.getArtifactId() //resnet18

                +"\nFilters: "+criteria.getFilters()); //null

    }


}
