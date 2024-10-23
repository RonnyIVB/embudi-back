package com.satgy.embudi.product.model;

    public enum ProductStatus {
        Available("Available"),
        Inactive("Inactive"),
        Sold("Sold"),
        Leased("Leased");

    public final String label;

    private ProductStatus(String label){
        this.label = label;
    }

    public static ProductStatus get(String label){
        for (ProductStatus o: ProductStatus.values()){
            if (o.label.equals(label)) return o;
        }
        return null;
    }
}
