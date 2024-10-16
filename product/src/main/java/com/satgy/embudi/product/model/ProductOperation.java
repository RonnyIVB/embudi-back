package com.satgy.embudi.product.model;

public enum ProductOperation {
    Buy("Buy"),
    Sale("Sale");

    public final String label;

    private ProductOperation(String label){
        this.label = label;
    }

    public static ProductOperation get(String label){
        for (ProductOperation o: ProductOperation.values()){
            if (o.label.equals(label)) return o;
        }
        return null;
    }
}
