package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LolDB {

    static String db_url = "jdbc:mysql://localhost:3306/lol";
    static String user = "user";
    static String password = "itecitec";

    // Creates tables and data sets if doesn't exists.
    static void DBSetup()  throws SQLException {
        String createChampionsTable =
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

        String createAbilitiesTable =
                "CREATE TABLE IF NOT EXISTS abilities (" +
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
                        "    REFERENCES champions (`champion_id`)" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION)";

        String insertChampions =
                "INSERT IGNORE INTO champions (champion_id, name, health, he_level, attack_damage, ad_level, attack_speed, as_level, movement_speed, armor, ar_level, magic_resist, mr_level) VALUES" +
                        "('1', 'Aatrox', '580', '85', '68', '3.2', '0.651', '1.03', '345', '33', '3.8', '32.1', '1.25')," +
                        "('2', 'Ahri', '526', '92', '53.04', '3', '0.668', '1.02', '330', '20.88', '3.5', '30', '0.5')," +
                        "('3', 'Akali', '593', '90', '58.376', '3.2', '0.694', '1.031', '350', '31.38', '3.5', '32.1', '1.25')," +
                        "('4', 'Alistar', '613.36', '106', '61.1116', '3.62', '0.625', '1.02125', '330', '44', '3.5', '32.1', '1.25');";

        String insertAbilities =
                "INSERT IGNORE INTO abilities (ability_id, champion_id, ability_name, ability_ratio, ability_ratio_type, ability_type, lv1, lv2, lv3, lv4, lv5) VALUES" +
                        "('1', '1', 'Dark Flight', '1.1', 'Total AD', 'Physical', '10', '35', '60', '95', '120'),\n" +
                        "('2', '1', 'Blood Price', '0.75', 'Bonus AD', 'Physical', '50', '85', '120', '155', '190'),\n" +
                        "('3', '1', 'Blades of Torment', '0.7', 'Bonus AD', 'Physical', '75', '115', '155', '195', '235'),\n" +
                        "('4', '1', 'Massacre', '1', 'AP', 'Magic', '200', '300', '400', NULL, NULL),\n" +
                        "('5', '2', 'Orb of Deception', '0.35', 'AP', 'Magic', '40', '65', '90', '115', '140'),\n" +
                        "('6', '2', 'Fox-Fire', '0.3', 'AP', 'Magic', '40', '65', '90', '115', '140'),\n" +
                        "('7', '2', 'Charm', '0.6', 'AP', 'Magic', '60', '95', '130', '165', '200'),\n" +
                        "('8', '2', 'Spirit Rush', '0.25', 'AP', 'Magic', '70', '110', '150', NULL, NULL),\n" +
                        "('9', '3', 'Mark of the Assassin', '0.9', 'AP', 'Magic', '80', '125', '170', '215', '260'),\n" +
                        "('10', '3', 'Twilight Shroud', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL),\n" +
                        "('11', '3', 'Crescent Slash', '0.8', 'Bonus AD', 'Physical', '70', '100', '130', '160', '190'),\n" +
                        "('12', '3', 'Shadow Dance', '0.35', 'AP', 'Magic', '50', '100', '150', NULL, NULL),\n" +
                        "('13', '4', 'Pulverize', '0.5', 'AP', 'Magic', '60', '105', '150', '195', '240'),\n" +
                        "('14', '4', 'Headbutt', '0.7', 'AP', 'Magic', '55', '110', '165', '220', '275'),\n" +
                        "('15', '4', 'Trample', '0.04', 'AP', 'Magic', '10', '12.5', '15', '17.5', '20'),\n" +
                        "('16', '4', 'Unbreakable Will', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);";


        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {
            statement.execute(createChampionsTable);
            statement.execute(createAbilitiesTable);
            statement.executeUpdate(insertChampions);
            statement.executeUpdate(insertAbilities);

            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }
}
