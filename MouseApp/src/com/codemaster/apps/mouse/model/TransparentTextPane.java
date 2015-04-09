package com.codemaster.apps.mouse.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class TransparentTextPane extends JTextPane {
	
	private int transparency;

	public TransparentTextPane(int transparency) {
		this.transparency = transparency;
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(255, 255, 255, transparency));
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (insets.left + insets.right);
		int height = getHeight() - (insets.top + insets.bottom);
		g.fillRect(x, y, width, height);
		super.paintComponent(g);
	}

	public void setCustomText(String text, Color color, int fontSize,
			boolean isBold, boolean isItalic, int align) {
		StyledDocument doc = this.getStyledDocument();
		MutableAttributeSet attrs = new SimpleAttributeSet();

		StyleConstants.setForeground(attrs, color);
		StyleConstants.setBold(attrs, isBold);
		StyleConstants.setItalic(attrs, isItalic);
		StyleConstants.setFontSize(attrs, fontSize);

		try {
			doc.remove(0, doc.getLength());
			doc.insertString(0, text, attrs);
		} catch (BadLocationException e) {
		}

		StyleConstants.setAlignment(attrs, align);
		doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
	}
}