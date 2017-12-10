package com.company;

public class ChampionInfoSheet {

    private String championName;
    private double health;
    private double attack_damage;
    private double attack_speed;
    private double movement_speed;
    private double armor;
    private double magic_resist;
    private int level;

    String ability1;
    String ability2;
    String ability3;
    String ability4;

    String generateInfoSheet(Champion champion){

        calculatedChampionStats(champion);

        return String.format(
                "   %-20s: %s%n" +
                        "   %-20s: %d%n" +
                        "   %-31s: %.0f%n" +
                        "   %-21s: %.0f%n" +
                        "   %-23s: %.3f%n" +
                        "   %-18s: %.0f%n" +
                        "   %-31s: %.0f%n" +
                        "   %-25s: %.0f%n%n%n" +
                        "",
                "CHAMPION NAME", championName,
                "CHAMPION LEVEL", level,
                "HEALTH", health,
                "ATTACK DAMAGE", attack_damage,
                "ATTACK SPEED", attack_speed,
                "MOVEMENT SPEED", movement_speed,
                "ARMOR", armor,
                "MAGIC RESIST", magic_resist);}

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
}
