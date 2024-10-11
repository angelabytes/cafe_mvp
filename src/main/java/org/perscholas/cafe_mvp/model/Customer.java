package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity

public class Customer extends CafeUser {

    public Customer() {
        super();
        setRole(Role.CUSTOMER);
    }
}
