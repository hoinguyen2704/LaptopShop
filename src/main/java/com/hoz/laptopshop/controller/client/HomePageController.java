package com.hoz.laptopshop.controller.client;

import com.hoz.laptopshop.dto.request.RegisterDTO;
import com.hoz.laptopshop.entitis.Product;
import com.hoz.laptopshop.service.IProductService;
import com.hoz.laptopshop.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final IProductService iProductService;
    private final IUserService iUserService;

    // private final PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        List<Product> listProducts = iProductService.fetchProducts();
        model.addAttribute("products", listProducts);
        HttpSession session = request.getSession(false);
        return "client/homepage/index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(value = "success", required = false) Boolean success) {
        model.addAttribute("registerUser", new RegisterDTO());
        if (success != null) {
            model.addAttribute("success", success);
        }
        // model.addAttribute("checkOverLay", false);
        return "client/auth/signin";
    }

    // @GetMapping("/register")
    // public String getRegisterPage(Model model) {
    // model.addAttribute("registerUser", new RegisterDTO());
    // // model.addAttribute("checkOverLay", true);
    // return "client/auth/signin";
    // }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        // validate
        if (bindingResult.hasErrors()) {
            return "client/auth/signin";
        }

        iUserService.register(registerDTO);
        return "redirect:/login?success=true";

    }

    // @GetMapping("/login")
    // public String getLoginPage(Model model) {

    // return "client/auth/signin";
    // }
    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }

    // @GetMapping("/test-session")
    // @ResponseBody
    // public String testSession(HttpServletRequest request) {
    //     HttpSession session = request.getSession();
    //     session.setAttribute("test", "hello");
    //     return session.getId();
    // }
}
