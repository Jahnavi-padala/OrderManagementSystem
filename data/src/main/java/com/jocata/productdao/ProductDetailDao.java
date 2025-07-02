package com.jocata.productdao;

import com.jocata.productservice.entity.ProductDetails;

public interface ProductDetailDao {
    ProductDetails save(ProductDetails details);
    ProductDetails findById(Integer id);
    ProductDetails findByProductId(Integer productId);
}
