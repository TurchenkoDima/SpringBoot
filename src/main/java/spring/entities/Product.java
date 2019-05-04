package spring.entities;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private int balance;
    private int price;

    public final boolean checkCount(final int count) {
        return this.balance >= count;
    }
}
