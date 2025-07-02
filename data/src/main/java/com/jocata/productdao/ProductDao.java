package com.jocata.productdao;

import com.jocata.productservice.entity.Product;

import java.util.List;

public interface ProductDao {
    Product save(Product product);
    Product findById(Integer id);
    List<Product> search(String keyword);
    Product update(Product product);
}
