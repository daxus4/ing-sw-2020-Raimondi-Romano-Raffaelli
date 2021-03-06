package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to show the god card and the god description once clicked
 * */
public class GodIconDialog extends GameDialog implements ActionListener {

    private final ImageContainer imageContainer = new ImageContainer();
    private Map<String,String> godProfile;

    /**
     * @param frame principal frame
     * @param godName god name
     * In each player frame there is a icon with the card of god and with a click on it, it show the god with description
     */
    public GodIconDialog(JFrame frame,String godName){
        super(frame,"GOD PROFILE");
        Font font = new Font("Impatto", Font.PLAIN, 33);
        Font font1 = new Font("Impatto", Font.PLAIN, 10);
        JLabel labelGround = new JLabel("");
        Image imageGround = new ImageIcon(this.getClass().getResource("/Odyssey_UI_Backdrop.png")).getImage().getScaledInstance(520,315,Image.SCALE_DEFAULT);
        labelGround.setIcon(new ImageIcon(imageGround));
        JLabel labelGod = new JLabel("");
        Image imageGod = imageContainer.getGodimage(godName).getScaledInstance(150,250,Image.SCALE_DEFAULT);
        labelGod.setIcon(new ImageIcon(imageGod));
        labelGod.setBounds(2,20,150,250);
        labelGround.add(labelGod);
        createMap();
        JLabel labelGodName = new JLabel(godName.toUpperCase());
        labelGodName.setBounds(155,10,600,50);
        labelGodName.setFont(font);
        labelGodName.setForeground(Color.WHITE);
        String power = getPower(godName);
        String[] powerSplit = power.split(",");
        String firstPart = powerSplit[0];
        String secondPart = powerSplit[1];
        JLabel labelFirst = new JLabel(firstPart);
        JLabel labelSecond = new JLabel(secondPart);
        labelFirst.setBounds(155,45,500,50);
        labelSecond.setBounds(155,60,500,50);
        labelSecond.setFont(font1);
        labelSecond.setForeground(Color.WHITE);
        labelFirst.setForeground(Color.WHITE);
        labelFirst.setFont(font1);
        Image imageClose = imageContainer.getCloseImage().getScaledInstance(70,70,Image.SCALE_DEFAULT);
        ButtonCircle buttonClose = new ButtonCircle(new ImageIcon(imageClose),Color.RED,true,this);
        buttonClose.setBounds(240,200,80,80);
        labelGround.add(buttonClose);
        labelGround.add(labelSecond);
        labelGround.add(labelFirst);
        labelGround.add(labelGodName);
        add(labelGround);
    }

    private void createMap(){
        godProfile = new HashMap<>();
        godProfile.put("Apollo","Your Worker may move into an opponent Worker’s, space by forcing their Worker to the space yours just vacated.");
        godProfile.put("Artemis","Your Worker may move one additional time, but not back to its initial space.");
        godProfile.put("Athena","If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
        godProfile.put("Atlas","Your Worker may build, a dome at any level.");
        godProfile.put("Demeter","Your Worker may build one additional time, but not on the same space.");
        godProfile.put("Hephaestus","Your Worker may build one additional, block (not dome) on top of your first block.");
        godProfile.put("Minotaur","Our Worker may move into an opponent Worker’s space if their Worker, can be forced one space straight backwards to an unoccupied space.");
        godProfile.put("Pan","You also win if your Worker, moves down two or more levels.");
        godProfile.put("Prometheus","If your Worker does not move up, it may build both before and after moving.");
        godProfile.put("Zeus","Your worker may, build a block under itself.");
        godProfile.put("Hestia","Your worker may build one additional time, but this cannot be on a perimeter space.");
        godProfile.put("Triton","Each time your worker moves into a, perimeter space it may immediately move again.");
        godProfile.put("Charon","Before your worker moves you may force a neighboring opponent worker, to the space directly on the other side of your worker if that space is unoccupied.");
        godProfile.put("Hera","An opponent cannot win, by moving into a perimeter space.");
    }

    private String getPower(String nameGod){
        return godProfile.get(nameGod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}