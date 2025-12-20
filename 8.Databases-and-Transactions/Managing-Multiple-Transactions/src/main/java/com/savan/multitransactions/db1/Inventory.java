package com.savan.multitransactions.db1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    private String productId;
    private Long balance;
}
