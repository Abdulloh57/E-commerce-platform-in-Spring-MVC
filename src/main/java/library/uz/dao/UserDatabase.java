package library.uz.dao;

import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;

import library.uz.controller.dto.UserResponseDto;
import library.uz.exception.RecordNotException;
import library.uz.model.RoleName;
import library.uz.model.User;
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
public class UserDatabase extends DatabaseConnection {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    String ADD_USER_QUERY = "select * from add_user(?,?,?,?)";
    String LOGIN_USER_QUERY = "select * from login(?,?)";
    String GET_USERS_QUERY ="select * from users";

    public int AddUser(UserCreateDto request){

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY)) {
            preparedStatement.setString(1,request.name());
            preparedStatement.setString(2,request.username());
            preparedStatement.setString(3,request.password());
            preparedStatement.setString(4, request.role().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.info("Exception {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
            //throw new DublicateRecordException(String.format("%s already exists", request.username()));
        }
        return 0;
    }

    public int login(UserLoginDto request){
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_USER_QUERY)){
            preparedStatement.setString(1, request.username());
            preparedStatement.setString(2, request.password());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e ){
            log.info("Exception {}", e.getMessage());
            throw new RecordNotException(String.format("%s user not found", request.username()));

        }
        return 1;
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
                users.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return users;
    }

    public UserResponseDto getById(int userId){
        String GET_BY_ID = "select* from get_by_id(?)";
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)){
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new UserResponseDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        RoleName.valueOf(resultSet.getString("role_name"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
