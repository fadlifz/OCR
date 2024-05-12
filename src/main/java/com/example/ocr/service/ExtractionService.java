package com.example.ocr.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.stereotype.Service;

import com.example.ocr.request.FileRequest;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.xml.bind.DatatypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExtractionService {

    public static final DateTimeFormatter yyMMddHHmmssSSFormat = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");

    public String extract(FileRequest file) throws IOException, TesseractException {

        String[] strings = file.getFile().split(",");
        String extension;
        switch (strings[0]) {// check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default:// should write cases for more images types
                extension = "jpg";
                break;
        }
        // convert base64 string to binary data
        BufferedImage image = null;
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);

        image = ImageIO.read(bis);
        bis.close();

        // write the image to a file
        File outputFile = new File("image_" + yyMMddHHmmssSSFormat.format(LocalDateTime.now()) + "." + extension);
        ImageIO.write(image, extension, outputFile);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/");
        tesseract.setLanguage("eng");
        String convertImage = tesseract.doOCR(outputFile);
        String output = convertImage.replaceAll("\\\n", " ");
        output = output.replaceAll(" ", " ");
        return output;
    }
}
