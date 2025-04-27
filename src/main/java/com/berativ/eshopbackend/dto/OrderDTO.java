package com.berativ.eshopbackend.dto;


import com.berativ.eshopbackend.model.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime orderDate;
    private Order.OrderStatus status;
}
