package library.uz.controller;

import library.uz.controller.dto.OrderCreateDto;
import library.uz.servise.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    @PostMapping
    @ResponseBody
    public String  AddOrder(@RequestBody OrderCreateDto request){

        service.addOrder(request);
        return "Order added successfully";
    }

}
