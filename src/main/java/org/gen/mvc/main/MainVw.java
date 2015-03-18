package org.gen.mvc.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.gen.Fcty;
import org.gen.Messages;
import org.gen.common.JScalablePane;
import org.gen.common.NumberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVw extends JFrame {
	static final Logger log = LoggerFactory.getLogger(MainVw.class);

	Fcty factory;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openMenuItem;
	MainVw view;
	JPanel splitPane;
	JPanel leftPanel;
	JPanel rightPanel;
	JLabel originalImageLabel;
	JLabel modifiedImageLabel;
	JList textList;
	JButton textListFileOpenButton;
	JLabel imageFileName;
	JButton imageFileButton;


	MainCtlr controller;
	JPanel optionPanel;
	JTextField textField;
	JLabel fontNameLabel;
	JButton fontButton;
	JLabel statusLabel;
	JTextField yLabel;
	JTextField xLabel;
	JLabel textListFileNameLabel;
	JLabel savePathNameLabel;
	JButton savePathButton;
	JButton previewButton;
	JButton saveAllButton;
	JComboBox drawFromComboBox;	

	JLabel titleLabel;

	JPanel contentPane;

	JLayeredPane layeredPane;

	JPanel bgPanel;

	JLabel bgLabel;

	BufferedImage bgImage;

	JLabel colorNameLabel;
	JButton colorButton;
	
	public MainVw(Fcty fcty, MainCtlr ctl) {
		try {
			this.factory = fcty;
			view = this;
			this.controller = ctl;
	
			//setLocationRelativeTo(null);
			menuBar = new JMenuBar();
			//setJMenuBar(menuBar);
			menuBar.add(fileMenu = new JMenu(Messages.getString("mainview.menu.file")));
			fileMenu.add(openMenuItem = new JMenuItem(Messages.getString("mainview.menu.open")));
			
			view.setIconImage(ImageIO.read(this.getClass().getResource("/images/icon.ico").openStream()));
			
			view.setTitle(Messages.getString("mainview.label.title"));
			view.setContentPane(layeredPane = new JLayeredPane());//layeredPane = new JLayeredPane());
			view.setLayout(null);
			layeredPane.add(bgPanel = new JPanel(){{setOpaque(false);}}, Integer.MIN_VALUE);
			layeredPane.setLayout(null);
			//bgPanel = new JPanel();
			bgPanel.add(bgLabel = new JLabel());
			bgPanel.setBorder(null);
			bgPanel.setLayout(null);
			bgImage = ImageIO.read(this.getClass().getResource("/images/background.jpg").openStream());

			layeredPane.add(contentPane = new JPanel() {{setOpaque(false);}}, JLayeredPane.PALETTE_LAYER);
			contentPane.setLayout(new BorderLayout());
			contentPane.setBorder(new EmptyBorder(10, 10, 40, 20));
			contentPane.add(titleLabel = new JLabel(Messages.getString("mainview.label.title")), BorderLayout.NORTH);
			Font titleFont = new Font(Messages.getString("mainview.font.default"), 0, 36);
			titleLabel.setFont(titleFont);
			titleLabel.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/icon.ico").openStream())));
			contentPane.add(splitPane = new JPanel() {{setOpaque(false);}});//new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {{setOpaque(false);}});
			contentPane.add(statusLabel = new JLabel(Messages.getString("mainview.status.ready")) {{setOpaque(false);}}, BorderLayout.SOUTH);
			splitPane.setLayout(new BorderLayout());
			//splitPane.setDividerLocation(300);
			splitPane.add(leftPanel = new JPanel() {{setOpaque(false);}}, BorderLayout.WEST); //new JSplitPane(JSplitPane.VERTICAL_SPLIT)
			splitPane.add(rightPanel = new JPanel() {{setOpaque(false);}});
			
			leftPanel.setLayout(new BorderLayout());
			
			leftPanel.add(optionPanel = new JPanel() {{setOpaque(false);}}, BorderLayout.NORTH);
			optionPanel.setPreferredSize(new Dimension(300, 400));
			//leftPanel.setLeftComponent(optionPanel = new JPanel());
			//leftPanel.setDividerLocation(400);
			
			optionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			optionPanel.add(new JPanel() {{
				setOpaque(false);
				setLayout(new GridLayout(1, 0, 10, 10));
				add(new JLabel(Messages.getString("mainview.label.x"), SwingConstants.RIGHT));
				add(xLabel = new JTextField(Messages.getString("mainview.field.x_value")));
				xLabel.setDocument(new NumberValidator(4));
				add(new JLabel(Messages.getString("mainview.label.y"), SwingConstants.RIGHT));
				add(yLabel = new JTextField(Messages.getString("mainview.field.y_value")));
				yLabel.setDocument(new NumberValidator(4));
			}});
			optionPanel.add(new JLabel(Messages.getString("mainview.label.draw_from")));
			optionPanel.add(drawFromComboBox = new JComboBox(){{
				addItem(Messages.getString("mainview.combo.left_top"));
				addItem(Messages.getString("mainview.combo.center"));
				addItem(Messages.getString("mainview.combo.right_bottom"));
				this.setSelectedIndex(MainCtlr.LEFT_TOP);
			}});
	
			
			optionPanel.setLayout(new GridLayout(0, 1));
			optionPanel.add(new JLabel(Messages.getString("mainview.label.font")));
			optionPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new FlowLayout(FlowLayout.TRAILING));
				add(fontNameLabel = new JLabel(Messages.getString("mainview.label.font_value")));
				add(fontButton = new JButton(Messages.getString("mainview.button.choose_font")) {{setOpaque(false);}});
				}});
			
			optionPanel.add(new JLabel(Messages.getString("mainview.label.color")));
			optionPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new FlowLayout(FlowLayout.TRAILING));
				add(colorNameLabel = new JLabel(Messages.getString("mainview.label.color_value")));
				add(colorButton = new JButton(Messages.getString("mainview.button.choose_color")) {{setOpaque(false);}});
				}});
			
			optionPanel.add(new JLabel(Messages.getString("mainview.label.image")));
			optionPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new FlowLayout(FlowLayout.TRAILING));
				add(imageFileName = new JLabel(Messages.getString("mainview.label.image_value")));
				add(imageFileButton = new JButton(Messages.getString("mainview.button.choose_image")) {{setOpaque(false);}});
				}});
	
			optionPanel.add(new JLabel(Messages.getString("mainview.label.save_path")));
			optionPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new FlowLayout(FlowLayout.TRAILING));
				add(savePathNameLabel = new JLabel(Messages.getString("mainview.label.save_path_value")));
				add(savePathButton = new JButton(Messages.getString("mainview.button.choose_save_path")) {{setOpaque(false);}});
			}});
			
			optionPanel.add(new JLabel(Messages.getString("mainview.label.text_list_file")));
			optionPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new FlowLayout(FlowLayout.TRAILING));
				add(textListFileNameLabel = new JLabel(Messages.getString("mainview.label.choose_text_list_file")));
				add(textListFileOpenButton = new JButton(Messages.getString("mainview.button.choose_text_list_file")){{setOpaque(false);}});
			}}, BorderLayout.NORTH);

			//leftPanel.setRightComponent(new JPanel(){{
			leftPanel.add(new JPanel(){{
				setOpaque(false);
				setLayout(new BorderLayout());
				add(new JScrollPane(textList = new JList(),
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
				add(new JPanel(){{
					setOpaque(false);
					setLayout(new FlowLayout(FlowLayout.TRAILING));
					add(previewButton = new JButton(Messages.getString("mainview.button.preview")) {{setOpaque(false);}});
					add(saveAllButton = new JButton(Messages.getString("mainview.button.save_all")) {{setOpaque(false);}});
				}}, BorderLayout.SOUTH);
			}});

			rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			rightPanel.setLayout(new BorderLayout(0, 1));
			//right.add(origLabel = new JLabel("orig"));
			rightPanel.add(new JLabel(Messages.getString("mainview.label.click_preview_to_preview")), BorderLayout.NORTH);
			rightPanel.add(new JScrollPane(modifiedImageLabel = new JLabel(),
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) {{setOpaque(false);getViewport().setOpaque(false);}});
		
		} catch (Exception e) {
			log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
			throw new RuntimeException(e);
		}
		
	}

	public void bind () {
		view.addComponentListener(new ComponentListener() {
			@Override
		    public void componentResized(ComponentEvent e) {
				bgPanel.setBounds(0, 0, view.getWidth(), view.getHeight());
				bgLabel.setBounds(0, 0, view.getWidth(), view.getHeight());
				bgLabel.setIcon(new ImageIcon(bgImage.getScaledInstance(view.getWidth(), view.getWidth(), Image.SCALE_DEFAULT)));
				contentPane.setBounds(0, 0, view.getWidth(), view.getHeight());
		    }
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		
		openMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onTextListFileOpen();
			}});
		imageFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onImageFileOpen();
			}});
		textListFileOpenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onTextListFileOpen();
			}});
		fontButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onFontChoose();
			}});
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onColorChoose();
			}});
		savePathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onSavePathChoose();
			}});
		previewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onPreview();
			}});
		saveAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onSaveAll();
			}});
		
		drawFromComboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.onDrawFromChange();
			}});
		
		textList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				controller.onTextListSelected();
			}});
		
	}

}
