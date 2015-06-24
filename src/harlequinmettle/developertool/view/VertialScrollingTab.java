package harlequinmettle.developertool.view;

import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

//Jun 24, 2015  1:37:44 PM 
public class VertialScrollingTab {
	public VertialScrollingTab(String title) {
		this.title = title;
	}

	String title = "";
	VerticalJPanel contents = new VerticalJPanel();
	PreferredJScrollPane scroller = new PreferredJScrollPane(contents);

	public void revalidateRepaint() {

		contents.revalidate();
		contents.repaint();
	}
}
