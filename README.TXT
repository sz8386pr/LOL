Created by Scott Kim
Dec 11, 2017 for Java Final Project

need to run these codes on MYSQL to create the lol schema and user to use the program
    create database lol;
    create user 'user'@'localhost' identified by 'itecitec';
    grant select, insert, update, delete, create, drop, references on lol.* to 'user'@'localhost';

Open files for more information

-ChampionInfoSheet(Folder): Where saved champion info sheet would be saved at.
-src
    -com.company
        -Abilities: Abilities class file
        -Champion; Champion class file
        -ChampionInfoSheet: Calculate, display, and save champion info sheet
        -Items: Items class file
        -ItemSlotsToolTipRenderer: Custom ListCellRenderer for Item slot tooltips
        -LolDB: Lol DB access information and create DB if not available
        -LolProgram: Application
        -LolGUI
            -LolGUI: Main codes