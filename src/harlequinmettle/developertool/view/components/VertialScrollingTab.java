package harlequinmettle.developertool.view.components;

import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

//Jun 24, 2015  1:37:44 PM 
public class VertialScrollingTab {
	public VertialScrollingTab(String title) {
		this.title = title;
	}

	String title = "";
	public VerticalJPanel contents = new VerticalJPanel();
	public PreferredJScrollPane scroller = new PreferredJScrollPane(contents);

	public void revalidateRepaint() {

		contents.revalidate();
		contents.repaint();
	}
}
