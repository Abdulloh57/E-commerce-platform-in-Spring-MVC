package library.uz.servise;

import library.uz.controller.dto.OrderCreateDto;
import library.uz.controller.dto.OrderItemDto;
import library.uz.model.Order;
import library.uz.model.OrderItem;
import library.uz.model.OrderStatus;
import library.uz.model.Product;
import library.uz.repository.OrderItemRepository;
import library.uz.repository.OrderRepository;
import library.uz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addOrder(OrderCreateDto request){
        Order order = new Order();
        order.setUserid(request.userId());


        List<Integer> list = request.orderItems().stream().map(OrderItemDto::productId).toList();
        List<Product> products = productRepository.findByIdIn(list);
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
        Order savedOrder = repository.save(order);

        List<OrderItem> items = request.orderItems().stream()
                .map(item -> new OrderItem(
                        null,
                        savedOrder.getId(),
                        item.productId(),
                        item.quantity()
                ))
                .toList();
        orderItemRepository.saveAll(items);

    }

    public List<Order> getOrderByUserId(int userId){
        return repository.findByUseridOrderByIdDesc(userId);
    }
}
