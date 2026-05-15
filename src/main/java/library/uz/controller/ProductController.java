package library.uz.controller;

import library.uz.controller.dto.ProductCreateDto;

import library.uz.model.Product;
import library.uz.servise.FileServise;
import library.uz.servise.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServise service;
    private final FileServise fileServise;
    @PostMapping
    public String addProduct(@ModelAttribute ProductCreateDto request, @RequestParam("file") MultipartFile file) throws IOException {
        String imagePath = fileServise.upload(file);
        service.addProduct(request,imagePath);
        return "redirect:/user/products";
    }

    @GetMapping
    @ResponseBody
    public List<Product> getAll(){
        return service.getAll();
    }
}
