package com.javanik.orderservice.repo;

import com.javanik.orderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<OrderEntity , UUID> {

}
