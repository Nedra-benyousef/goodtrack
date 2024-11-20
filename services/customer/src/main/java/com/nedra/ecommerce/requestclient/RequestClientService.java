package com.nedra.ecommerce.requestclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RequestClientService {
    @Autowired
    private RequestClientRepository requestRepository;

    public RequestClient addRequest(String name, String description, String clientId, MultipartFile file) throws IOException {
        if (file != null && file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        RequestClient request = new RequestClient();
        request.setName(name);
        request.setDescription(description);
        request.setClientId(clientId);

        if (file != null) {
            // Process file (e.g., save to disk or database)
            byte[] fileContent = file.getBytes();
            request.setFile(fileContent); // Assuming RequestClient has a field for storing the file
        }

        return requestRepository.save(request); // Persist the entity
    }
    public RequestClient saveRequest(RequestClient requestClient) {
        // Add any additional business logic here if needed
        return requestRepository.save(requestClient);
    }

    public List<RequestClient> getAllRequests() {
        return requestRepository.findAll();
    }

    public RequestClient getRequestById(String id) {
        return requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
    }
}
