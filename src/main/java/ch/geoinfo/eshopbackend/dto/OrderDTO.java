package ch.geoinfo.eshopbackend.dto;


import ch.geoinfo.eshopbackend.model.Order;
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
