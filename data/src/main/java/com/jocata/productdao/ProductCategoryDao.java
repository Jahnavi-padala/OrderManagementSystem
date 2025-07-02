package com.jocata.productdao;

import com.jocata.productservice.entity.ProductCategory;

public interface ProductCategoryDao {
    ProductCategory save(ProductCategory category);
    ProductCategory findById(Integer id);
}
