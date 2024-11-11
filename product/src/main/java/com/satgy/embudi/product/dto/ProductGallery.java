package com.satgy.embudi.product.dto;

import com.satgy.embudi.product.model.File;
import com.satgy.embudi.product.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductGallery {
    private Product product;
    private File file;
}
