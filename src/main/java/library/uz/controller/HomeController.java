package library.uz.controller;

import jakarta.servlet.http.HttpSession;
import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;
import library.uz.controller.dto.UserResponseDto;
import library.uz.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("user", new UserLoginDto(null,null));
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new UserLoginDto(null,null));
        return "login";
    }

    @GetMapping("/register")
    public String registr(Model model){
        model.addAttribute("user",new UserCreateDto(null,null,null,null));
        return "register";
    }
    @GetMapping("/home")
    public String dasboard(Model model, HttpSession session){
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        model.addAttribute("username",user.username());
        return "dashboard";
    }


    @GetMapping("/logout")
    public String logout(Model model,HttpSession session){
        model.addAttribute("user",new UserLoginDto(null,null));
        session.invalidate();
        return "redirect:/login";
    }

}
