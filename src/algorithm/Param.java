package algorithm;

import java.util.Map;

import global.GlobalVariables;
import node.NodeList;

@SuppressWarnings("unused")
public abstract class Param {
	
	// for Environment
	private int nodeSize = GlobalVariables.nodeSize;
	private double posMax = GlobalVariables.posMax;
	private double posMin = GlobalVariables.posMin;
	private double grid = GlobalVariables.grid;
	private double sensedCth = GlobalVariables.SensedCth;	
	
	// for node
	private int Angle = GlobalVariables.Angle;
	private int D = GlobalVariables.D;
	private double connectedRi = GlobalVariables.connectedRi;
	private double Rc = GlobalVariables.Rc;
	private double Rp = GlobalVariables.Rp;
	private double s = GlobalVariables.s;
	
	// for generate parameter
	private double[] initPosition;
	private double[][] Rv;
	
	public Param(){
		this.Rv = NodeList.getRv();
		this.initPosition = NodeList.getIniPos();
	}
	
	public void resetNodeProperty(){
		this.Rv = NodeList.getRv();
		this.initPosition = NodeList.getIniPos();
		// for Environment
		nodeSize = GlobalVariables.nodeSize;
		posMax = GlobalVariables.posMax;
		posMin = GlobalVariables.posMin;
		grid = GlobalVariables.grid;
		sensedCth = GlobalVariables.SensedCth;	
		
		// for node
		Angle = GlobalVariables.Angle;
		D = GlobalVariables.D;
		connectedRi = GlobalVariables.connectedRi;
		Rc = GlobalVariables.Rc;
		Rp = GlobalVariables.Rp;
		s = GlobalVariables.s;
	}
	 
	public abstract ParamConvertAdapter getConvert(); 
}
