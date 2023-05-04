package com.enviro.assessment.grd001.EdwinMakwala.service;

import com.enviro.assessment.grd001.EdwinMakwala.entity.AccountProfile;
import com.enviro.assessment.grd001.EdwinMakwala.repository.AccountProfileRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.util.Base64;

@Service
public class FileParserImpl implements FileParser {

    /** I am aware that constructor injection is better practise :) **/
    @Autowired
    AccountProfileRepository accountProfileRepository;

    public FileParserImpl(AccountProfileRepository accountProfileRepository) {
        this.accountProfileRepository = accountProfileRepository;
    }

    @Override
    public void parseCSV(File csvFile) throws IOException {

        try (Reader reader = Files.newBufferedReader(csvFile.toPath())) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] fields;
                csvReader.skip(1);
                while ((fields = csvReader.readNext()) != null) {

                    String accName = fields[0];
                    String accSurname = fields[1];
                    String imageFormat = fields[2];
                    String data = fields[3];

                    //convert byte image data to an image file.
                    File convertedFile = convertCSVDataToImage(imageFormat + ":" + data);

                    //create some kind of URL link that will be exposed
                    URI imageLink = createImageLink(convertedFile);

                    AccountProfile accountProfile = new AccountProfile();
                    accountProfile.setAccountHolderName(accName);
                    accountProfile.setAccountHolderSurname(accSurname);
                    accountProfile.setHttpImageLink(imageLink.toString());
                    accountProfileRepository.save(accountProfile);
                }
            } catch (IOException | CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public File convertCSVDataToImage(String base64ImageData) {

        String currentDir = System.getProperty("user.dir");
        String resourcesDir = currentDir + "/src/main/resources/static/";

        //Separate the format type from the image data
        String[] data = base64ImageData.split(":");

        //Access the format type from the separation done above
        String format = data[0];

        //decode the image data
        byte[] decodeBytes = Base64.getDecoder().decode(data[1]);

        File imageFile = new File(resourcesDir + "image." + format.substring(6));
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(decodeBytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Oops! Failed to create the image", e);
        }

        return imageFile;
    }

    @Override
    public URI createImageLink(File fileImage) {
        return fileImage.toURI();
    }
}
