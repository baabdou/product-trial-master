package com.alten.testTech.service;


import com.alten.testTech.entity.Product;
import com.alten.testTech.payload.ProductRequest;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product addProduct(ProductRequest createProductRequest) throws IOException;
    public Product getProduct(Long id);
    public Product updateProduct(Long id, ProductRequest request) throws IOException;
    public void deleteProduct(Long id) throws IOException;
}
