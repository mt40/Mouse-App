package com.codemaster.apps.mouse.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TransparentPane extends JPanel {
	
	public TransparentPane() {
        setOpaque(false);
        setBackground(new Color(0,0,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 255, 128));
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        g.fillRect(x, y, width, height);
        super.paintComponent(g);
    }
}