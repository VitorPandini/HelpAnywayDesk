package br.com.vitorpandini.orderserviceapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.OrderStatusEnum;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "tb_order")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 45)
    private String requesterId;
    @Column(nullable = false,length = 45)
    private String customerId;
    @Column(nullable = false,length = 50)
    private String title;
    @Column(nullable = false,length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private OrderStatusEnum status = OrderStatusEnum.OPEN;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

}
