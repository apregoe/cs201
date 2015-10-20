package sorryclient;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by albertoprego on 10/13/15.
 */
public class CardDialog extends JDialog{
    private JTextArea text = new JTextArea();
    private JTextArea valueText = new JTextArea();
    private Font font;
    public final String stringValue;
    public CardDialog(String content,String name, int value){
        super();
        if(value == 0){
            stringValue = "Sorry!";
        }else{
            stringValue = Integer.toString(value);
        }
        try{
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));
        }
        catch(IOException|FontFormatException ffe){
            System.out.print(ffe.toString() + " in NumPlayerSelector constructor\n");
            ffe.printStackTrace();
        }
        setSize(200,350);
        setMaximumSize(new Dimension(200, 350));
        this.setMinimumSize(new Dimension(200,350));
        this.setTitle(name);
        ImageIcon img = new ImageIcon("images/cards/card_beige.png");

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = this.getWidth();
                int y = this.getHeight();
                g.drawImage(img.getImage(), 0, 0, x, y, this);
            }
        };

        text.setText(content);
        text.setSize(new Dimension(180, 325));
        text.setEditable(false);
        text.setFont(font.deriveFont(Font.PLAIN, 15f));
        text.setOpaque(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        if(stringValue.equals("Sorry!")){
            valueText.setText(" " + stringValue);

        }else{
            valueText.setText("        " + stringValue);
        }
        valueText.setEditable(false);
        valueText.setSize(new Dimension(180, 325));
        valueText.setFont(font.deriveFont(Font.PLAIN, 35f));
        valueText.setOpaque(false);
        valueText.setLineWrap(true);
        valueText.setWrapStyleWord(true);

        panel.add(valueText);
        panel.add(text);
        this.add(panel);
        this.setLocationRelativeTo(getParent());

        JButton closeButton = new JButton();
        closeButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    System.out.println("close");
                    getMe().setVisible(false);
                }
            }
        });
    }

    public CardDialog getMe(){return this;}
}
