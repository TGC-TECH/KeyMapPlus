import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class KeyMapPlusConfigTool {

	static JCheckBox macOption;
	static boolean mac;
	static JFrame frame;
	static JPanel mainPanel;
	static JPanel keybindPanel;
	static boolean loaded;
	static JLabel status;
	static ArrayList<JTextField>[] keybinds;
	static ArrayList<String> logged;

	public static void main(String[] args) {
		logged = new ArrayList<String>();
		keybinds = new ArrayList[2];
		for (int i = 0; i < keybinds.length; i++) keybinds[i] = new ArrayList<JTextField>();
		open();
	}

	public static void open() {
		frame = new JFrame("KeyMapPlus Config Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension d = frame.getSize();
				Dimension minD = frame.getMinimumSize();
				if (minD.width < 250) minD.width = 250;
				if (d.width < minD.width) d.width = minD.width;
				if (d.height < minD.height) d.height = minD.height;
				frame.setSize(d);
			}
		});
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		status = new JLabel("Uncompiled");
		status.setAlignmentX(Component.CENTER_ALIGNMENT);
		keybindPanel = new JPanel();
		keybindPanel.setLayout(new BoxLayout(keybindPanel, BoxLayout.Y_AXIS));
		makeFields();
		JButton keybindButton = new JButton("Add new keybind");
		ActionListener buttonListen = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				makeFields();
			}
		};
		keybindButton.addActionListener(buttonListen);
		keybindButton.setToolTipText("Create a new keybind.");
		keybindButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mac = System.getProperty("os.name").indexOf("Mac") != -1;
		macOption = new JCheckBox("Compile for Mac?", mac);
		ActionListener checkListen = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				mac = abstractButton.getModel().isSelected();
				System.out.println(mac);
			}
		};
		macOption.addActionListener(checkListen);
		macOption.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton compButton = new JButton("Compile!");
		ActionListener compListen = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				try {
					assembleFile();
					status.setText("Successfully compiled!");
				} catch (IOException e) {
					status.setText("Error while compiling.");
				}
			}
		};
		compButton.addActionListener(compListen);
		compButton.setToolTipText("Compile the .ino file containing your settings.");
		compButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(status);
		mainPanel.add(keybindPanel);
		mainPanel.add(keybindButton);
		mainPanel.add(macOption);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(compButton);
		frame.add(mainPanel);
		frame.setVisible(true);
		loaded = true;
	}

	public static void makeFields() {
		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		JTextField l = new JTextField();
		JTextField r = new JTextField();
		t.add(l);
		t.add(r);
		keybinds[0].add(l);
		keybinds[1].add(r);
		keybindPanel.add(t);
		if (loaded) {
			Dimension d = frame.getSize();
			Dimension minD = frame.getMinimumSize();
			if (d.height < minD.height) d.height = minD.height;
			frame.setSize(d);
			frame.setVisible(true);
		}
	}

	public static String getCommands(String key) {
		String commands = "";
		String com = "";
		boolean added = false;
		for (int i = 0; i < keybinds[0].size(); i++) {
			if (keybinds[0].get(i).getText().equalsIgnoreCase(key)) {
				com = "'" + keybinds[1].get(i).getText() + "'";
				if (com.equalsIgnoreCase("'CTRL'")) com = "ctrlKey";
				if (com.equalsIgnoreCase("'ALT'")) com = "KEY_LEFT_ALT";
				if (com.equalsIgnoreCase("'TAB'")) com = "KEY_TAB";
				if (com.equalsIgnoreCase("'ESC'")) com = "KEY_ESC";
				if (com.equalsIgnoreCase("'PGDN'")) com = "KEY_PAGE_DOWN";
				if (com.equalsIgnoreCase("'PGUP'")) com = "KEY_PAGE_UP";
				if (com.equalsIgnoreCase("'SHIFT'")) com = "KEY_LEFT_SHIFT";
				if (com.equalsIgnoreCase("'LEFT'")) com = "KEY_LEFT_ARROW";
				if (com.equalsIgnoreCase("'RIGHT'")) com = "KEY_RIGHT_ARROW";
				if (com.equalsIgnoreCase("'UP'")) com = "KEY_UP_ARROW";
				if (com.equalsIgnoreCase("'DOWN'")) com = "KEY_DOWN_ARROW";
				if (com.equalsIgnoreCase("'BKSP'")) com = "KEY_BACKSPACE";
				if (com.equalsIgnoreCase("'RTRN'")) com = "KEY_RETURN";
				if (com.equalsIgnoreCase("'DEL'")) com = "KEY_DELETE";
				if (com.equalsIgnoreCase("'HOME'")) com = "KEY_HOME";
				if (com.equalsIgnoreCase("'END'")) com = "KEY_END";
				if (com.equalsIgnoreCase("'CAPS'")) com = "KEY_CAPS_LOCK";
				if (com.equalsIgnoreCase("'INS'")) com = "KEY_INSERT";
				if (com.equalsIgnoreCase("'F1'")) com = "KEY_F1";
				if (com.equalsIgnoreCase("'F2'")) com = "KEY_F2";
				if (com.equalsIgnoreCase("'F3'")) com = "KEY_F3";
				if (com.equalsIgnoreCase("'F4'")) com = "KEY_F4";
				if (com.equalsIgnoreCase("'F5'")) com = "KEY_F5";
				if (com.equalsIgnoreCase("'F6'")) com = "KEY_F6";
				if (com.equalsIgnoreCase("'F7'")) com = "KEY_F7";
				if (com.equalsIgnoreCase("'F8'")) com = "KEY_F8";
				if (com.equalsIgnoreCase("'F9'")) com = "KEY_F9";
				if (com.equalsIgnoreCase("'F10'")) com = "KEY_F10";
				if (com.equalsIgnoreCase("'F11'")) com = "KEY_F11";
				if (com.equalsIgnoreCase("'F12'")) com = "KEY_F12";
				commands += "Keyboard.press(" + com + ");\n";
				added = true;
			}
		}
		if (added) {
			commands += "delay(100);\nKeyboard.releaseAll();\n";
			logged.add(key);
		}
		return commands;
	}

	public static void assembleFile() throws IOException {
		String file = "#include <PS2Keyboard.h>\n#include <Keyboard.h>\nconst int DataPin = 8;\nconst int IRQpin =  1;\nchar ctrlKey = KEY_LEFT_";
		if (mac) file += "GUI";
		else file += "CTRL";
		file += ";\nPS2Keyboard keyboard;\nvoid setup() {\ndelay(1000);\nkeyboard.begin(DataPin, IRQpin);\nKeyboard.begin();\n}\nvoid loop() {\nif (keyboard.available()) {\nchar c = keyboard.read();\n";
		file += "if (c == PS2_ENTER) {\nKeyboard.println();\n";
		file += getCommands("RTRN");
		file += "} else if (c == PS2_TAB) {\nKeyboard.print(\"[Tab]\");\n";
		file += getCommands("TAB");
		file += "} else if (c == PS2_ESC) {\nKeyboard.print(\"[ESC]\");\n";
		file += getCommands("ESC");
		file += "} else if (c == PS2_PAGEDOWN) {\nKeyboard.print(\"[PgDn]\");\n";
		file += getCommands("PGDN");
		file += "} else if (c == PS2_PAGEUP) {\nKeyboard.print(\"[PgUp]\");\n";
		file += getCommands("PGUP");
		file += "} else if (c == PS2_LEFTARROW) {\nKeyboard.print(\"[Left]\");\n";
		file += getCommands("LEFT");
		file += "} else if (c == PS2_RIGHTARROW) {\nKeyboard.print(\"[Right]\");\n";
		file += getCommands("RIGHT");
		file += "} else if (c == PS2_UPARROW) {\nKeyboard.print(\"[Up]\");\n";
		file += getCommands("UP");
		file += "} else if (c == PS2_DOWNARROW) {\nKeyboard.print(\"[Down]\");\n";
		file += getCommands("DOWN");
		file += "} else if (c == PS2_DELETE) {\nKeyboard.print(\"[Del]\");\n";
		file += getCommands("DEL");
		file += "} else if (c == PS2_F1) {\nKeyboard.print(\"[F1]\");\n";
		file += getCommands("F1");
		file += "} else if (c == PS2_F2) {\nKeyboard.print(\"[F2]\");\n";
		file += getCommands("F2");
		file += "} else if (c == PS2_F3) {\nKeyboard.print(\"[F3]\");\n";
		file += getCommands("F3");
		file += "} else if (c == PS2_F4) {\nKeyboard.print(\"[F4]\");\n";
		file += getCommands("F4");
		file += "} else if (c == PS2_F5) {\nKeyboard.print(\"[F5]\");\n";
		file += getCommands("F5");
		file += "} else if (c == PS2_F6) {\nKeyboard.print(\"[F6]\");\n";
		file += getCommands("F6");
		file += "} else if (c == PS2_F7) {\nKeyboard.print(\"[F7]\");\n";
		file += getCommands("F7");
		file += "} else if (c == PS2_F8) {\nKeyboard.print(\"[F8]\");\n";
		file += getCommands("F8");
		file += "} else if (c == PS2_F9) {\nKeyboard.print(\"[F9]\");\n";
		file += getCommands("F9");
		file += "} else if (c == PS2_F10) {\nKeyboard.print(\"[F10]\");\n";
		file += getCommands("F10");
		file += "} else if (c == PS2_F11) {\nKeyboard.print(\"[F11]\");\n";
		file += getCommands("F11");
		file += "} else if (c == PS2_F12) {\nKeyboard.print(\"[F12]\");\n";
		file += getCommands("F12");
		file += "} else if (c == PS2_HOME) {\nKeyboard.print(\"[Home]\");\n";
		file += getCommands("HOME");
		file += "} else if (c == PS2_END) {\nKeyboard.print(\"[End]\");\n";
		file += getCommands("END");
		file += "} else if (c == PS2_BACKSPACE) {\nKeyboard.print(\"[Backspace]\");\n";
		file += getCommands("BKSP");
		String key = "";
		for (int i = 0; i < keybinds[0].size(); i++) {
			key = keybinds[0].get(i).getText();
			if (key.length() == 1 && !logged.contains(key)) {
				file += "} else if (c == '" + key + "') {\nKeyboard.print(\"" + key + "\");\n";
				file += getCommands(key);
			}
		}
		file += "} else {\nKeyboard.print(c);\n}\n}\n}";
		BufferedWriter writer = new BufferedWriter(new FileWriter("KmpExport.ino"));
		writer.write(file);
		writer.close();
		logged.clear();
	}
}