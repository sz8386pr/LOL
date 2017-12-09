package com.company;

public class Champion {

    private String championName;
    double health;
    double he_level;
    double attack_damage;
    double ad_level;
    double attack_speed;
    double as_level;
    double movement_speed;
    double armor;
    double ar_level;
    double magic_resist;
    double mr_level;

    public Champion(String championName, double health, double he_level, double attack_damage, double ad_level, double attack_speed, double as_level,
                    double movement_speed, double armor, double ar_level, double magic_resist, double mr_level) {

        this.championName = championName;
        this.health = health;
        this.he_level = he_level;
        this.attack_damage = attack_damage;
        this.ad_level = ad_level;
        this.attack_speed = attack_speed;
        this.as_level = as_level;
        this.movement_speed = movement_speed;
        this.armor = armor;
        this.ar_level = ar_level;
        this.magic_resist = magic_resist;
        this.mr_level = mr_level;
    }

}
