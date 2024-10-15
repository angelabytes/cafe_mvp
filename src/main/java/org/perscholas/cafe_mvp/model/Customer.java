package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity

public class Customer extends CafeUser {

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    public Customer() {
        super();
        setRole(Role.CUSTOMER);
    }


}
