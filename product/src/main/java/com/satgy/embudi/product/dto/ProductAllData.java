package com.satgy.embudi.product.dto;

import com.satgy.embudi.product.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductAllData implements Serializable {

    private Product product;
    private List<ProductResource> productResourceList;

}
