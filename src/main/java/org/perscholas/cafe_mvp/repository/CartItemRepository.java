package org.perscholas.cafe_mvp.repository;

import org.perscholas.cafe_mvp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
