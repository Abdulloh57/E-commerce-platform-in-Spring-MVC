package library.uz.dao;

import library.uz.controller.dto.OrderCreateDto;
import library.uz.controller.dto.OrderItemDto;
import library.uz.model.Order;
import library.uz.model.OrderItem;
import library.uz.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDatabase extends DatabaseConnection{
    public int addOrder(Order order){
        String ADD_ORDER = "insert into orders(user_id,price,status) values (?,?,?) returning id";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER)){
            preparedStatement.setInt(1,order.getUserid());
            preparedStatement.setDouble(2,order.getPrice());
            preparedStatement.setString(3,order.getStatus().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            throw new RuntimeException("ERROR WHILE ADD ORDER",e);
        }
        return 0;
    }



    public void addItem(int orderId,List<OrderItemDto> items) {
        String ADD_ITEM = "insert into order_item(order_id, product_id , quantity) VALUES (?,?,?)";

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_ITEM)){
            for (OrderItemDto item : items){

                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, item.productId());
                preparedStatement.setInt(3, item.quantity());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (SQLException e){
            throw new RuntimeException("ERROR WHILE ADD ITEM");
        }
    }


    public List<Order> getOrdersByUserId(int userId){

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC ";

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql)){

            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){

                while (resultSet.next()){

                    Order order = new Order();

                    order.setId(resultSet.getInt("id"));
                    order.setUserid(resultSet.getInt("user_id"));
                    order.setPrice(resultSet.getDouble("price"));

                    order.setStatus(
                            OrderStatus.valueOf(
                                    resultSet.getString("status")
                            )
                    );

                    orders.add(order);
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(
                    "ERROR WHILE GETTING ORDERS",
                    e
            );
        }

        return orders;
    }


}
