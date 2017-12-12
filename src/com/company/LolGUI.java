package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import static com.company.LolDB.*;

public class LolGUI extends JFrame {
    private JPanel mainPanel;

    private JComboBox<String> ChampionName;
    private JComboBox<Integer> Level;
    private JComboBox<Integer> Ability1;
    private JComboBox<Integer> Ability2;
    private JComboBox<Integer> Ability3;
    private JComboBox<Integer> Ability4;

    private JLabel A1;
    private JLabel A2;
    private JLabel A3;
    private JLabel A4;

    private JComboBox<String> ItemSlot1;
    private JComboBox<String> ItemSlot2;
    private JComboBox<String> ItemSlot3;
    private JComboBox<String> ItemSlot4;
    private JComboBox<String> ItemSlot5;
    private JComboBox<String> ItemSlot6;

    private JTextPane ChampionInfoTextPane;

    private JButton generateButton;
    private JCheckBox saveCheckBox;

    private static final String SELECT_CHAMPION = "Select A Champion";
    private static final String NO_ITEM = "No Item";

    private Items items;
    private Champion champion;
    private Abilities ability;
    private ArrayList<Abilities> abilitiesList;
    private ArrayList<String> abilityNameList;
    private ChampionInfoSheet championInfoSheet;
    private ArrayList<Items> itemsList;
    private ArrayList<String> tooltips;
    private boolean saveToFile = false;

    LolGUI(){
        setTitle("League of Legend Champion Info Sheet");
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setSize(550, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setup();
    }

    //Initial setup phase
    private void setup(){
        loadChampions();
        loadItems();
        addActionListeners();

    }

    //
    //CHAMPIONS
    //

    //Load champions from champions table
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

    //Create Champion class object
    private void createChampion(String championName) {
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement champions = conn.prepareStatement("SELECT * FROM champions WHERE name = ?");
            champions.setString(1, championName);
            ResultSet rs = champions.executeQuery();

            //getString from the rs and apply to the String Array abilities.
            while (rs.next()) {
                String name = rs.getString("name");
                double health = rs.getDouble("health");
                double he_level = rs.getDouble("he_level");
                double attack_damage = rs.getDouble("attack_damage");
                double ad_level = rs.getDouble("ad_level");
                double attack_speed = rs.getDouble("attack_speed");
                double as_level = rs.getDouble("as_level");
                double movement_speed = rs.getDouble("movement_speed");
                double armor = rs.getDouble("armor");
                double ar_level = rs.getDouble("ar_level");
                double magic_resist = rs.getDouble("magic_resist");
                double mr_level = rs.getDouble("mr_level");
                int level = (int)Level.getSelectedItem();

                champion = new Champion(name, health, he_level, attack_damage, ad_level, attack_speed, as_level, movement_speed, armor, ar_level, magic_resist, mr_level, level);
            }
            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }

    //Setup GUI for the selected champion
    private void setupChampion(String championName) {

        //Add champion and skill levels once user selects a champion from the Champion Name JComboBox
        if (Level.getItemCount() == 0) {
            loadChampionLevels();
        }
        if (Ability1.getItemCount() == 0) {
            abilityLevels();
        }

        setAbilityName(championName);
        createChampion(championName);

        //Create 4 Ability class objects and add to the abilitiesList ArrayList
        abilitiesList = new ArrayList<>();
        for (int i = 0; abilityNameList.size() > i; i++) {
            createAbility(abilityNameList.get(i));
            abilitiesList.add(i, ability);
        }

    }

    //
    //LEVELS
    //

    //Level ComboBox setup
    private void loadChampionLevels() {
        for (int x = 1 ; x <= 18 ; x++ ) {
            Level.addItem(x);
        }
    }

    //Set/update champion & ability level values
    private void setLevels(){
        //Set champion level
        if (champion != null){
            champion.setLevel((int)Level.getSelectedItem());
        }

        //Set ability levels
        if (abilitiesList != null) {
            abilitiesList.get(0).setLevel((int) Ability1.getSelectedItem());
            abilitiesList.get(1).setLevel((int) Ability2.getSelectedItem());
            abilitiesList.get(2).setLevel((int) Ability3.getSelectedItem());
            abilitiesList.get(3).setLevel((int) Ability4.getSelectedItem());
        }
    }


    //
    //ABILITIES
    //

    //Setup Ability ComboBoxes
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
            abilityNameList = new ArrayList<>();

            //get the ability names from the table and add them to abilityNameList ArrayList
            while (rs.next()) {
                abilities[i] = rs.getString("ability_name");
                abilityNameList.add(abilities[i]);
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

    //Create Ability Class object
    private void createAbility(String abilityName) {
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement abilities = conn.prepareStatement("SELECT * FROM abilities WHERE ability_name = ?");
            abilities.setString(1, abilityName);
            ResultSet rs = abilities.executeQuery();

            //getString from the rs and apply to the String Array abilities.
            while (rs.next()) {
                String ability_name = rs.getString("ability_name");
                double ability_ratio = rs.getDouble("ability_ratio");
                String ability_ratio_type = rs.getString("ability_ratio_type");
                String ability_type = rs.getString("ability_type");
                double lv1 = rs.getDouble("lv1");
                double lv2 = rs.getDouble("lv2");
                double lv3 = rs.getDouble("lv3");
                double lv4 = rs.getDouble("lv4");
                double lv5 = rs.getDouble("lv5");

                ability = new Abilities(ability_name, ability_ratio, ability_ratio_type, ability_type, lv1, lv2, lv3, lv4, lv5);
            }
            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }


    //
    //ITEMS
    //

    //Create Items class object
    private void createItems(String itemName){
        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement itemPS = conn.prepareStatement("SELECT * FROM items WHERE item_name = ?");
            itemPS.setString(1, itemName);
            ResultSet rs = itemPS.executeQuery();

            //getString from the rs and apply to the String Array abilities.
            while (rs.next()) {
                String item_name = rs.getString("item_name");
                String bonus_type1 = rs.getString("bonus_type1");
                double bonus_value1 = rs.getDouble("bonus_value1");
                String bonus_type2 = rs.getString("bonus_type2");
                double bonus_value2 = rs.getDouble("bonus_value2");
                String bonus_type3 = rs.getString("bonus_type3");
                double bonus_value3 = rs.getDouble("bonus_value3");

                items = new Items(item_name, bonus_type1, bonus_value1, bonus_type2, bonus_value2, bonus_type3, bonus_value3);
            }
            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
    }

    //Setup item slots
    private void loadItems(){

        //Initial ItemSlot items
        ItemSlot1.addItem(NO_ITEM);
        ItemSlot2.addItem(NO_ITEM);
        ItemSlot3.addItem(NO_ITEM);
        ItemSlot4.addItem(NO_ITEM);
        ItemSlot5.addItem(NO_ITEM);
        ItemSlot6.addItem(NO_ITEM);

        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement items = conn.prepareStatement("SELECT * FROM items");

            ResultSet rs = items.executeQuery();



            while (rs.next()) {
                String s = rs.getString("item_name");
                ItemSlot1.addItem(s);
                ItemSlot2.addItem(s);
                ItemSlot3.addItem(s);
                ItemSlot4.addItem(s);
                ItemSlot5.addItem(s);
                ItemSlot6.addItem(s);

            }

            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }

        //Setup item tooltips
        setupItemSlotTooltip();

    }

    //Setup item slot tooltips
    private void setupItemSlotTooltip(){

        //Adding blank tooltips for the no item slot
        tooltips = new ArrayList<>();
        tooltips.add("");

        try (Connection conn = DriverManager.getConnection(db_url, user, password);
             Statement statement = conn.createStatement()) {

            PreparedStatement itemToolTip = conn.prepareStatement("SELECT * FROM items");

            ResultSet rs = itemToolTip.executeQuery();

            //Get bonus type and value data
            while (rs.next()) {
                String bonus_type1 = rs.getString("bonus_type1");
                double bonus_value1 = rs.getDouble("bonus_value1");
                String bonus_type2 = rs.getString("bonus_type2");
                double bonus_value2 = rs.getDouble("bonus_value2");
                String bonus_type3 = rs.getString("bonus_type3");
                double bonus_value3 = rs.getDouble("bonus_value3");

                //Create string for the Item tool tips
                StringBuilder toolTip = new StringBuilder();

                //If item value is less than 1, convert and display the value to a percentage value instead
                if (bonus_value1 > 1) {
                    toolTip.append(String.format("<HTML>%s: %.0f<BR>", bonus_type1, bonus_value1));
                }
                else {
                    toolTip.append(String.format("<HTML>%s: %.0f%%<BR>", bonus_type1, bonus_value1*100));
                }
                //Sometimes items only have a single bonus, append to the toolTip only if item has more than 2 bonuses
                if (bonus_type2!=null){
                    if (bonus_value2 > 1) {
                        toolTip.append(String.format("%s: %.0f<BR>", bonus_type2, bonus_value2));
                    }
                    else {
                        toolTip.append(String.format("%s: %.0f%%<BR>", bonus_type2, bonus_value2*100));
                    }
                }
                //The same idea as for the bonus_type2. Only handful of items have a third bonus
                if (bonus_type3!=null){
                    if (bonus_value3 > 1) {
                        toolTip.append(String.format("%s: %.0f<BR>", bonus_type3, bonus_value3));
                    }
                    else {
                        toolTip.append(String.format("%s: %.0f%%<BR>", bonus_type3, bonus_value3*100));
                    }
                }

                //Once tooltip StringBuilder object has been created, add it towards tooltips ArrayList
                tooltips.add(toolTip.toString());
            }

            statement.close();
            conn.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }

        //Create ItemSlotsToolTipRenderer class object
        ItemSlotsToolTipRenderer renderer = new ItemSlotsToolTipRenderer();

        //setRenderer for the item slots
        ItemSlot1.setRenderer(renderer);
        ItemSlot2.setRenderer(renderer);
        ItemSlot3.setRenderer(renderer);
        ItemSlot4.setRenderer(renderer);
        ItemSlot5.setRenderer(renderer);
        ItemSlot6.setRenderer(renderer);

        //Set tooltip information for the renderer
        renderer.setTooltips(tooltips);

    }

    //Create Items class object and store them into itemsList ArrayList
    private void setItems(){
        itemsList = new ArrayList<>();

        //Only create Items class object and store them into the list if an item has been selected
        if (ItemSlot1.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot1.getSelectedItem());
            itemsList.add(items);
        }
        if (ItemSlot2.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot2.getSelectedItem());
            itemsList.add(items);
        }
        if (ItemSlot3.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot3.getSelectedItem());
            itemsList.add(items);
        }
        if (ItemSlot4.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot4.getSelectedItem());
            itemsList.add(items);
        }
        if (ItemSlot5.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot5.getSelectedItem());
            itemsList.add(items);
        }
        if (ItemSlot6.getSelectedItem() != NO_ITEM) {
            createItems((String)ItemSlot6.getSelectedItem());
            itemsList.add(items);
        }
    }

    //
    //CHAMPION INFO SHEET
    //

    private void generateInfoSheet(){
        championInfoSheet = new ChampionInfoSheet();
        ChampionInfoTextPane.setText(championInfoSheet.generateInfoSheet(champion, abilitiesList, itemsList, saveToFile));
        if (saveToFile) {
            String saveMessage = championInfoSheet.saveToFile();
            JOptionPane.showMessageDialog(LolGUI.this, saveMessage);
        }
    }



    //
    //ACTION LISTENERS
    //

    private void addActionListeners() {
        //When user select a champion from the ChampionName JComboBox, ability names on GUI will change accordingly.
        ChampionName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sets default value for the ChampionName JComboBox to SELECT_CHAMPION and removes it once user selects a champion and title is no longer needed.
                if (ChampionName.getItemAt(0).equals(SELECT_CHAMPION)) {
                    ChampionName.removeItemAt(0);
                }

                String championName = (String) ChampionName.getSelectedItem();
                setupChampion(championName);
            }
        });

        // Generate button checks if user have used the correct number of skill points--the same as the champion level. ie. Champion level 6 should be able to use the total skill points of 6.
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //User must select a champion before generating info sheet
                if (ChampionName.getSelectedItem() != SELECT_CHAMPION) {
                    //Update/set champion and ability levels before generating info sheet
                    setLevels();
                    //Update/set items before generating info sheet
                    setItems();

                    //Variables to check if user have used correct number of skill points/item slots that could be used
                    int championLevel = (Integer) Level.getSelectedItem();
                    int abilityPoints = (Integer) Ability1.getSelectedItem() + (Integer) Ability2.getSelectedItem() + (Integer) Ability3.getSelectedItem() + (Integer) Ability4.getSelectedItem();
                    boolean allAbilityPointsUsed = true;
                    boolean allItemSlotUsed = true;

                    //If an item slot is not equipped with an item, item count is decreased by 1
                    int itemCount = 6;

                    if (ItemSlot1.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }
                    if (ItemSlot2.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }
                    if (ItemSlot3.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }
                    if (ItemSlot4.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }
                    if (ItemSlot5.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }
                    if (ItemSlot6.getSelectedItem() == NO_ITEM) {
                        itemCount--;
                    }

                    //If user have not fully used skill points, confirm if user still wish to proceed
                    if (championLevel > abilityPoints) {
                        int skillsNotUsed = JOptionPane.showConfirmDialog(LolGUI.this, "You haven't assigned all the skill points.\nWould you still like to generate the info sheet?", "WARNING", JOptionPane.YES_NO_OPTION);
                        if (skillsNotUsed == JOptionPane.NO_OPTION) {
                            allAbilityPointsUsed = false;
                        }
                    }
                    //If user have used too much ability points, info sheet won't be generated
                    else if (championLevel < abilityPoints) {
                            JOptionPane.showMessageDialog(LolGUI.this, "You have assigned too many skill points!");
                            allAbilityPointsUsed = false;
                    }

                    //Won't trigger if user have decided not to generate info sheet in the previous if statement
                    if ((itemCount < 6) && allAbilityPointsUsed) {
                        int itemNotSelected = JOptionPane.showConfirmDialog(LolGUI.this, "You haven't equipped the items in all of your available slots.\nWould you still like to generate the info sheet?", "WARNING", JOptionPane.YES_NO_OPTION);
                        if (itemNotSelected == JOptionPane.NO_OPTION) {
                        allItemSlotUsed = false;
                        }
                    }

                    //If all ability points and all item slots have been used or user have decided to continue anyway, generate the info sheet
                    if (allAbilityPointsUsed && allItemSlotUsed) {
                        generateInfoSheet();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(LolGUI.this, "You have not selected a champion!");
                }
            }
        });

        //Checks if user have selected to save to a file or not
        saveCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile = saveCheckBox.isSelected();
            }
        });
    }
}
