package com.company;

import javax.swing.*;
import java.sql.*;

import static com.company.LolDB.*;

public class LolGUI extends JFrame {
    private JPanel mainPanel;

    private JComboBox<String> ChampionName;
    private JComboBox<Integer> Level;
    private JComboBox<Integer> Ability1;
    private JComboBox<Integer> Ability2;
    private JComboBox<Integer> Ability3;
    private JComboBox<Integer> Ability4;
    private JTextPane ChampionInfo;
    private JButton generateButton;

    private JLabel A1;
    private JLabel A2;
    private JLabel A3;
    private JLabel A4;

    protected LolGUI(){
        setTitle("Rubik Cube Solver Times");
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setup();

    }

    private void setup(){
        loadChampions();
        championLevels();
        abilityLevels();
        setAbilityName("Aatrox"); //Work in testing purposes only. Method should work with the action listener with ChampionName JComboBox
    }

    private void loadChampions() {

        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement champions = conn.prepareStatement("SELECT name FROM champions");

            ResultSet rs = champions.executeQuery();

            while (rs.next()) {
                String s = rs.getString("name");
                ChampionName.addItem(s);
            }

            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }

    private void championLevels() {
        for (int x = 1 ; x <= 18 ; x++ ) {
                Level.addItem(x);
        }
    }

    private void abilityLevels(){
        //TODO: load the champion ability data and add max number of skills accordingly.

        for (int x = 1 ; x <= 5 ; x++ ) {
            Ability1.addItem(x);
            Ability2.addItem(x);
            Ability3.addItem(x);
        }
        for (int x = 1 ; x <= 3 ; x++ ) {
            Ability4.addItem(x);
        }
    }

    private void setAbilityName(String championName){
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement champions = conn.prepareStatement("SELECT ability_name FROM abilities a JOIN champions c ON c.champion_id = a.champion_id WHERE c.name = ?");
            champions.setString(1, championName);
            ResultSet rs = champions.executeQuery();

            String[] abilities = new String[4];
            int i = 0;

            while (rs.next()) {
                abilities[i] = rs.getString("ability_name");
                i++;
            }

            A1.setText(abilities[0]);
            A2.setText(abilities[1]);
            A3.setText(abilities[2]);
            A4.setText(abilities[3]);

            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }
}
