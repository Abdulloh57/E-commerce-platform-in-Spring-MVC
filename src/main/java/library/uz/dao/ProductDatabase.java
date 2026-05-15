package library.uz.dao;

import library.uz.controller.dto.ProductCreateDto;
import library.uz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class ProductDatabase extends DatabaseConnection{
    private String ADD_PRODUCT="Insert Into product(name,price,image_path) values (?,?,?)";
    private String GET_ALL = "select * from product";

    //nimadur qaytarish kerak
    public void addProduct(ProductCreateDto product, String imagePath){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT)){
            preparedStatement.setString(1,product.name());
            preparedStatement.setDouble(2,product.price());
            preparedStatement.setString(3,imagePath);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    public List<Product> getAll(){
        List<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image_path")
                );
                products.add(product);
            }
        }catch (SQLException e){
            //bu yerda ham log yozilishi kerak
            throw new RuntimeException();
        }
        return products;
    }

    public List<Product> getByOrder(List<Integer> ids) {
        List<Product> products = new ArrayList<>();
        if (ids == null || ids.isEmpty()){
            return products;
        }
        StringBuilder q = new StringBuilder();
        for (int i : ids){
            q.append(i).append(",");
        }
        String GET_PRODUCTS = "select * from product where id in ("+q.substring(0, q.length() - 1)+")";

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image_path")
                );
                products.add(product);
            }
        }catch (SQLException e){
            throw new RuntimeException("ERROR WHILE GET PRODUCTS");
        }
        return products;
    }

}
