package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
  private final ProductService productService;
  private final UploadService uploadService;

  public ProductController(ProductService productService, UploadService uploadService) {
    this.productService = productService;
    this.uploadService = uploadService;
  }

  @RequestMapping("/admin/product")
  public String getProduct(Model model) {
    List<Product> products = this.productService.getAllProducts();
    model.addAttribute("products", products);
    return "admin/product/show";
  }

  @RequestMapping("/admin/product/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    Product product = this.productService.getProductById(id);
    model.addAttribute("product", product);
    model.addAttribute("id", id);
    return "admin/product/detail";
  }

  @GetMapping("/admin/product/create") // GET
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());
    return "admin/product/create";
  }

  @PostMapping(value = "/admin/product/create")
  public String handleCreateProduct(Model model,
      @ModelAttribute("newProduct") @Valid Product pr, BindingResult newProductBindingResult,
      @RequestParam("hoidanitFile") MultipartFile file) {
    // Validate
    if (newProductBindingResult.hasErrors()) {
      return "/admin/product/create";
    }
    String image = this.uploadService.handleSaveUploadFile(file, "product");
    pr.setImage(image);

    // save
    this.productService.createProduct(pr);
    return "redirect:/admin/product";
  }

  @RequestMapping("/admin/product/update/{id}")
  public String getUpdateProductDetailPage(Model model, @PathVariable long id) {
    Product currentProduct = this.productService.getProductById(id);
    model.addAttribute("newProduct", currentProduct);
    return "admin/product/update";
  }

  @PostMapping("/admin/product/update")
  public String postUpdateUser(Model model, @ModelAttribute("newProduct") Product product,
      @RequestParam("hoidanitFile") MultipartFile file) {
    Product currentProduct = this.productService.getProductById(product.getId());
    // String image = this.uploadService.handleSaveUploadFile(file, "product");
    // product.setImage(image);
    if (currentProduct != null) {
      currentProduct.setName(product.getName());
      currentProduct.setPrice(product.getPrice());
      currentProduct.setDetailDesc(product.getDetailDesc());
      currentProduct.setShortDesc(product.getShortDesc());
      currentProduct.setQuantity(product.getQuantity());
      currentProduct.setFactory(product.getFactory());
      currentProduct.setTarget(product.getTarget());
      currentProduct.setImage(product.getImage());
      this.productService.createProduct(currentProduct);
    }
    return "redirect:/admin/product";
  }

  @GetMapping("/admin/product/delete/{id}")
  public String getDeleteProductPage(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    model.addAttribute("newProduct", new Product());
    return "admin/product/delete";
  }

  @PostMapping("/admin/product/delete")
  public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
    this.productService.deleteProduct(product.getId());
    return "redirect:/admin/product";
  }

}