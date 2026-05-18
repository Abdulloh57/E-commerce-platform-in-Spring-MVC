package library.uz.dao;

import library.uz.controller.dto.ProductCreateDto;
import library.uz.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductDatabase extends DatabaseConnection{

    private final JdbcTemplate jdbcTemplate;

    public void addProduct(ProductCreateDto product, String imagePath){
        String ADD_PRODUCT = "Insert Into product(name,price,image_path) values (?,?,?)";
        jdbcTemplate.update(ADD_PRODUCT, product.name() , product.price() ,imagePath);
    }

    public List<Product> getAll(){
        return jdbcTemplate.query("select * from product" ,
                (resultSet,rowNum) -> makeProduct(resultSet));
    }

    public List<Product> getByOrder(List<Integer> ids){
        if (ids == null || ids.isEmpty()){
            return new ArrayList<>();
        }
        String QUERY = makeQUERY(ids);

        return jdbcTemplate.query(QUERY, (resultSet, rowNum) -> makeProduct(resultSet));
    }

    private Product makeProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getString("image_path")
        );
    }

    private String makeQUERY(List<Integer> ids){
        StringBuilder q = new StringBuilder();
        for (int i : ids){
            q.append(i).append(",");
        }
        return "select * from product where id in ("+q.substring(0, q.length() - 1)+")";

    }
}
