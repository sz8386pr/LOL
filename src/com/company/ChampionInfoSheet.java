package com.company;

import java.util.ArrayList;

public class ChampionInfoSheet {

    private String championName;
    private double health;
    private double attack_damage;
    private double attack_speed;
    private double movement_speed;
    private double armor;
    private double magic_resist;
    private int level;
    private double ability_power = 0;
    private double bonus_attack_damage = 0;

    private ArrayList<String> ability = new ArrayList<>();
    private ArrayList<Double> ability_ratio = new ArrayList<>();
    private ArrayList<String> ability_ratio_type = new ArrayList<>();
    private ArrayList<String> ability_type = new ArrayList<>();
    private ArrayList<Double> ability_damage = new ArrayList<>();
    private ArrayList<Double> ability_bonus_damage = new ArrayList<>();


    String generateInfoSheet(Champion champion, ArrayList<Abilities> abilitiesList){

        calculatedChampionStats(champion);
        calculateAbilityDamage(abilitiesList);

        String championStats = String.format(
                "   %-20s: %s%n" +
                        "   %-20s: %d%n" +
                        "   %-20s: %.0f%n" +
                        "   %-20s: %.0f%n" +
                        "   %-20s: %.3f%n" +
                        "   %-20s: %.0f%n" +
                        "   %-20s: %.0f%n" +
                        "   %-20s: %.0f%n%n%n" +
                        "",
                "CHAMPION NAME", championName,
                "CHAMPION LEVEL", level,
                "HEALTH", health,
                "ATTACK DAMAGE", attack_damage,
                "ATTACK SPEED", attack_speed,
                "MOVEMENT SPEED", movement_speed,
                "ARMOR", armor,
                "MAGIC RESIST", magic_resist);

        String abilities = "";
        for (int i = 0; abilitiesList.size() > i; i++) {

            //If ability point is 0, consider that ability hasn't been trained yet
            if (abilitiesList.get(i).getLevel() == 0) {
                abilities += String.format(
                        "   %-20s: Ability has not been trained yet.%n",
                       ability.get(i));
            }
            else {
                abilities += String.format(
                        "   %-20s: %.0f + (%.0f) %s Damage%n",
                        ability.get(i), ability_damage.get(i), ability_bonus_damage.get(i), ability_type.get(i));
            }
        }


        return championStats+abilities;}

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

    private void calculateAbilityDamage(ArrayList<Abilities> abilitiesList){
        for (int i = 0; abilitiesList.size() > i; i++) {
            if (abilitiesList.get(i).getLevel() == 1) {
                ability_damage.add(i, abilitiesList.get(i).getLv1());
            } else if (abilitiesList.get(i).getLevel() == 2) {
                ability_damage.add(i, abilitiesList.get(i).getLv2());
            } else if (abilitiesList.get(0).getLevel() == 3) {
                ability_damage.add(i, abilitiesList.get(i).getLv3());
            } else if (abilitiesList.get(i).getLevel() == 4) {
                ability_damage.add(i, abilitiesList.get(i).getLv4());
            } else if (abilitiesList.get(i).getLevel() == 5) {
                ability_damage.add(i, abilitiesList.get(i).getLv5());
            }

            ability.add(i, abilitiesList.get(i).getAbility_name());
            //set values only if user has allocated the skill level point towards the spell
            if (abilitiesList.get(i).getLevel() != 0) {
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

        }

    }
}
