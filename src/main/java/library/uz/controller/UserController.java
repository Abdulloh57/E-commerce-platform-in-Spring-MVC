package library.uz.controller;


import jakarta.servlet.http.HttpSession;
import library.uz.controller.dto.ProductCreateDto;
import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;
import library.uz.controller.dto.UserResponseDto;
import library.uz.model.Order;
import library.uz.model.User;
import library.uz.servise.OrderService;
import library.uz.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService servise;
    private final OrderService orderService;

    @PostMapping("/register")
    public String login(@ModelAttribute UserCreateDto request) {
        servise.register(request);
        return "redirect:/login";
    }


    @PostMapping("/login")
    public String login(
            @ModelAttribute UserLoginDto request, HttpSession session
    ) {
        UserResponseDto user = servise.login(request);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        } else return "login";

    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAll() {
        return servise.getAll();
    }


    @GetMapping("products")
    public String products(Model model, HttpSession session){
        UserResponseDto user = (UserResponseDto)session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("product",new ProductCreateDto(null,0));
        return "products";
    }

    @GetMapping("/orders")
    public String orders(Model model,HttpSession session){
        UserResponseDto user = (UserResponseDto)session.getAttribute("user");
        model.addAttribute("user",user);
        List<Order> orders = orderService.getOrderByUserId(user.id());
        model.addAttribute("orders", orders);
        return "orders";
    }
}
