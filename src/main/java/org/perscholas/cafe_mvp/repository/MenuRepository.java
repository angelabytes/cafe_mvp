package org.perscholas.cafe_mvp.repository;

import org.perscholas.cafe_mvp.model.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<CafeMenu, Long> {
}
