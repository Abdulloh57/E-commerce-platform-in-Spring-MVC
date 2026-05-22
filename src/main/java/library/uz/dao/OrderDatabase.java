package library.uz.dao;

import library.uz.controller.dto.OrderItemDto;
import library.uz.model.Order;
import library.uz.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDatabase extends DatabaseConnection{

    private final JdbcTemplate jdbcTemplate;

    public Integer addOrder(Order order){
        String ADD_ORDER = "insert into orders(user_id,price,status) values (?,?,?) returning id";

        return jdbcTemplate.queryForObject(ADD_ORDER,
                (res,rowNum) -> res.getInt(1),
                order.getUserid(),
                order.getPrice(),
                order.getStatus().name());
    }

    public void addItem(int orderId, List<OrderItemDto> items){
        String ADD_ITEM = "insert into order_item(order_id, product_id , quantity) VALUES (?,?,?)";

        jdbcTemplate.batchUpdate(ADD_ITEM ,items,items.size(),
                ( ps,item)->{
                    ps.setInt(1,orderId);
                    ps.setInt(2,item.productId());
                    ps.setInt(3,item.quantity());
                });
    }

    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC ";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Order(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getDouble("price"),
                OrderStatus.valueOf(
                        resultSet.getString("status")
                )
        ),userId);
    }
}
