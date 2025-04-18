package com.alten.testTech.controller;

import com.alten.testTech.entity.Product;
import com.alten.testTech.payload.ProductRequest;
import com.alten.testTech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@ModelAttribute ProductRequest request) throws IOException {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product response = productService.getProduct(id);
        return response != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @ModelAttribute ProductRequest updatedProduct) {
        try {
            Product updated = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
