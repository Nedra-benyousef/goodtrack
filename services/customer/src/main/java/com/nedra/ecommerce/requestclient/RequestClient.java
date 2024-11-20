package com.nedra.ecommerce.requestclient;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class RequestClient {
    @Id
    private String id;
    private String name;
    private String description;


    private Status status;
    private String clientId;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Field("file_data")
    private byte[] file; // Store file as byte array

    private String fileType; // Type of file (e.g., PDF, DOC)
    private String fileName; // Original name of the file



}
