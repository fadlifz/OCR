package com.example.ocr.controller;

import com.example.ocr.service.ExtractionService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    ResponseEntity<Map<String, Object>> scanFile(@RequestHeader("Authorization") String authorization,
            @RequestParam("file") MultipartFile file,
            @RequestParam("requestId") String requestId)
            throws TesseractException, IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        if (!authorization.isEmpty()) {
            String text = extractionService.extract(file, requestId);
            jsonMap.put("extractedText", text);
            return new ResponseEntity<>(jsonMap, HttpStatus.OK);
        }
        jsonMap.put("extractedText", null);
        return new ResponseEntity<>(jsonMap, HttpStatus.BAD_REQUEST);
    }
}
