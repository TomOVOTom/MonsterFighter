package monster_fighting_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonsterFighter extends JFrame {
    private int playerHealth = 100;
    private int monsterHealth = 100;
    private JLabel playerHealthLabel;
    private JLabel monsterHealthLabel;
    private JLabel playerLabel;
    private JLabel monsterLabel;
    private JLabel effectLabel;
    private JButton attackButton;
    private Timer effectTimer;
    private int effectFrame = 0;
    private String[] currentEffectImages;

    public MonsterFighter() {
        setTitle("Monster Fighter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        playerHealthLabel = new JLabel("Player Health: " + playerHealth);
        playerHealthLabel.setBounds(50, 20, 200, 30);
        monsterHealthLabel = new JLabel("Monster Health: " + monsterHealth);
        monsterHealthLabel.setBounds(550, 20, 200, 30);

        playerLabel = new JLabel(loadImageIcon("player.png"));
        playerLabel.setBounds(50, 100, 100, 100);
        monsterLabel = new JLabel(loadImageIcon("monster.png"));
        monsterLabel.setBounds(550, 100, 100, 100);

        effectLabel = new JLabel("", SwingConstants.CENTER);
        effectLabel.setBounds(300, 100, 200, 200);

        attackButton = new JButton("Attack");
        attackButton.setBounds(350, 400, 100, 50);

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack();
            }
        });

        add(playerHealthLabel);
        add(monsterHealthLabel);
        add(playerLabel);
        add(monsterLabel);
        add(effectLabel);
        add(attackButton);
    }

    private ImageIcon loadImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
//            System.out.println("Loaded image: " + path);
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void attack() {
        // 玩家攻击怪兽
        int playerAttack = (int) (Math.random() * 10);
        monsterHealth -= playerAttack;
        // 怪兽攻击玩家
        int monsterAttack = (int) (Math.random() * 10);
        playerHealth -= monsterAttack;

        playerHealthLabel.setText("Player Health: " + playerHealth);
        monsterHealthLabel.setText("Monster Health: " + monsterHealth);

        // 显示攻击特效
        String effectText = "Player attacks Monster for " + playerAttack + " damage! Monster attacks Player for " + monsterAttack + " damage!";
        effectLabel.setText(effectText);

        // 随机选择火焰或冰霜特效
        currentEffectImages = Math.random() > 0.5 ? new String[]{
                "/monster_fighting_game/resources/fire/fire(1).png",
                "/monster_fighting_game/resources/fire/fire(2).png",
                "/monster_fighting_game/resources/fire/fire(3).png",
                "/monster_fighting_game/resources/fire/fire(4).png",
                "/monster_fighting_game/resources/fire/fire(5).png",
                "/monster_fighting_game/resources/fire/fire(6).png",
                "/monster_fighting_game/resources/fire/fire(7).png",
                "/monster_fighting_game/resources/fire/fire(8).png",
                "/monster_fighting_game/resources/fire/fire(9).png",
                "/monster_fighting_game/resources/fire/fire(10).png",
                "/monster_fighting_game/resources/fire/fire(11).png",
                "/monster_fighting_game/resources/fire/fire(12).png",
                "/monster_fighting_game/resources/fire/fire(13).png",
                "/monster_fighting_game/resources/fire/fire(14).png",
                "/monster_fighting_game/resources/fire/fire(15).png",
                "/monster_fighting_game/resources/fire/fire(16).png"
        } : new String[]{
                "/monster_fighting_game/resources/ice/ice(1).png",
                "/monster_fighting_game/resources/ice/ice(2).png",
                "/monster_fighting_game/resources/ice/ice(3).png",
                "/monster_fighting_game/resources/ice/ice(4).png",
                "/monster_fighting_game/resources/ice/ice(5).png",
                "/monster_fighting_game/resources/ice/ice(6).png",
                "/monster_fighting_game/resources/ice/ice(7).png",
                "/monster_fighting_game/resources/ice/ice(8).png",
                "/monster_fighting_game/resources/ice/ice(9).png",
                "/monster_fighting_game/resources/ice/ice(10).png",
                "/monster_fighting_game/resources/ice/ice(11).png",
                "/monster_fighting_game/resources/ice/ice(12).png",
                "/monster_fighting_game/resources/ice/ice(13).png",
                "/monster_fighting_game/resources/ice/ice(14).png",
                "/monster_fighting_game/resources/ice/ice(15).png",
                "/monster_fighting_game/resources/ice/ice(16).png"
        };
        effectFrame = 0;
        showEffect();

        if (playerHealth <= 0) {
            JOptionPane.showMessageDialog(this, "You lost!");
            resetGame();
        } else if (monsterHealth <= 0) {
            JOptionPane.showMessageDialog(this, "You won!");
            resetGame();
        }
    }

    private void showEffect() {
        if (effectTimer != null && effectTimer.isRunning()) {
            effectTimer.stop();
        }

        effectTimer = new Timer(100, new ActionListener() { // 调整时间间隔以适应更多帧
            @Override
            public void actionPerformed(ActionEvent e) {
                if (effectFrame < currentEffectImages.length) {
                    ImageIcon icon = loadImageIcon(currentEffectImages[effectFrame]);
                    if (icon != null) {
                        Image img = icon.getImage();
                        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        effectLabel.setIcon(new ImageIcon(newImg));
                    }
                    effectFrame++;
                } else {
                    effectLabel.setIcon(null);
                    effectTimer.stop();
                }
            }
        });
        effectTimer.start();
    }

    private void resetGame() {
        playerHealth = 100;
        monsterHealth = 100;
        playerHealthLabel.setText("Player Health: " + playerHealth);
        monsterHealthLabel.setText("Monster Health: " + monsterHealth);
        effectLabel.setText("");
        effectLabel.setIcon(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MonsterFighter().setVisible(true);
            }
        });
    }
}