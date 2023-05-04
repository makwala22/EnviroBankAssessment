package com.enviro.assessment.grd001.EdwinMakwala;

import com.enviro.assessment.grd001.EdwinMakwala.service.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class EnviroBankAssessmentApplication implements CommandLineRunner {

    /** I am aware that constructor injection is better practise :) **/
    @Autowired
    FileParser fileParser;

    public static void main(String[] args) {
        SpringApplication.run(EnviroBankAssessmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String currentDir = System.getProperty("user.dir");
        String resourcesDir = currentDir + "/src/main/resources/";
        String csvFile = resourcesDir + "1672815113084-GraduateDev_AssessmentCsv_Ref003.csv";

        File file = new File(csvFile);
        fileParser.parseCSV(file);
    }
}
