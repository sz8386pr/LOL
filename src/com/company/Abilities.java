package com.company;

public class Abilities {
    private String ability_name;
    private double ability_ratio;
    private String ability_ratio_type;
    private String ability_type;
    private double lv1;
    private double lv2;
    private double lv3;
    private double lv4;
    private double lv5;
    private int level;
    private double bonus_damage;

    public Abilities(String ability_name, double ability_ratio, String ability_ratio_type, String ability_type, double lv1, double lv2, double lv3, double lv4, double lv5){

        this.ability_name = ability_name;
        this.ability_ratio = ability_ratio;
        this.ability_ratio_type = ability_ratio_type;
        this.ability_type = ability_type;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.lv4 = lv4;
        this.lv5 = lv5;
    }

    String getAbility_name() { return ability_name; }
    void setAbility_name(String abiilty_name) { this.ability_name = abiilty_name; }

    double getAbility_ratio() { return ability_ratio; }
    void setAbility_ratio(double ability_ratio) { this.ability_ratio = ability_ratio; }

    String getAbility_ratio_type() { return ability_ratio_type; }
    void setAbility_ratio_type(String ability_ratio_type) { this.ability_ratio_type = ability_ratio_type; }

    String getAbility_type() { return ability_type; }
    void setAbility_type(String ability_type) { this.ability_type = ability_type; }

    double getLv1() { return lv1; }
    void setLv1(double lv1) { this.lv1 = lv1; }

    double getLv2() { return lv2; }
    void setLv2(double lv2) { this.lv2 = lv2; }

    double getLv3() { return lv3; }
    void setLv3(double lv3) { this.lv3 = lv3; }

    double getLv4() { return lv4; }
    void setLv4(double lv4) { this.lv4 = lv4; }

    double getLv5() { return lv5; }
    void setLv5(double lv5) { this.lv5 = lv5; }

    int getLevel() { return level; }
    void setLevel(int level) { this.level = level; }

    double getBonus_damage() { return bonus_damage; }
    void setBonus_damage(double bonus_damage) { this.bonus_damage = bonus_damage; }
}
