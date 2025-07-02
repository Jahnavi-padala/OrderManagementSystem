package com.jocata.service.impl;

import com.jocata.productdao.ProductCategoryDao;
import com.jocata.productdao.ProductDao;
import com.jocata.productdao.ProductDetailDao;
import com.jocata.productservice.entity.Product;
import com.jocata.productservice.entity.ProductCategory;
import com.jocata.productservice.entity.ProductDetails;
import com.jocata.productservice.form.ProductCategoryForm;
import com.jocata.productservice.form.ProductDataForm;
import com.jocata.productservice.form.ProductDetailForm;
import com.jocata.productservice.form.ProductForm;
import com.jocata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductDao productDao;
    @Autowired
    private ProductDetailDao productDetailDao;
    @Autowired private ProductCategoryDao productCategoryDao;

    @Override
    public ProductDataForm addProduct(ProductDataForm productDataForm) {

        ProductCategoryForm cf = productDataForm.getCategory();
        ProductCategory category = new ProductCategory();
        category.setCategoryName(cf.getCategoryName());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        productCategoryDao.save(category);


        ProductForm pf = productDataForm.getProduct();
        Product product = new Product();
        product.setName(pf.getName());
        product.setDescription(pf.getDescription());
        product.setPrice(new BigDecimal(pf.getPrice()));
        product.setQuantity(Integer.valueOf(pf.getQuantity()));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCategory(category);
        productDao.save(product);


        ProductDetailForm df = productDataForm.getProductDetails();
        ProductDetails details = new ProductDetails();
        details.setProduct(product);
        details.setWeight(new BigDecimal(df.getWeight()));
        details.setDimensions(df.getDimensions());
        details.setColor(df.getColor());
        details.setCreatedAt(LocalDateTime.now());
        productDetailDao.save(details);


        return mapProductToDataForm(product, details, category);
    }

    @Override
    public ProductDataForm getProductById(String id) {
        Integer productId = Integer.valueOf(id);
        Product product = productDao.findById(productId);
        if (product == null) return null;

        ProductDetails details = productDetailDao.findByProductId(productId);

        ProductCategory category = product.getCategory();

        return mapProductToDataForm(product, details, category);
    }

    @Override
    public List<ProductDataForm> searchProducts(String keyword) {
        List<Product> products = productDao.search(keyword);
        List<ProductDataForm> results = new ArrayList<>();

        for (Product product : products) {
            ProductDetails details = productDetailDao.findById(product.getId());
            ProductCategory category = product.getCategory();

            results.add(mapProductToDataForm(product, details, category));
        }
        return results;
    }


    private ProductDataForm mapProductToDataForm(Product product, ProductDetails details, ProductCategory category) {
        ProductForm pf = new ProductForm();
        pf.setId(product.getId().toString());
        pf.setName(product.getName());
        pf.setDescription(product.getDescription());
        pf.setPrice(product.getPrice().toString());
        pf.setQuantity(String.valueOf(product.getQuantity()));
        pf.setCreatedAt(product.getCreatedAt().toString());
        pf.setUpdatedAt(product.getUpdatedAt().toString());

        ProductDetailForm df = new ProductDetailForm();
        df.setId(details.getId().toString());
        df.setWeight(details.getWeight().toString());
        df.setDimensions(details.getDimensions());
        df.setColor(details.getColor());
        df.setCreatedAt(details.getCreatedAt().toString());

        ProductCategoryForm cf = new ProductCategoryForm();
        cf.setId(category.getId().toString());
        cf.setCategoryName(category.getCategoryName());
        cf.setCreatedAt(category.getCreatedAt().toString());
        cf.setUpdatedAt(category.getUpdatedAt().toString());

        ProductDataForm response = new ProductDataForm();
        response.setProduct(pf);
        response.setProductDetails(df);
        response.setCategory(cf);

        return response;
    }
    public void reduceQuantity(Integer productId, Integer quantity) {
        Product product = productDao.findById(productId);
        if (product == null) throw new RuntimeException("Product not found");
        if (product.getQuantity() < quantity) throw new RuntimeException("Insufficient stock");
        product.setQuantity(product.getQuantity() - quantity);
        productDao.update(product);
    }


}
