package org.perscholas.cafe_mvp.repository;

import org.perscholas.cafe_mvp.model.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeUserRepository extends JpaRepository<CafeUser, Long> {
}
