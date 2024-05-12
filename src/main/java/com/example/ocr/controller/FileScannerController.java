package com.example.ocr.controller;

import com.example.ocr.request.FileRequest;
import com.example.ocr.service.ExtractionService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileScannerController {

    @Autowired
    private ExtractionService extractionService;

    @PostMapping("/extract-text")
    ResponseEntity<Map<String, Object>> scanFile(@RequestBody FileRequest file)
            throws TesseractException, IOException {
        String text = extractionService.extract(file);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("extractedText", text);
        return new ResponseEntity<>(jsonMap, HttpStatus.OK);
    }
}
