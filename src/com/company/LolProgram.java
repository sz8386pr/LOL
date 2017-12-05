package com.company;

import static com.company.LolDB.*;
import java.sql.*;

public class LolProgram {
    public static void main(String[] args)  throws SQLException{
        DBSetup();
        LolGUI gui = new LolGUI();

    }

    static void DBSetup()  throws SQLException{

            String DBCreate =
                            "CREATE TABLE IF NOT EXISTS champions (" +
                            "  `champion_id` INT(11) NOT NULL AUTO_INCREMENT," +
                            "  `name` VARCHAR(45) NOT NULL," +
                            "  `health` DOUBLE NOT NULL," +
                            "  `he_level` DOUBLE NOT NULL," +
                            "  `attack_damage` DOUBLE NOT NULL," +
                            "  `ad_level` DOUBLE NOT NULL," +
                            "  `attack_speed` DOUBLE NOT NULL," +
                            "  `as_level` DOUBLE NOT NULL," +
                            "  `movement_speed` DOUBLE NOT NULL," +
                            "  `armor` DOUBLE NOT NULL," +
                            "  `ar_level` DOUBLE NOT NULL," +
                            "  `magic_resist` DOUBLE NOT NULL," +
                            "  `mr_level` DOUBLE NOT NULL," +
                            "  PRIMARY KEY (`champion_id`));\n";
             /*                "CREATE TABLE IF NOT EXISTS abilities (";

                            "  `ability_id` INT(11) NOT NULL AUTO_INCREMENT," +
                            "  `champion_id` INT(11) NULL DEFAULT NULL," +
                            "  `ability_name` VARCHAR(45) NULL DEFAULT NULL," +
                            "  `ability_ratio` DOUBLE NULL DEFAULT NULL," +
                            "  `ability_ratio_type` VARCHAR(45) NULL DEFAULT NULL," +
                            "  `ability_type` VARCHAR(45) NULL DEFAULT NULL," +
                            "  `lv1` DOUBLE NULL DEFAULT NULL," +
                            "  `lv2` DOUBLE NULL DEFAULT NULL," +
                            "  `lv3` DOUBLE NULL DEFAULT NULL," +
                            "  `lv4` DOUBLE NULL DEFAULT NULL," +
                            "  `lv5` DOUBLE NULL DEFAULT NULL," +
                            "  `lv6` DOUBLE NULL DEFAULT NULL," +
                            "  PRIMARY KEY (`ability_id`)," +
                            "  INDEX `chapion_id_idx` (`champion_id` ASC)," +
                            "  CONSTRAINT `chapion_id`" +
                            "    FOREIGN KEY (`champion_id`)" +
                            "    REFERENCES `lol`.`champions` (`champion_id`)" +
                            "    ON DELETE NO ACTION" +
                            "    ON UPDATE NO ACTION);";

*/
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
        Statement statement = conn.createStatement()) {
            statement.execute(DBCreate);

        }
    }
}
