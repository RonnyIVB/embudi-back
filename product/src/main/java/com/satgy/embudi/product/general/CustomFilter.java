package com.satgy.embudi.product.general;


import lombok.Data;
import lombok.Getter;

/**
 * This clas is used to get many search parameters for a search, example: City, Price, Bedrooms, etc.
 */
@Data
public class CustomFilter {
    String id;
    String type;
}
