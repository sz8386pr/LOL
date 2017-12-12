//Generate, calculate, display, save champion info sheet to a file

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChampionInfoSheet {

    //Base stats
    private String championName;
    private double health;
    private double attack_damage;
    private double attack_speed;
    private double movement_speed;
    private double armor;
    private double magic_resist;
    private int level;
    private double ability_power = 0;

    //Bonus stats
    private double bonus_health = 0;
    private double bonus_attack_damage = 0;
    private double bonus_attack_speed = 0;
    private double bonus_movement_speed = 0;
    private double bonus_armor = 0;
    private double bonus_magic_resist = 0;

    //ArrayList to contain ability information
    private ArrayList<String> ability;
    private ArrayList<Double> ability_ratio;
    private ArrayList<String> ability_ratio_type;
    private ArrayList<String> ability_type;
    private ArrayList<Double> ability_damage;
    private ArrayList<Double> ability_bonus_damage;

    private String output;


    String generateInfoSheet(Champion champion, ArrayList<Abilities> abilitiesList, ArrayList<Items> itemsList, Boolean saveToFile){

        calculatedChampionStats(champion);
        calculateItemStats(itemsList);
        calculateAbilityDamage(abilitiesList);

        String championStats = String.format(
                        "   %-14s: %s%n%n" +
                        "   %-14s: %d%n" +
                        "   %-14s: %.0f(+%.0f) = %.0f%n" +
                        "   %-14s: %.0f(+%.0f) = %.0f%n" +
                        "   %-14s: %.3f(+%.3f) = %.3f/sec%n" +
                        "   %-14s: %.0f(+%.0f) = %.0f%n" +
                        "   %-14s: %.0f(+%.0f) = %.0f%n" +
                        "   %-14s: %.0f(+%.0f) = %.0f%n" +
                        "   %-14s: %.0f%n%n%n" +
                        "",
                "CHAMPION NAME", championName,
                "CHAMPION LEVEL", level,
                "HEALTH", health, bonus_health, health + bonus_health,
                "ATTACK DAMAGE", attack_damage, bonus_attack_damage, attack_damage + bonus_attack_damage,
                "ATTACK SPEED", attack_speed, bonus_attack_speed, attack_speed + bonus_attack_speed,
                "MOVEMENT SPEED", movement_speed, bonus_movement_speed, movement_speed + bonus_movement_speed,
                "ARMOR", armor, bonus_armor, armor + bonus_armor,
                "MAGIC RESIST", magic_resist, bonus_magic_resist, magic_resist + bonus_magic_resist,
                "ABILITY POWER", ability_power);

        StringBuilder abilities = new StringBuilder();
        abilities.append(String.format("   Abilities%n%n"));
        for (int i = 0; abilitiesList.size() > i; i++) {

            //If ability point is 0, consider that ability hasn't been trained yet
            if (abilitiesList.get(i).getLevel() == 0) {
                abilities.append(String.format(
                        "   %-20s: This ability has not been trained yet.%n",
                        ability.get(i)));
            }
            //If ability ratio is 0, this ability does no damage
            else if (abilitiesList.get(i).getAbility_ratio() == 0) {
                abilities.append(String.format(
                        "   %-20s: This ability has no damage.%n",
                        ability.get(i)));
            } else {
                abilities.append(String.format(
                        "   %-20s: %.0f + (%.0f) %s Damage%n",
                        ability.get(i), ability_damage.get(i), ability_bonus_damage.get(i), ability_type.get(i)));
            }
        }

        //Equipped items list
        StringBuilder items = new StringBuilder();
        items.append(String.format("%n%n   Items%n"));

        //If there are no items selected/equipped, display that no items are equipped
        if (itemsList.size() == 0) {
            items.append(String.format("%n   No items are equipped"));
        }
        else {
            for (Items ItemsList : itemsList) {
                //Display the name of the equipped items
                String itemName = ItemsList.getItem_name();
                items.append(String.format("%n   %-25s| ", itemName));

                //Retrieve Bonus type & value pair using the Items class method
                HashMap<String, Double> bonusSets = ItemsList.getItems();
                for (Map.Entry<String, Double> entry :bonusSets.entrySet()) {
                    String bonusType = entry.getKey();
                    Double bonusValue = entry.getValue();
                    //If bonusType is null, no need to display
                    if (bonusType!=null) {
                        if (bonusValue > 1) {
                            items.append(String.format("%3.0f %-15s | ", bonusValue, bonusType));
                        }
                        //If the bonus value is below 1, convert and display percentage value
                        else {
                            items.append(String.format("%3.0f%% %-15s | ", bonusValue * 100, bonusType));
                        }
                    }
                }
            }
        }

        //Output consists of champion stats, abilities, and items part
        output = championStats + abilities + items;

        //If saveToFile CheckBox is checked, save to txt file
        if (saveToFile) {
            saveToFile();
        }
        return output;
    }

    //Calculate base champion stats
    private void calculatedChampionStats(Champion champion) {

        championName = champion.getChampionName();
        level = champion.getLevel();
        health = champion.getHealth() + (champion.getHe_level() * (level-1));
        attack_damage = champion.getAttack_damage() + (champion.getAd_level() * (level-1));

        if (level == 1) { attack_speed = champion.getAttack_speed(); }
        else { attack_speed = champion.getAttack_speed() * (Math.pow(champion.getAs_level(), (level-1))); }

        movement_speed = champion.getMovement_speed();
        armor = champion.getArmor() + (champion.getAr_level() * (level-1));
        magic_resist = champion.getMagic_resist() + (champion.getMr_level() * (level-1));
    }

    //Calculate bonus stats from items
    private void calculateItemStats(ArrayList<Items> itemsList){
        for (Items ItemsList : itemsList) {
            if (ItemsList.getItems().containsKey("Health")) {
                bonus_health += ItemsList.getItems().get("Health");
            }
            if (ItemsList.getItems().containsKey("Magic Resist")) {
                bonus_magic_resist += ItemsList.getItems().get("Magic Resist");
            }
            if (ItemsList.getItems().containsKey("Armor")) {
                bonus_armor += ItemsList.getItems().get("Armor");
            }
            if (ItemsList.getItems().containsKey("Attack Damage")) {
                bonus_attack_damage += ItemsList.getItems().get("Attack Damage");
            }

            //All the champions start with 0 ability power, so no need for bonus variable
            if (ItemsList.getItems().containsKey("Ability Power")) {
                ability_power += ItemsList.getItems().get("Ability Power");
            }

            //attack speed is little different because it is calculated based on the base attack speed (ie: bonus attack speed = 30% * 1.0 = 0.30)
            if (ItemsList.getItems().containsKey("Attack Speed")) {
                bonus_attack_speed += (ItemsList.getItems().get("Attack Speed")) * attack_speed;
            }

            //movement_speed is also different because sometimes it gives a flat movement bonus and sometimes it's percentage boost based on the base movement speed
            if (ItemsList.getItems().containsKey("Movement Speed")) {
                //If it gives flat movement speed boost
                if (ItemsList.getItems().get("Movement Speed") > 1) {
                    bonus_movement_speed += ItemsList.getItems().get("Movement Speed");
                }
                //If it gives percentage movement speed boost
                else {
                    bonus_movement_speed += (ItemsList.getItems().get("Movement Speed")) * movement_speed;
                }
            }


        }
    }

    //Calculate ability damages based on stats
    private void calculateAbilityDamage(ArrayList<Abilities> abilitiesList){
        ability = new ArrayList<>();
        ability_ratio = new ArrayList<>();
        ability_ratio_type = new ArrayList<>();
        ability_type = new ArrayList<>();
        ability_damage = new ArrayList<>();
        ability_bonus_damage = new ArrayList<>();
        for (int i = 0; abilitiesList.size() > i; i++) {
            ability.add(i, abilitiesList.get(i).getAbility_name());
            if (abilitiesList.get(i).getAbility_ratio() != 0 && abilitiesList.get(i).getLevel() != 0) {
                if (abilitiesList.get(i).getLevel() == 1) {
                    ability_damage.add(i, abilitiesList.get(i).getLv1());
                } else if (abilitiesList.get(i).getLevel() == 2) {
                    ability_damage.add(i, abilitiesList.get(i).getLv2());
                } else if (abilitiesList.get(i).getLevel() == 3) {
                    ability_damage.add(i, abilitiesList.get(i).getLv3());
                } else if (abilitiesList.get(i).getLevel() == 4) {
                    ability_damage.add(i, abilitiesList.get(i).getLv4());
                } else if (abilitiesList.get(i).getLevel() == 5) {
                    ability_damage.add(i, abilitiesList.get(i).getLv5());
                }

                //set values only if user has allocated the skill level point towards the spell
                //if (abilitiesList.get(i).getLevel() != 0) {
                ability_ratio.add(i, abilitiesList.get(i).getAbility_ratio());
                ability_ratio_type.add(i, abilitiesList.get(i).getAbility_ratio_type());
                ability_type.add(i, abilitiesList.get(i).getAbility_type());

                switch (ability_ratio_type.get(i)) {
                    case "Total AD":
                        ability_bonus_damage.add(i, ability_ratio.get(i) * (attack_damage + bonus_attack_damage));
                        break;
                    case "Bonus AD":
                        ability_bonus_damage.add(i, ability_ratio.get(i) * bonus_attack_damage);
                        break;
                    case "AP":
                        ability_bonus_damage.add(i, ability_ratio.get(i) * ability_power);
                        break;
                }

            }
            else {
                ability_damage.add(i, 0.0);
                ability_ratio.add(i, 0.0);
                ability_ratio_type.add(i, "");
                ability_type.add(i, "");
                ability_bonus_damage.add(i, 0.0);
            }

        }

    }

    //Save the output.
    String saveToFile(){
        String saveFolder = "ChampionInfoSheet";    //Default output location is ChampionInfoSheet folder
        SimpleDateFormat filenameFormatter = new SimpleDateFormat("MMMM_dd_yyyy_HH_mm");
        Date date = new Date();   //defaults to today, right now
        String formattedDate = filenameFormatter.format(date);
        String filename = championName+"_"+formattedDate+".txt";  //Default save file name format is Champion name, followed by formatted datetime.txt

        try(PrintWriter out = new PrintWriter(saveFolder+File.separator+filename)){
            out.println(output);
            return "Saved to " + filename;
        }
        catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
            return "Error saving to a file";
        }
    }
}
