package com.example.webpos.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@SessionScope
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }

}
