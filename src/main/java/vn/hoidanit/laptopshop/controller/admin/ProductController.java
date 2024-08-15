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
}