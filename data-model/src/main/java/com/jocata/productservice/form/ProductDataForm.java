package com.jocata.productservice.form;

public class ProductDataForm {
    private ProductForm product;
    private ProductDetailForm productDetails;
    private ProductCategoryForm category;
    public ProductForm getProduct() {
        return product;
    }
    public void setProduct(ProductForm product) {
        this.product = product;
    }


    public ProductDetailForm getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(ProductDetailForm productDetails) {
        this.productDetails = productDetails;
    }


    public ProductCategoryForm getCategory() {
        return category;
    }
    public void setCategory(ProductCategoryForm category) {
        this.category = category;
    }
}
