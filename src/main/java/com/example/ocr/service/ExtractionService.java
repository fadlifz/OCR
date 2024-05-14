package com.example.ocr.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class ExtractionService {

    public String extract(MultipartFile file, String requestId)
            throws IOException, TesseractException {

        if (!requestId.isEmpty()) {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/");
            tesseract.setLanguage("eng");
            String convertImage = tesseract.doOCR(convert(file));
            String output = convertImage.replaceAll("\\\n", " ");
            output = output.replaceAll(" ", " ");
            return output;
        }
        return "";
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
