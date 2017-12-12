//DB access info and DB creation

package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LolDB {

    static String db_url = "jdbc:mysql://localhost:3306/lol?autoReconnect=true&useSSL=false";
    static String user = "user"; //Should be set as environment variable for security purposes, but used a fixed value for this purposes
    static String password = "itecitec"; //Should be set as environment variable for security purposes, but used a fixed value for this purposes

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
                        "  PRIMARY KEY (`ability_id`)," +
                        "  INDEX `chapion_id_idx` (`champion_id` ASC)," +
                        "  CONSTRAINT `chapion_id`" +
                        "    FOREIGN KEY (`champion_id`)" +
                        "    REFERENCES champions (`champion_id`)" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION )";

        String createItemsTable =
                "CREATE TABLE IF NOT EXISTS items (" +
                        "  `item_id` INT(11) NOT NULL AUTO_INCREMENT," +
                        "  `item_name` VARCHAR(45) NULL DEFAULT NULL," +
                        "  `bonus_type1` VARCHAR(45) NULL DEFAULT NULL," +
                        "  `bonus_value1` DOUBLE NULL DEFAULT NULL," +
                        "  `bonus_type2` VARCHAR(45) NULL DEFAULT NULL," +
                        "  `bonus_value2` DOUBLE NULL DEFAULT NULL," +
                        "  `bonus_type3` VARCHAR(45) NULL DEFAULT NULL," +
                        "  `bonus_value3` DOUBLE NULL DEFAULT NULL," +
                        "  PRIMARY KEY (`item_id`));\n";



        String insertChampions =
                "INSERT IGNORE INTO champions (champion_id, name, health, he_level, attack_damage, ad_level, attack_speed, as_level, movement_speed, armor, ar_level, magic_resist, mr_level) VALUES" +
                        "('1', 'Aatrox', '580', '85', '68', '3.2', '0.651', '1.03', '345', '33', '3.8', '32.1', '1.25')," +
                        "('2', 'Ahri', '526', '92', '53.04', '3', '0.668', '1.02', '330', '20.88', '3.5', '30', '0.5')," +
                        "('3', 'Akali', '593', '90', '58.376', '3.2', '0.694', '1.031', '350', '31.38', '3.5', '32.1', '1.25')," +
                        "('4', 'Alistar', '613.36', '106', '61.1116', '3.62', '0.625', '1.02125', '330', '44', '3.5', '32.1', '1.25')," +
                        "('5', 'Amumu', '613.12', '84', '53.38', '3.8', '0.638', '1.0218', '335', '33', '3.8', '32.1', '1.25')," +
                        "('6', 'Anivia', '480', '82', '51.376', '3.2', '0.625', '1.0168', '325', '21.22', '4', '30', '0.5')," +
                        "('7', 'Annie', '524', '88', '50.41', '2.625', '0.579', '1.0136', '335', '19.22', '4', '30', '0.5')," +
                        "('8', 'Ashe', '527.72', '79', '65', '2.26', '0.658', '1.0333', '325', '30', '3.4', '30', '0.5');";

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
                        "('16', '4', 'Unbreakable Will', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL),\n" +
                        "('17', '5', 'Bandage Toss', '0.7', 'AP', 'Magic', '80', '130', '180', '230', '280'),\n" +
                        "('18', '5', 'Despair', '0.01', 'AP', 'Magic', '10', '15', '20', '25', '30'),\n" +
                        "('19', '5', 'Tantrum', '0.50', 'AP', 'Magic', '75', '100', '125', '150', '175'),\n" +
                        "('20', '5', 'Curse of the Sad Mummy', '0.8', 'AP', 'Magic', '150', '250', '350', NULL, NULL),\n" +
                        "('21', '6', 'Flash Frost', '0.8', 'AP', 'Magic', '120', '170', '220', '270', '320'),\n" +
                        "('22', '6', 'Crystallize', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),\n" +
                        "('23', '6', 'Frostbite', '0.5', 'AP', 'Magic', '50', '75', '100', '125', '150'),\n" +
                        "('24', '6', 'Glacial Storm', '0.13', 'AP', 'Magic', '40', '60', '80', NULL, NULL),\n" +
                        "('25', '7', 'Disintegrate', '0.8', 'AP', 'Magic', '80', '115', '150', '185', '220'),\n" +
                        "('26', '7', 'Incinerate', '0.85', 'AP', 'Magic', '70', '115', '160', '205', '250'),\n" +
                        "('27', '7', 'Molten Shield', '0.2', 'AP', 'Magic', '20', '30', '40', '50', '60'),\n" +
                        "('28', '7', 'Summon: Tibbers', '0.65', 'AP', 'Magic', '150', '275', '400', NULL, NULL),\n" +
                        "('29', '8', 'Ranger''s Focus', '1', 'Total AD', 'Physical', '0', '0', '0', '0', '0'),\n" +
                        "('30', '8', 'Volley', '1', 'Total AD', 'Physical', '20', '35', '50', '65', '80'),\n" +
                        "('31', '8', 'Hawkshot', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),\n" +
                        "('32', '8', 'Enchanted Crystal Arrow', '1.0', 'AP', 'Magic', '200', '400', '600', NULL, NULL);";

        String insertItems =
                "INSERT IGNORE INTO items (item_id, item_name, bonus_type1, bonus_value1, bonus_type2, bonus_value2, bonus_type3, bonus_value3) VALUES" +
                "('1', 'Adaptive Helm', 'Health', 350, 'Magic Resist', 55, NULL, NULL),"+
                "('2', 'Aegis of the Legion', 'Armor', 30, 'Magic Resist', 30, NULL, NULL),"+
                "('3', 'Dead Man''s Plate', 'Health', 425, 'Armor', 60, NULL, NULL),"+
                "('4', 'Forgefire Cape', 'Health', 625, 'Armor', 90, NULL, NULL),"+
                "('5', 'Frozen Mallet', 'Health', 700, 'Attack Damage', 30, NULL, NULL),"+
                "('6', 'Infernal Mask', 'Health', 550, 'Magic Resist', 90, NULL, NULL),"+
                "('7', 'Lich Bane', 'Ability Power', 80, 'Movement Speed', 0.07, NULL, NULL),"+
                "('8', 'Trinity Force', 'Health', 250, 'Attack Damage', 25, 'Attack Speed', 0.40),"+
                "('9', 'Wit''s End', 'Attack Speed', 0.40, 'Magic Resist', 40, NULL, NULL),"+
                "('10', 'Zhonya''s Hourglass', 'Ability Power', 70, 'Armor', 45, NULL, NULL),"+
                "('11', 'Statikk Shiv', 'Attack Speed', 0.35, 'Movement Speed', 0.05, NULL, NULL),"+
                "('12', 'Liandry''s Torment', 'Ability Power', 80, 'Health', 300, NULL, NULL),"+
                "('13', 'Hextech Protobelt-01', 'Health', 300, 'Ability Power', 60, NULL, NULL),"+
                "('14', 'Ninja Tabi', 'Armor', 30, 'Movement Speed', 45, NULL, NULL),"+
                "('15', 'Phantom Dancer', 'Attack Speed', 0.45, 'Movement Speed', 0.05, NULL, NULL),"+
                "('16', 'Boots of Swiftness', 'Movement Speed', 55, NULL, NULL, NULL, NULL),"+
                "('17', 'Berserker''s Greaves', 'Attack Speed', 0.35, 'Movement Speed', 45, NULL, NULL),"+
                "('18', 'B. F. Sword', 'Attack Damage', 40, NULL, NULL, NULL, NULL),"+
                "('19', 'Hextech Gunblade', 'Attack Damage', 40, 'Ability Power', 80, NULL, NULL),"+
                "('20', 'Thorn Mail', 'Health', 250, 'Armor', 80, NULL, NULL),"+
                "('21', 'Randuin''s Omen', 'Health', 400, 'Armor', 60, NULL, NULL),"+
                "('22', 'Guardian Angel', 'Attack Damage', 40, 'Armor', 30, NULL, NULL),"+
                "('23', 'Maw of Malmortius', 'Attack Damage', 50, 'Magic Resist', 45, NULL, NULL),"+
                "('24', 'Rylai''s Crystal Scepter', 'Health', 300, 'Ability Power', 75, NULL, NULL);";


        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {
            statement.execute(createChampionsTable);
            statement.execute(createAbilitiesTable);
            statement.execute(createItemsTable);
            statement.executeUpdate(insertChampions);
            statement.executeUpdate(insertAbilities);
            statement.executeUpdate(insertItems);


            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }
}
