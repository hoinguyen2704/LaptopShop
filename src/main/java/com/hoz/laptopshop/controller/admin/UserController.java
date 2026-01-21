package com.hoz.laptopshop.controller.admin;

import com.hoz.laptopshop.entitis.User;
import com.hoz.laptopshop.entitis.User_;
import com.hoz.laptopshop.service.IRoleService;
import com.hoz.laptopshop.service.IUpLoadFileService;
import com.hoz.laptopshop.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final IUpLoadFileService iUploadService;

    @RequestMapping("/admin/user")
    public String getUserPage(Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(User_.CREATED_AT).descending());
        Page<User> usersPage = userService.getAllUsers(pageable);
        List<User> users = usersPage.getContent();
        model.addAttribute("users1", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "admin/user/user-page";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model,
            @PathVariable long id) {
        User user = userService.getUserById(id);
        // Role role = user.getRole();
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        // model.addAttribute("role", role);
        // System.out.println("Role: " + role);
        return "admin/user/detail";
    }

    @GetMapping("admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user/create";
    }

    @PostMapping(value = "admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User newUser,
            BindingResult newUserBindingResult,
            @RequestParam("avatarFile") MultipartFile file) throws IOException {
        // List<FieldError> errors = newUserBindingResult.getFieldErrors();
        // for (FieldError error : errors) {
        // System.out.println(">>>>" + error.getField() + " - " +
        // error.getDefaultMessage());
        // }

        // validate
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String avatar = iUploadService.uploadFile("avatar", file);
        String hashPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setAvatar(avatar);
        newUser.setPassword(hashPassword);
        newUser.setRole(roleService.getRoleByName(newUser.getRole().getName()));
        // save
        this.userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        // User currentUser = userService.getUserById(id);
        model.addAttribute("newUser", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User editUser) {
        User currentUser = userService.getUserById(editUser.getId());
        if (currentUser != null) {
            currentUser.setAddress(editUser.getAddress());
            currentUser.setFullName(editUser.getFullName());
            currentUser.setPhone(editUser.getPhone());
            currentUser.setRole(roleService.getRoleByName(editUser.getRole().getName()));
            // bug here
            userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }
    
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
        this.userService.deleteAUser(eric.getId());
        return "redirect:/admin/user";
    }
}
