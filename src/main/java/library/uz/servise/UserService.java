package library.uz.servise;


import library.uz.controller.dto.UserCreateDto;
import library.uz.controller.dto.UserLoginDto;
import library.uz.controller.dto.UserResponseDto;
import library.uz.exception.DublicateRecordException;
import library.uz.model.RoleName;
import library.uz.model.User;
import library.uz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    @Transactional
    public int register(UserCreateDto request){
        if (repository.existsByUsername(request.username())) {
            throw new DublicateRecordException("Username already exists");
        }
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(request.password());
        User savedUser = repository.save(user);
        repository.attachRoleToUser(savedUser.getId(), request.role().name());
        return savedUser.getId();
    }


    public UserResponseDto login(UserLoginDto request) {
        return repository.findByUsernameAndPassword(request.username(), request.password())
                .map(user -> {
                    String role = repository.findRoleNameByUserId(user.getId());
                    RoleName roleName = role == null ? RoleName.USER : RoleName.valueOf(role);
                    return new UserResponseDto(user.getId(), user.getName(), user.getUsername(), roleName);
                })
                .orElse(null);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
