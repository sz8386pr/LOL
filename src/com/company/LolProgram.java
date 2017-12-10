//need to run these codes on MYSQL to create the lol schema and user to utilize the db
//create database lol;
//create user 'user'@'localhost' identified by 'itecitec';
//grant select, insert, update, delete, create, drop, references on lol.* to 'user'@'localhost';

package com.company;

import static com.company.LolDB.*;
import java.sql.*;

public class LolProgram {
    public static void main(String[] args)  throws SQLException{
        DBSetup();
        LolGUI gui = new LolGUI();
    }


}
