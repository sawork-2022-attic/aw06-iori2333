package com.example.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("products")
public class Product {

    private String main_cat;

    private String title;

    @Id
    private String asin;

    private List<String> category;

    private List<String> imageURLHighRes;
}
