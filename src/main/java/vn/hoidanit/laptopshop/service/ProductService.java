package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    return this.productRepository.findAll();
  }

  public List<Product> getOneUserByName(String name) {
    return this.productRepository.findOneByName(name);
  }

  public Product getUserById(long id) {
    return this.productRepository.findById(id);
  }

  public Product createProduct(Product product) {
    return this.productRepository.save(product);
  }

  public void deleteUser(long id) {
    this.productRepository.deleteById(id);
  }
}
