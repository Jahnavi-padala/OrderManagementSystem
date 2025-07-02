package com.jocata.service;

import com.jocata.productservice.form.ProductDataForm;

import java.util.List;

public interface ProductService {

    ProductDataForm addProduct(ProductDataForm productDataForm);

    ProductDataForm getProductById(String id);

    List<ProductDataForm> searchProducts(String keyword);
    void reduceQuantity(Integer productId, Integer quantity);

}
