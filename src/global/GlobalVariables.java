package global;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import node.IrregularSensedNode;
import node.NodeList;

public class GlobalVariables{
	//for node
	public static int Angle = 360;
	public static int D = 10;
	public static double connectedRi = 50;
	public static double Rc = 20;
	public static double Rp = 30;
	public static double s = 0.050;

	//for run environment
	public static int nodeSize = 24;
	public static double posMax = 300;
	public static double posMin = 100;
	public static double grid = 10;
	public static double SensedCth = 0.8;//节点能被检测到的概率阈值
	
	//for paint
	public static double PaintSize = 0.5;
	public static boolean paintPath = true;
	public static boolean paintSensedRi = true;
	
	//for fitness calculate;
	public static boolean singleObject = true;//单目标
	public static double f1 = 1;
	public static double f2 = 100;
	
	//全部初始化
	public static void init(){
		IrregularSensedNode.setStaticValue(Angle, D, s, connectedRi, Rc, Rp);
		NodeList.initNodeListStaticValue(nodeSize, posMax, posMin);
	}
	//初始化传感器节点属性
	public static void resetNodeProperties(){
		IrregularSensedNode.setStaticValue(Angle, D, s, connectedRi, Rc, Rp);
		NodeList.setInitRv(nodeSize);
	}
	//初始化传感器初始位置
	public static void resetInitPosition(){
		NodeList.setInitPos(nodeSize, posMax, posMin);
	}
	
	//序列化
	public static void write(ObjectOutputStream out) throws Exception{
		out.writeObject(GlobalVariables.Angle);
		out.writeObject(GlobalVariables.D);
		out.writeObject(GlobalVariables.connectedRi);
		out.writeObject(GlobalVariables.Rc);
		out.writeObject(GlobalVariables.Rp);
		out.writeObject(GlobalVariables.s);
		
		out.writeObject(GlobalVariables.nodeSize);
		out.writeObject(GlobalVariables.posMax);
		out.writeObject(GlobalVariables.posMin);
		out.writeObject(GlobalVariables.grid);
		out.writeObject(GlobalVariables.SensedCth);
		
		out.writeObject(GlobalVariables.PaintSize);
		out.writeObject(GlobalVariables.paintPath);
		out.writeObject(GlobalVariables.paintSensedRi);
		
		out.writeObject(GlobalVariables.singleObject);
		out.writeObject(GlobalVariables.f1);
		out.writeObject(GlobalVariables.f2);
	}
	
	public static void read(ObjectInputStream in) throws Exception{
		Angle = (Integer) in.readObject();
		D = (Integer) in.readObject();
		connectedRi = (Double) in.readObject();
		Rc = (Double) in.readObject();
		Rp = (Double) in.readObject();
		s = (Double) in.readObject();

		nodeSize = (Integer) in.readObject();
		posMax = (Double) in.readObject();
		posMin = (Double) in.readObject();
		grid = (Double) in.readObject();
		SensedCth = (Double) in.readObject();
		
		PaintSize = (Double) in.readObject();
		paintPath = (Boolean) in.readObject();
		paintSensedRi = (Boolean) in.readObject();
		
		singleObject = (Boolean) in.readObject();
		f1 = (Double) in.readObject();
		f2 = (Double) in.readObject();
		IrregularSensedNode.setStaticValue(Angle, D, s, connectedRi, Rc, Rp);
	}
}
