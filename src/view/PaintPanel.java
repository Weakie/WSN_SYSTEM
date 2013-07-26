package view;

import global.GlobalVariables;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import node.Node;
import node.NodeList;
import node.PaintedNode;

public class PaintPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private double size = GlobalVariables.PaintSize;
	private NodeList nodes;
	//showed text
	private String showedTextHeader;
	private String showedTextFooter;
	
	public PaintPanel(){
		setDoubleBuffered(true);
	}
	public void draw(NodeList p) {
		int panelSize = (int)(GlobalVariables.posMax*size + GlobalVariables.posMin*size);
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.nodes = p;
		this.size = GlobalVariables.PaintSize;
		this.repaint();
	}
	public void draw(NodeList p,double size) {
		int panelSize = (int)(GlobalVariables.posMax*size + GlobalVariables.posMin*size);
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.nodes = p;
		this.size = size;
		this.repaint();
	}
	public void reDraw(double size){
		int panelSize = (int)(GlobalVariables.posMax*size + GlobalVariables.posMin*size);
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.size = size;
		this.repaint();
	}
	public void setShowedText(String header,String footer){
		this.showedTextHeader = header;
		this.showedTextFooter = footer;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		if(nodes==null)
			return;
		//ÏÔÊ¾´«¸ÐÆ÷½Úµã
		for(PaintedNode node : nodes.getNodes()){
			node.draw(g, size);
		}
		//ÏÔÊ¾ÇøÓò±ß½ç
		g.setColor(Color.black);
		g.drawLine((int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMax*size));
		g.drawLine((int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMax*size), (int)(GlobalVariables.posMin*size));
		g.drawLine((int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMax*size), (int)(GlobalVariables.posMax*size), (int)(GlobalVariables.posMax*size));
		g.drawLine((int)(GlobalVariables.posMax*size), (int)(GlobalVariables.posMin*size), (int)(GlobalVariables.posMax*size), (int)(GlobalVariables.posMax*size));
		//ÏÔÊ¾ÒÆ¶¯Â·¾¶
		if(GlobalVariables.paintPath)
		{
			paintPath(g);
		}
		//ÏÔÊ¾×Ö·û´®
		paintText(g);
	}
	
	private void paintPath(Graphics g) {
		g.setColor(Color.red);
		double[] pos = NodeList.getIniPos();
		for(int i=0;i<nodes.getNodes().size();i++){
			Node node = nodes.getNodes().get(i);
			int pos_x1 = (int) ((pos[i*2]-GlobalVariables.posMin)*size+GlobalVariables.posMin*size);
			int pos_y1 = (int) ((pos[i*2+1]-GlobalVariables.posMin)*size+GlobalVariables.posMin*size);
			int pos_x2 = (int) ((node.getPosX()-GlobalVariables.posMin)*size+GlobalVariables.posMin*size);
			int pos_y2 = (int) ((node.getPosY()-GlobalVariables.posMin)*size+GlobalVariables.posMin*size);
			g.drawLine(pos_x1, pos_y1, pos_x2, pos_y2);
			//g.drawLine((int)pos[i*2],(int)pos[i*2+1],(int)node.getPosX(),(int)node.getPosY());
		}
	}
	
	private void paintText(Graphics g){
		g.setColor(Color.BLACK);
		if(this.getName()!=null){
			g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, (int)(24*size)));
			g.drawString(this.getName(), (int)((GlobalVariables.posMax+GlobalVariables.posMin)/2*size), (int)(20*size));
		}
		g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, (int)(20*size)));
		if(this.showedTextHeader!=null){
			g.drawString(showedTextHeader, (int)(10*size), (int)(50*size));
		}
		if(this.showedTextFooter!=null){
			g.drawString(showedTextFooter, (int)(10*size), (int)(GlobalVariables.posMax*size + GlobalVariables.posMin*size-20*size));
		}
	}

}