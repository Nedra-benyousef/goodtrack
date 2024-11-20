package com.nedra.ecommerce.requestclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class RequestClientController {
    @Autowired
private RequestClientService requestService;

    @PostMapping(name="/requests",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RequestClient> createRequest(
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("clientId") String clientId,
            @RequestPart("file") MultipartFile file) {

        try {
            RequestClient requestClient = RequestClient.builder()
                    .name(name)
                    .description(description)
                    .clientId(clientId)
                    .file(file.getBytes())
                    .fileType(file.getContentType())
                    .fileName(file.getOriginalFilename())
                    .status(Status.In_Reviews) // Set default status
                    .createdAt(LocalDateTime.now())
                    .build();

            RequestClient savedRequest = requestService.saveRequest(requestClient);
            return ResponseEntity.ok(savedRequest);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/requests")
    public ResponseEntity<List<RequestClient>> getAllRequests() {
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<RequestClient> getRequestById(@PathVariable String id) {
        return new ResponseEntity<>(requestService.getRequestById(id), HttpStatus.OK);
    }
}