package com.enviro.assessment.grd001.EdwinMakwala.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public interface FileParser {

    void parseCSV(File csvFile) throws IOException;
    File convertCSVDataToImage(String base64ImageData);
    URI createImageLink(File fileImage);
}
