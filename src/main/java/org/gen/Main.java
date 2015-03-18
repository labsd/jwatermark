package org.gen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.gen.mvc.main.MainCtlr;
import org.gen.mvc.main.MainVw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JFrame {
	static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		log.info("method main started.");
		Fcty fcty = new Fcty();
		MainCtlr mainCtl = fcty.getMainCtl();
		mainCtl.showWindow();
		log.info("main showed.");
	}
}
