package spring.controllers;


import spring.entities.Alcohol;
import spring.entities.Client;
import spring.entities.Dairy;
import spring.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static spring.utils.Random.getRandom;

@RestController
public class ProductServiceController {
    private static Map<String, Product> productRepo = new HashMap<>();
    private static final int ADULTHOOD = 18;

    static {
        Dairy dairy = new Dairy();
        dairy.setId("1");
        dairy.setName("Milk");
        dairy.setBalance(getRandom());
        dairy.setPrice(getRandom());
        dairy.setCarbohydrate(getRandom());
        dairy.setProtein(getRandom());
        productRepo.put(dairy.getName(), dairy);

        Alcohol alcohol = new Alcohol();
        alcohol.setId("2");
        alcohol.setName("Vodka");
        alcohol.setBalance(getRandom());
        alcohol.setPrice(getRandom());
        alcohol.setPercentage(getRandom());
        productRepo.put(alcohol.getName(), alcohol);
    }

    @GetMapping(value = "/products")
    public final ResponseEntity<Object> buyProduct(@RequestBody final Client client) {
        String message;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Product product = productRepo.get(client.getName());
        if (product == null) {
            message = "No product. Entry something else";
        } else if (product.checkCount(client.getCount())) {
            message = "No amount of product. Entry less";
        } else if (product instanceof Alcohol && client.getAge() < ADULTHOOD) {
            message = "You are not adulthood. Please, go a way!";
        } else {
            int change = client.getMoney() - client.getCount() * product.getPrice();
            if (change > 0) {
                message = "You buy:" + product.getName() + "\nYou change:" + change;
                status = HttpStatus.OK;
            } else {
                message = "You do not have enough money to buy the product: " + change;
            }
        }
        return new ResponseEntity<>(message, status);
    }

    @PostMapping(value = "/products/dairy/new")
    public final ResponseEntity<Object> setDairy(@RequestBody final Dairy dairy) {
        String message = "Product is created successfully";
        HttpStatus status = HttpStatus.OK;
        if (productRepo.get(dairy.getName()) == null) {
            productRepo.put(dairy.getName(), dairy);
        } else {
            message = "Product is already there";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(message, status);
    }
}
