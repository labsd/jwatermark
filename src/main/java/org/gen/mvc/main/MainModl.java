package org.gen.mvc.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.gen.Fcty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainModl {
	
	static final Logger log = LoggerFactory.getLogger(MainModl.class);

	Fcty factory;
	DefaultListModel textListModel;
	MainCtlr controller;
	File[] fileList;
	File textListFile;
	File imageFile;
	BufferedImage image;
	File savePath;
	int drawFrom;
	Color color = new Color(255, 255, 255);
	Font font = new Font("Serif", Font.PLAIN, 72);
	int fontSize;

	public MainModl(Fcty fcty, MainCtlr ctl) {
		this.factory = fcty;
		this.controller = ctl;
	}

	public void setTextListFile(File file) {
		this.textListFile = file;
		loadTextListFile();
	}

	private void loadTextListFile() {
		textListModel = new DefaultListModel();
		try {
			String charset = "UTF-8";
			if (factory.getLocale() == Locale.JAPAN || factory.getLocale() == Locale.JAPANESE) {
				charset = "ms932";
			} else if (factory.getLocale() == Locale.CHINA || factory.getLocale() == Locale.CHINESE){
				charset = "gb2312";
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(textListFile), charset));
			for (String line = ""; (line = reader.readLine()) != null; ) {
				textListModel.addElement(line);
			}
			controller.onLoadedTextList();
		} catch (Exception e) {
			log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
		}
	}

	public void setImageFile(File file) {
		this.imageFile = file;
		loadImageFile();
	}

	private void loadImageFile() {
		try {
			this.image = ImageIO.read(imageFile);
			controller.onLoadedImageFile();
		} catch (IOException e) {
			log.info("Exception: {}, {}", e.toString(), e.getStackTrace());
		}
	}

	public void setSavePath(File file) {
		this.savePath = file;		
	}

}
