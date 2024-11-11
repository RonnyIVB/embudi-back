package com.satgy.embudi.product.general;



/**
 * This clas is used to get many search parameters for a search, example: City, Price, Bedrooms, etc.
 */

public class CustomFilter {
    String value;
    String type;
    String min;
    String max;

    public String getValue() { return value; }

    public String getLowerValue() { return value.toLowerCase().trim(); }

    public void setValue(String value) { this.value = value; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getMin() { return min; }

    public void setMin(String min) { this.min = min; }

    public String getMax() { return max; }

    public void setMax(String max) {
        this.max = max;
    }
}

