package com.example.ocr.controller;

import com.example.ocr.service.ExtractionService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileScannerController {

    @Autowired
    private ExtractionService extractionService;

    @PostMapping("/extract-text")
    ResponseEntity<Map<String, Object>> scanFile(@RequestParam("file") MultipartFile file)
            throws TesseractException, IOException {
        String text = extractionService.extract(file);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("result", text);
        return new ResponseEntity<>(jsonMap, HttpStatus.OK);
    }
}
