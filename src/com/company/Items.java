package com.company;

import java.util.HashMap;

public class Items {
   private HashMap<String, Double> items = new HashMap<>();

   public Items(String bonus_type, double bonus_value){
       this.items.put(bonus_type, bonus_value);
   }
}
