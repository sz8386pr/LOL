package com.company;

public class Champion {

    private String championName;
    private double health;
    private double he_level;
    private double attack_damage;
    private double ad_level;
    private double attack_speed;
    private double as_level;
    private double movement_speed;
    private double armor;
    private double ar_level;
    private double magic_resist;
    private double mr_level;
    private int level;

    public Champion(String championName, double health, double he_level, double attack_damage, double ad_level, double attack_speed, double as_level,
                    double movement_speed, double armor, double ar_level, double magic_resist, double mr_level, int level) {

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
        this.level = level;
    }

    String getChampionName() {
        return championName;
    }

    void setChampionName(String championName) {
        this.championName = championName;
    }

    double getHealth() {
        return health;
    }

    void setHealth(double health) {
        this.health = health;
    }

    double getHe_level() {
        return he_level;
    }

    void setHe_level(double he_level) {
        this.he_level = he_level;
    }

    double getAttack_damage() {
        return attack_damage;
    }

    void setAttack_damage(double attack_damage) {
        this.attack_damage = attack_damage;
    }

    double getAd_level() {
        return ad_level;
    }

    void setAd_level(double ad_level) {
        this.ad_level = ad_level;
    }

    double getAttack_speed() {
        return attack_speed;
    }

    void setAttack_speed(double attack_speed) {
        this.attack_speed = attack_speed;
    }

    double getAs_level() {
        return as_level;
    }

    void setAs_level(double as_level) {
        this.as_level = as_level;
    }

    double getMovement_speed() {
        return movement_speed;
    }

    void setMovement_speed(double movement_speed) {
        this.movement_speed = movement_speed;
    }

    double getArmor() {
        return armor;
    }

    void setArmor(double armor) {
        this.armor = armor;
    }

    double getAr_level() {
        return ar_level;
    }

    void setAr_level(double ar_level) {
        this.ar_level = ar_level;
    }

    double getMagic_resist() {
        return magic_resist;
    }

    void setMagic_resist(double magic_resist) {
        this.magic_resist = magic_resist;
    }

    double getMr_level() {
        return mr_level;
    }

    void setMr_level(double mr_level) {
        this.mr_level = mr_level;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

}
