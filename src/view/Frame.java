package view;

import global.GlobalVariables;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import node.NodeList;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel value1 = new JPanel();
	private PaintPanel result1 = new PaintPanel();
	private JPanel value2 = new JPanel();
	private PaintPanel result2 = new PaintPanel();
	private JPanel value3 = new JPanel();
	private PaintPanel result3 = new PaintPanel();
	private void setGUI(){
		Container c=getContentPane();
		c.setLayout(new BorderLayout(0,0));
		
		JTabbedPane tabPanel1 = new JTabbedPane(JTabbedPane.LEFT);
		tabPanel1.add("Result",value1);
		tabPanel1.add("PSOVF",result1);
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(tabPanel1);
		
		JTabbedPane tabPanel2 = new JTabbedPane(JTabbedPane.LEFT);
		tabPanel2.add("Result",value2);
		tabPanel2.add("PSOVF",result2);
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(tabPanel2);
		
		JTabbedPane tabPanel3 = new JTabbedPane(JTabbedPane.LEFT);
		tabPanel3.add("Result",value3);
		tabPanel3.add("PSOVF",result3);
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(tabPanel3);
		
		JTabbedPane tabPanelPSO = new JTabbedPane(JTabbedPane.TOP);
		tabPanelPSO.add("VirtureForcePso",p1);
		tabPanelPSO.add("BasePSO",p2);
		tabPanelPSO.add("初始化节点位置",p3);
		
		c.add(tabPanelPSO,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}
	
	public Frame(){
		super();
		this.setGUI();
	}
	
	public void run(){
		result3.draw(new NodeList(), GlobalVariables.PaintSize);
		setVisible(true);
		
		NodeList list1 = new NodeList();
		NodeList list2 = new NodeList();
		
		algorithm.jni.multi.JNIPSOVirtureForce pso1 = new algorithm.jni.multi.JNIPSOVirtureForce();
		pso1.setLoopNum(100);
		pso1.setScaleNum(50);
		pso1.runAlgorithm();
		pso1.getResult(list1);
		result1.draw(list1, GlobalVariables.PaintSize);
		System.out.println(pso1.getTotalRunTime());
		
		JPanel p1 = chart.LineCharts.createXYLine(pso1.getGlobalBestFit(),pso1.getGlobalBestCov(),pso1.getGlobalBestDis());
		value1.add(p1);
		setVisible(true);
		
		algorithm.jni.multi.JNIPSOVirtureForce pso2 = new algorithm.jni.multi.JNIPSOVirtureForce();
		pso2.setLoopNum(100);
		pso2.setScaleNum(1);
		pso2.runAlgorithm();
		pso2.getResult(list2);
		result2.draw(list2, GlobalVariables.PaintSize);
		System.out.println(pso2.getTotalRunTime());
		
		JPanel p2 = chart.LineCharts.createXYLine(pso2.getGlobalBestFit(),pso2.getGlobalBestCov(),pso2.getGlobalBestDis());
		value2.add(p2);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		GlobalVariables.init();
		new Frame().run();

	}
}
