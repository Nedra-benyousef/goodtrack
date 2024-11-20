package com.nedra.ecommerce.requestclient;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestClientRepository extends MongoRepository<RequestClient, String> {
}
