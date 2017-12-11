package com.company;

import java.util.HashMap;

public class Items {
    private String item_name;
    private HashMap<String, Double> items = new HashMap<>();

   public Items(String item_name, String bonus_type1, double bonus_value1, String bonus_type2, double bonus_value2,String bonus_type3, double bonus_value3){
       this.item_name = item_name;
       this.items.put(bonus_type1, bonus_value1);
       this.items.put(bonus_type2, bonus_value2);
       this.items.put(bonus_type3, bonus_value3);

   }

    String getItem_name() { return item_name; }

    void setItem_name(String item_name) { this.item_name = item_name; }

    HashMap<String, Double> getItems() { return items; }

    void setItems(String bonus_value, Double bonus_type) { this.items.put(bonus_value, bonus_type); }
}
