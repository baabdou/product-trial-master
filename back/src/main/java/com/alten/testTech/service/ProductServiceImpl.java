package com.alten.testTech.service;

import com.alten.testTech.entity.Product;
import com.alten.testTech.entity.enums.InventoryStatus;
import com.alten.testTech.payload.ProductRequest;
import com.alten.testTech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(ProductRequest request) throws IOException {
        if(!accountService.isAdmin()) throw new RuntimeException("Please logIn as admin to add Product");
        Product product = new Product();
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String filename = UUID.randomUUID() + "_" + request.getFile().getOriginalFilename();
            Path path = Paths.get(uploadDir, filename);
            Files.copy(request.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(filename);
        }
        if(request.getCode().equals("")) throw new RuntimeException("Please enter a valid product code");
        if(request.getName().equals("")) throw new RuntimeException("Please enter a valid product name");
        product.setCode(request.getCode());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setInternalReference(request.getInternalReference());
        product.setQuantity(product.getQuantity());
        product.setInventoryStatus(InventoryStatus.valueOf(request.getInventoryStatus()));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setRating(request.getRating());
        product.setShellId(request.getShellId());
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);

    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest request) throws IOException {
        if(!accountService.isAdmin()) throw new RuntimeException("Please logIn as admin to update Product");
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product existingProduct = optionalProduct.get();
        if (request.getCode() != null) existingProduct.setCode(request.getCode());
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getDescription() != null) existingProduct.setDescription(request.getDescription());
        if (request.getCategory() != null) existingProduct.setCategory(request.getCategory());
        if (request.getInventoryStatus() != null) existingProduct.setInventoryStatus(InventoryStatus.valueOf(request.getInventoryStatus()));

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            if (existingProduct.getImage() != null) {
                Path oldPath = Paths.get(uploadDir, existingProduct.getImage());
                Files.deleteIfExists(oldPath);
            }

            String filename = UUID.randomUUID() + "_" + request.getFile().getOriginalFilename();
            Path newPath = Paths.get(uploadDir, filename);
            Files.copy(request.getFile().getInputStream(), newPath, StandardCopyOption.REPLACE_EXISTING);
            existingProduct.setImage(filename);
        }

        return productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Long id) throws IOException {
        if(!accountService.isAdmin()) throw new RuntimeException("Please logIn as admin to delete Product");
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getImage() != null) {
                Path path = Paths.get(uploadDir, product.getImage());
                Files.deleteIfExists(path);
            }
            productRepository.deleteById(id);
        }

    }
}
