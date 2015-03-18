package org.gen.mvc.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.gen.Fcty;
import org.gen.Messages;
import org.gen.common.ChooserFont;
import org.gen.common.JFontChooser;
import org.gen.common.JFontChooser2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCtlr {
	static final Logger log = LoggerFactory.getLogger(MainCtlr.class);
	
	static final int LEFT_TOP = 0;
	static final int CENTER = 1;
	static final int RIGHT_BOTTOM = 2;

	private Fcty factory;
	private MainVw view;
	private MainModl model;

	public MainCtlr(Fcty factory) {
		this.factory = factory;
		view = factory.getMainView(this);
		model = factory.getMainModel(this);
		bind();
	}

	private void bind() {
		view.bind();
		
		Calendar today = Calendar.getInstance();
		Calendar endDay = Calendar.getInstance();
		endDay.set(2015, 2, 24);
		if (today.compareTo(endDay) > 0) {
			JOptionPane.showMessageDialog(null, "プロトタイプのお試し期間が終了しました。システム担当者に問い合わせしてください。");
			System.exit(0);
		}
	}

	public void showWindow() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(view);
		} catch (Exception e) {
			log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
		}
		view.setSize(1024, 768);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void onTextListFileOpen() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		//fileChooser.setCurrentDirectory(new File("D:/xampp/mysql/data/"));
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Source Files");
		//chooser.setMultiSelectionEnabled(true);
		int result = chooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getAbsolutePath();
			//setStatus("loading " + filename + " ...");
			//model.setFilename(filename);
			//setStatus("loaded " + filename + "");
			//setTitle(chooser.getSelectedFile().getName());
			model.setTextListFile(chooser.getSelectedFile());
			//model.fileList = chooser.getSelectedFiles();
			//for (File f : chooser.getSelectedFiles()) {
			//	model.fileListModel.addElement(f.getName());
			//}
			
		}				
	}

	public void onFontChoose() {
        JFontChooser chooser = new JFontChooser();
        chooser.setSelectedFont(model.font);
        //ChooserFont lhf = one.showDialog(null,null);
        if (chooser.showDialog(view) == JFontChooser.OK_OPTION) {
        	model.font = chooser.getSelectedFont();
        	model.fontSize = chooser.getSelectedFontSize();
        	view.fontNameLabel.setText(model.font.getFamily() + " " + model.font.getSize());
        }
        
	}

	public void onColorChoose() {
		Color color = JColorChooser.showDialog(view,
	            "Choose a color...", model.color);
        if (color != null) {
          model.color = color;
          view.colorNameLabel.setText("" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
        }
        
	}

	public void onTextListSelected() {
		
	}
	
	public void onLoadedTextList() {
		view.textList.setModel(model.textListModel);
		if (model.textListModel.getSize() == 0) {
			model.textListModel.addElement("No entries loaded.");
		}
		view.textList.setSelectedIndex(0);
		view.textListFileNameLabel.setText(model.textListFile.getName());
	}

	public void onImageFileOpen() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("*.jpg;*.gif;*.png", "jpg", "gif", "png"));
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Image File");
		int result = chooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			model.setImageFile(chooser.getSelectedFile());
		}			
	}

	public void onLoadedImageFile() {
//		JLabel label = view.origLabel;
//		BufferedImage image = model.image;
//		ImageIcon icon = new ImageIcon();
//		icon.setImage(image.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_DEFAULT));
//		label.setIcon(icon);
		
		view.imageFileName.setText(model.imageFile.getName());
		view.modifiedImageLabel.setIcon(new ImageIcon(model.image));
	}

	public void onSavePathChoose() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Save to Path");
		int result = chooser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			model.setSavePath(chooser.getSelectedFile());
			view.savePathNameLabel.setText(chooser.getSelectedFile().getName());
		}
	}

	public void onPreview() {
		try {
			String errMsg = "";
			if (model.image == null) {
				errMsg += ", " + Messages.getString("mainview.label.image");
			} 
			if (model.savePath == null) {
				errMsg += ", " + Messages.getString("mainview.label.save_path");
			} 
			if (model.textListModel == null) {
				errMsg += ", " + Messages.getString("mainview.label.text_list_file");
			}
			
			if (errMsg.length() > 0) {
				errMsg = errMsg.replaceFirst(", ", "");
				JOptionPane.showMessageDialog(view, Messages.getString("mainview.error.necessary", errMsg), 
						Messages.getString("mainview.error.title", errMsg),JOptionPane.ERROR_MESSAGE);
			} else {
				String text = (String) model.textListModel.getElementAt(view.textList.getSelectedIndex());
				
				Color fontColor = model.color;
				BufferedImage oldImg = model.image;
				String typeFace = view.fontNameLabel.getText();
				int fontSize = 72;
				Font font = model.font;//new Font(typeFace, Font.PLAIN, fontSize);
				int xLocation = Integer.parseInt(view.xLabel.getText().equals("") ? "0" : view.xLabel.getText());
				int yLocation = Integer.parseInt(view.yLabel.getText().equals("") ? "0" : view.xLabel.getText());
				String fileType = "JPG";
			
				BufferedImage buffImg = createImageWithText(oldImg, font, fontColor, model.drawFrom, text, xLocation, yLocation, fileType);
	
				view.modifiedImageLabel.setIcon(new ImageIcon(buffImg
	//					.getScaledInstance(
	//					view.modLabel.getWidth(), 
	//					view.modLabel.getHeight(), 
	//					Image.SCALE_DEFAULT)
						));
				view.modifiedImageLabel.setText("");
			}
		} catch (Exception e) {
			log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
		}
	}

	private BufferedImage createImageWithText(BufferedImage oldImg, Font font, Color fontColor, int drawFrom, String text, 
			int x, int y, String fileType) {
		BufferedImage buffImg = new BufferedImage(oldImg.getWidth(), oldImg.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g = buffImg.createGraphics();

		FontMetrics metrics = new FontMetrics(font) {};  
		Rectangle2D bounds = metrics.getStringBounds(text, null);  
		int textWidth = (int) bounds.getWidth();
		int textHeight = (int) bounds.getHeight();
		int textLeading = (int) metrics.getLeading();
		int textDecent = (int) metrics.getMaxDescent();
		//SwingUtilities.computeStringWidth( fontMetrics, stringToComputeTheWidth );
		//System.out.println("textLeading: " + textLeading + " textDecent: " + textDecent);
		
		int xLocation, yLocation;
		switch (drawFrom) {
		case LEFT_TOP:
			xLocation = x;
			yLocation = y + textHeight - textDecent - textLeading;
			break;
		case CENTER:
			xLocation = buffImg.getWidth() / 2 + x - textWidth / 2;
			yLocation = buffImg.getHeight() / 2 + y + (textHeight - textDecent - textLeading) / 2;
			break;
		case RIGHT_BOTTOM:
			xLocation = buffImg.getWidth() - x - textWidth;
			yLocation = buffImg.getHeight() - y - textDecent - textLeading;
			break;
		default:
			throw new IllegalArgumentException("unknown draw from value: " + drawFrom);
		}
		
		g = buffImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
			    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.drawImage(oldImg, null, 0, 0);
		g.setColor(fontColor);
		g.setFont(font);
		g.drawString(text, xLocation, yLocation - (int)(12d / 72 * font.getSize()));
		//g.drawRect(xLocation, yLocation - textHeight, textWidth, textHeight);
		g.dispose();
		return buffImg;
	}

	public void onSaveAll() {
		String errMsg = "";
		if (model.image == null) {
			errMsg += ", " + Messages.getString("mainview.label.image");
		} 
		if (model.savePath == null) {
			errMsg += ", " + Messages.getString("mainview.label.save_path");
		} 
		if (model.textListModel == null) {
			errMsg += ", " + Messages.getString("mainview.label.text_list_file");
		}
		
		if (errMsg.length() > 0) {
			errMsg = errMsg.replaceFirst(", ", "");
			JOptionPane.showMessageDialog(view, Messages.getString("mainview.error.necessary", errMsg), 
					Messages.getString("mainview.error.title", errMsg),JOptionPane.ERROR_MESSAGE);
		} else {
			Color fontColor = model.color;
			BufferedImage oldImg = model.image;
			String typeFace = "Times New Roman";
			int fontSize = 72;
			Font font = model.font;//new Font(typeFace, Font.PLAIN, fontSize);
			int xLocation = Integer.parseInt(view.xLabel.getText().equals("") ? "0" : view.xLabel.getText());
			int yLocation = Integer.parseInt(view.yLabel.getText().equals("") ? "0" : view.xLabel.getText());
			String fileType = "jpg";
			
			try {
				for (int i = 0; i < model.textListModel.getSize(); i ++) {
					if (i > 5) {
						JOptionPane.showMessageDialog(view, 
								"プロトタイプの画像作成は5枚までになります。ご了承をお願いいたします。",
								"プロトタイプの利用について",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					}
					String text = (String) model.textListModel.getElementAt(i);
				
					String filename = model.savePath.getAbsolutePath() + File.separator + (i + 1) + "." + fileType;
					view.statusLabel.setText("Saving " + filename);
					FileOutputStream output = new FileOutputStream(new File(filename));
					BufferedImage buffImg = createImageWithText(model.image, font, fontColor, model.drawFrom, text, xLocation, yLocation, fileType);
					view.modifiedImageLabel.setIcon(new ImageIcon(buffImg));
					view.modifiedImageLabel.setText("");
					ImageIO.write(buffImg, fileType, output); 
					output.close();
				}
			} catch (Exception e) {
				log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
			}
		}
	}

	public void onDrawFromChange() {
		model.drawFrom = view.drawFromComboBox.getSelectedIndex();
	}

}
