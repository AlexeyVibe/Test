package ru.avmadeit.CarRental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.avmadeit.CarRental.models.User;
import ru.avmadeit.CarRental.services.UsersService;
import ru.avmadeit.CarRental.utils.UserValidator;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class UsersController {

    private UserValidator userValidator;
    private UsersService usersService;

    @Autowired
    public UsersController(UserValidator userValidator, UsersService usersService) {
        this.userValidator = userValidator;
        this.usersService = usersService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/starter")
    public String startPage() {
        return "auth/starter";
    }

    @GetMapping("/registration")
    public String newRegistration(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user
            , @RequestParam("file") BindingResult bindingResult) {


        userValidator.validate(user,bindingResult);

        if (bindingResult.hasErrors())
            return "auth/registration";

        usersService.save(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users",usersService.findAll());
        return "auth/admin";
    }

    @PatchMapping("/{id}/assignAdmin")
    public String assignAdmin(@PathVariable("id") int id) {
        usersService.assignAdmin(id);
        return "redirect:/auth/admin";
    }

    @PatchMapping("/{id}/assignUser")
    public String assignUser(@PathVariable("id") int id) {
        usersService.assignUser(id);
        return "redirect:/auth/admin";
    }

}
