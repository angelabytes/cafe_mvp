package org.perscholas.cafe_mvp.repository;

import org.perscholas.cafe_mvp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
