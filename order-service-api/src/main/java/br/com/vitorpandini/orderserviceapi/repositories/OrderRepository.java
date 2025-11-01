package br.com.vitorpandini.orderserviceapi.repositories;

import br.com.vitorpandini.orderserviceapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
