package library.uz.servise;

import library.uz.controller.dto.OrderCreateDto;
import library.uz.controller.dto.OrderItemDto;
import library.uz.dao.OrderDatabase;
import library.uz.dao.ProductDatabase;
import library.uz.model.Order;
import library.uz.model.OrderStatus;
import library.uz.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDatabase repository;
    private final ProductDatabase productRepository;
    public void addOrder(OrderCreateDto request){
        Order order = new Order();
        order.setUserid(request.userId());


        List<Integer> list = request.orderItems().stream().map(OrderItemDto::productId).toList();
        List<Product>products = productRepository.getByOrder(list);
        double price = 0;

        for (Product product : products) {

            OrderItemDto item = request.orderItems()
                    .stream()
                    .filter(i -> i.productId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow();

            price += product.getPrice() * item.quantity();
        }
        order.setPrice(price);
        order.setStatus(OrderStatus.CREATED);
        int orderId = repository.addOrder(order);
        repository.addItem(orderId,request.orderItems());

    }

    public List<Order> getOrderByUserId(int userId){
        return repository.getOrdersByUserId(userId);
    }
}
