package bGUI;


import javax.swing.*;

import java.awt.*;


public class AboutBox extends JFrame{

	public AboutBox(){
		super();
		setPreferredSize(new Dimension(300, 500));
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 500));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Bunny World"));
		panel.add(new JLabel("version: we lost count a long time ago"));
		panel.add(Box.createVerticalGlue());
		panel.add(new JLabel(BIconHelper.BUNNY_OF_DEATH));
		panel.add(new JLabel("All Hail the Dirty Bit"));
		panel.add(new JLabel("Matthew A. L. Chun-Lum"));
		panel.add(new JLabel("Chidozie Nwobilor"));
		panel.add(new JLabel("Junichi Tsutsui"));
		panel.add(Box.createVerticalGlue());
		panel.add(new JLabel("Button icons from http://dryicons.com"));
		panel.add(new JLabel("used under the DryIcons Free License"));
		
		for(Component comp : panel.getComponents())
            ((JComponent) comp).setAlignmentX(Box.CENTER_ALIGNMENT);
		
		add(panel);
		setTitle("About");
		pack();
		setVisible(true);
	}
}
