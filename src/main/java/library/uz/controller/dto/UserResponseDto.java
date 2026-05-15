package library.uz.controller.dto;

import library.uz.model.RoleName;

public record UserResponseDto (int id, String name, String username, RoleName role){
}
