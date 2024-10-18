package org.perscholas.cafe_mvp.repository;

import org.perscholas.cafe_mvp.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findMenuItemById(Long menuItemId);

}
