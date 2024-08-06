package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

  @GetMapping("/admin/product")
  public String getProduct(Model model) {
    List<Product> products = this.productService.getAllProducts();
    model.addAttribute("product", products);
    return "admin/product/show";
  }

  @GetMapping("/admin/product/create") // GET
  public String getCreateProductPage(Model model) {
    model.addAttribute("newProduct", new Product());
    return "admin/product/create";
  }

  @PostMapping(value = "/admin/product/create")
  public String createProductPage(Model model,
      @ModelAttribute("newProduct") Product hoidanit,
      @RequestParam("hoidanitFile") MultipartFile file) {
    // String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");

    // hoidanit.setAvatar(avatar);

    // save
    // his.userService.handleSaveProduct(hoidanit);
    return "redirect:/admin/product";
  }
}