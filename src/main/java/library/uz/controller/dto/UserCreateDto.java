package library.uz.controller.dto;

import library.uz.model.RoleName;

public record UserCreateDto(String name, String username, String password, RoleName role) {
}
