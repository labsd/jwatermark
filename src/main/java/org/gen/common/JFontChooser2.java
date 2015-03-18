package org.gen.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JFontChooser2 extends JPanel {

    private String current_fontName = "Times New Roman";
    private int current_fontStyle = Font.PLAIN;
    private int current_fontSize = 9;
    private Color current_color = Color.BLACK;
    private Component parent;
    private JDialog dialog;
    private ChooserFont myfont;
    private JLabel lblFont;
    private JLabel lblStyle;
    private JLabel lblSize;
    private JLabel lblColor;
    private JTextField txtFont;
    private JTextField txtStyle;
    private JTextField txtSize;
    private JList lstFont;
    private JList lstStyle;
    private JList lstSize;
    private JComboBox cbColor;
    private JButton ok, cancel;
    private JScrollPane spFont;
    private JScrollPane spSize;
    private JLabel lblShow;
    private JPanel showPan;
    private Map sizeMap;
    private Map colorMap;

    public JFontChooser2() {
        init();
    }

    private void init() {
        lblFont = new JLabel("Font:");
        lblStyle = new JLabel("Style:");
        lblSize = new JLabel("Size:");
        lblColor = new JLabel("Color:");
        lblShow = new JLabel("Font Selector");
        txtFont = new JTextField("Times New Roman");
        txtStyle = new JTextField("Normal");
        txtSize = new JTextField("9");
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        lstFont = new JList(fontNames);
        
        lstStyle = new JList(new String[]{"Normal", "Italic", "Bold", "Bold Italic"});
        
        String[] sizeStr = new String[]{
            "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72", "96", "128", "160"
        };
        int sizeVal[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 96, 128, 160
        };
        sizeMap = new HashMap();
        for (int i = 0; i < sizeStr.length; ++i) {
            sizeMap.put(sizeStr[i], sizeVal[i]);
        }
        lstSize = new JList(sizeStr);
        spFont = new JScrollPane(lstFont);
        spSize = new JScrollPane(lstSize);

        String[] colorStr = new String[]{
            "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", 
            "ORANGE", "PINK", "RED", "WHITE", "YELLOW"
        };
        Color[] colorVal = new Color[]{
            Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, 
            Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
        };
        colorMap = new HashMap();
        for (int i = 0; i < colorStr.length; i++) {
            colorMap.put(colorStr[i], colorVal[i]);
        }
        cbColor = new JComboBox(colorStr);
        showPan = new JPanel();
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        
        

        this.setLayout(null);     
        add(lblFont);
        lblFont.setBounds(12, 10, 30, 20);
        txtFont.setEditable(false);
        add(txtFont);
        txtFont.setBounds(10, 30, 155, 20);
        lstFont.setSelectedValue("Times New Roman", true);
        add(spFont);
        spFont.setBounds(10, 50, 155, 100);

        add(lblStyle);
        lblStyle.setBounds(175, 10, 50, 20);
        txtStyle.setEditable(false);
        add(txtStyle);
        txtStyle.setBounds(175, 30, 130, 20);
        lstStyle.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        add(lstStyle);
        lstStyle.setBounds(175, 50, 130, 100);
        lstStyle.setSelectedValue("Normal", true);

        add(lblSize);
        lblSize.setBounds(320, 10, 30, 20);
        txtSize.setEditable(false);
        add(txtSize);
        txtSize.setBounds(320, 30, 60, 20);
        add(spSize);
        spSize.setBounds(320, 50, 60, 100);
        lstSize.setSelectedValue("9", false);


        add(lblColor);
        lblColor.setBounds(13, 170, 50, 20);
        add(cbColor);
        cbColor.setBounds(10, 195, 130, 22);
        cbColor.setMaximumRowCount(5);

        showPan.setBorder(javax.swing.BorderFactory.createTitledBorder("Font Select"));
        add(showPan);
        showPan.setBounds(170, 170, 210, 100);
        showPan.setLayout(new BorderLayout());
        lblShow.setBackground(Color.white);
        showPan.add(lblShow);
        add(ok);
        ok.setBounds(10, 240, 75, 20);
        add(cancel);
        cancel.setBounds(92, 240, 75, 20);


        
        lstFont.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                current_fontName = (String) lstFont.getSelectedValue();
                txtFont.setText(current_fontName);
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstStyle.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                String value = (String) ((JList) e.getSource()).getSelectedValue();
                if (value.equals("Normal")) {
                    current_fontStyle = Font.PLAIN;
                }
                if (value.equals("Italic")) {
                    current_fontStyle = Font.ITALIC;
                }
                if (value.equals("Bold")) {
                    current_fontStyle = Font.BOLD;
                }
                if (value.equals("Bold Italic")) {
                    current_fontStyle = Font.BOLD | Font.ITALIC;
                }
                txtStyle.setText(value);
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstSize.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                current_fontSize = (Integer) sizeMap.get(lstSize.getSelectedValue());
                txtSize.setText(String.valueOf(current_fontSize));
                lblShow.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        cbColor.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                current_color = (Color) colorMap.get(cbColor.getSelectedItem());
                lblShow.setForeground(current_color);
            }
        });

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myfont = new ChooserFont();
                myfont.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
                myfont.setColor(current_color);
                dialog.dispose();
                dialog = null;
            }
        });

        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myfont = null;
                dialog.dispose();
                dialog = null;
            }
        });

    }

    public ChooserFont showDialog(Frame parent,String title) {
        if(title == null)
            title = "Font";
        dialog = new JDialog(parent, title,true);
        dialog.add(this);
        dialog.setResizable(false);
        dialog.setSize(400, 310);
        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                myfont = null;
                dialog.removeAll();
                dialog.dispose();
                dialog = null;
            }
        });

        dialog.setVisible(true);
        return myfont;
    }

    public static void main(String[] args) {

        JFontChooser2 one = new JFontChooser2();
        ChooserFont lhf = one.showDialog(null,null);
        System.out.println(lhf.getColor());

    }
}  