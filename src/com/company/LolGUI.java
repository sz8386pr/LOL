package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private static final String SELECT_CHAMPION = "Select A Champion";

    protected LolGUI(){
        setTitle("League of Legend Champion Info Sheet");
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setup();

        //When user select a champion from the ChampionName JComboBox, ability names on GUI will change accordingly.
        ChampionName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sets default value for the ChampionName JComboBox to SELECT_CHAMPION and removes it once user selects a champion and title is no longer needed.
                if (ChampionName.getItemAt(0).equals(SELECT_CHAMPION)) {
                    ChampionName.removeItemAt(0);
                }

                String championName = (String)ChampionName.getSelectedItem();
                setAbilityName(championName);
            }
        });

    }

    //Initial setup phase
    private void setup(){
        loadChampions();
        championLevels();
        abilityLevels();
    }

    //
    private void loadChampions() {

        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement champions = conn.prepareStatement("SELECT name FROM champions");

            ResultSet rs = champions.executeQuery();

            ChampionName.addItem(SELECT_CHAMPION);

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

        for (int x = 0 ; x <= 5 ; x++ ) {
            Ability1.addItem(x);
            Ability2.addItem(x);
            Ability3.addItem(x);
        }
        for (int x = 0 ; x <= 3 ; x++ ) {
            Ability4.addItem(x);
        }
    }

    // Retrieves champion's ability names for the GUI interface
    private void setAbilityName(String championName){
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement champions = conn.prepareStatement("SELECT ability_name FROM abilities a JOIN champions c ON c.champion_id = a.champion_id WHERE c.name = ?");
            champions.setString(1, championName);
            ResultSet rs = champions.executeQuery();

            //getString from the rs and apply to the String Array abilities.
            String[] abilities = new String[4];
            int i = 0;

            while (rs.next()) {
                abilities[i] = rs.getString("ability_name");
                i++;
            }

            //Set texts for each ability accordingly
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
