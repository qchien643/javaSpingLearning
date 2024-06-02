package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserService userService;

  public UserService(UserService userService) {
    this.userService = userService;
  }

  public String handleHello() {
    return "Hello from service ";
  }
}
