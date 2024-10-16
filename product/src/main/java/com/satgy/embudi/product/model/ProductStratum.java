package com.satgy.embudi.product.model;

public enum ProductStratum {
    S1("S1"),
    S2("S2"),
    S3("S3"),
    S4("S4"),
    S5("S5");

    public final String label;

    private ProductStratum(String label){
        this.label = label;
    }

    public static ProductStratum get(String label){
        for (ProductStratum o: ProductStratum.values()){
            if (o.label.equals(label)) return o;
        }
        return null;
    }
}
