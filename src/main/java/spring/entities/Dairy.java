package spring.entities;

import lombok.Data;


@Data
public class Dairy extends Product {
    private int fat;
    private int protein;
    private int carbohydrate;
}
