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
	public static double SensedCth = 0.8;//�ڵ��ܱ���⵽�ĸ�����ֵ
	
	//for paint
	public static double PaintSize = 0.5;
	public static boolean paintPath = true;
	public static boolean paintSensedRi = true;
	
	//for fitness calculate;
	public static boolean singleObject = true;//��Ŀ��
	public static double f1 = 1;
	public static double f2 = 100;
	
	//ȫ����ʼ��
	public static void init(){
		IrregularSensedNode.setStaticValue(Angle, D, s, connectedRi, Rc, Rp);
		NodeList.initNodeListStaticValue(nodeSize, posMax, posMin);
	}
	//��ʼ���������ڵ�����
	public static void resetNodeProperties(){
		IrregularSensedNode.setStaticValue(Angle, D, s, connectedRi, Rc, Rp);
		NodeList.setInitRv(nodeSize);
	}
	//��ʼ����������ʼλ��
	public static void resetInitPosition(){
		NodeList.setInitPos(nodeSize, posMax, posMin);
	}
	
	//���л�
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
