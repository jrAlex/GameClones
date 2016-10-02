package jrAlex.core;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public abstract class View extends JPanel
{
	private static final long serialVersionUID = 1L;

	public abstract void update(int delta);

	protected void addKeybind(String key, AbstractAction action)
	{
		getInputMap().put(KeyStroke.getKeyStroke(key), key);
		getActionMap().put(key, action);
	}
}
