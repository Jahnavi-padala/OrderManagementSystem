package com.jocata.controller;

import com.jocata.productservice.form.ProductDataForm;
import com.jocata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDataForm> addProduct(@RequestBody ProductDataForm productDataForm) {
        ProductDataForm response = productService.addProduct(productDataForm);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ProductDataForm> getProductById(@PathVariable String id) {
        ProductDataForm product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/searchProducts")
    public ResponseEntity<List<ProductDataForm>> searchProducts(@RequestParam("keyword") String keyword) {
        List<ProductDataForm> results = productService.searchProducts(keyword);
        return ResponseEntity.ok(results);
    }
    @PutMapping("/{productId}/reduceQuantity")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Integer productId, @RequestBody Map<String, Integer> body) {
        Integer qty = body.get("quantity");
        productService.reduceQuantity(productId, qty);
        return ResponseEntity.ok().build();
    }

}
