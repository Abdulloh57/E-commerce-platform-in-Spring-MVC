package library.uz.dao;

import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;

import library.uz.controller.dto.UserResponseDto;
import library.uz.model.RoleName;
import library.uz.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDatabase {
    private final JdbcTemplate jdbcTemplate;


    public Integer addUser(UserCreateDto request){
        return jdbcTemplate.queryForObject("select add_user(?,?,?,?)",
                (rs , RowId) -> rs.getInt(1),
                request.name() ,
                request.username(),
                request.password(),
                request.role().name());
    }

    public Integer login(UserLoginDto request){
        return jdbcTemplate.queryForObject(
                "select login(?, ?)",
                Integer.class,
                request.username(),
                request.password()
        );
    }

    public List<User> getAll(){
        return jdbcTemplate.query("select * from users" ,(resultSet,RowNUm) ->
                new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ));
    }

    public UserResponseDto getById(int userId){
        String GET_BY_ID = "select* from get_by_id(?)";
        return jdbcTemplate.queryForObject(GET_BY_ID , (resultSet , rowNum)->
                new UserResponseDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        RoleName.valueOf(resultSet.getString("role_name"))
                ) , userId);
    }

}
