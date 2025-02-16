package org.perscholas.cafe_mvp.repository;


import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer(Customer customer);
    Optional<Cart> findByCustomerId(Long customerId);
}
