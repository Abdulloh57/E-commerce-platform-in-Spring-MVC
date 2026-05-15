package library.uz.controller.dto;

import java.util.List;

public record OrderCreateDto(Integer userId, List<OrderItemDto> orderItems) {
}
