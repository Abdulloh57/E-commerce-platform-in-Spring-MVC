package library.uz.servise;


import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;
import library.uz.controller.dto.UserResponseDto;
import library.uz.dao.UserDatabase;
import library.uz.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDatabase repository;
    public int register(UserCreateDto request){
        return repository.AddUser(request);
    }


    public UserResponseDto login(UserLoginDto request) {
        int login = repository.login(request);
        if (login != 0){
            return repository.getById(login);
        }
        return null;
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
