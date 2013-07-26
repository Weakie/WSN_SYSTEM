package node;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NodeList implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private static Random r = new Random();
	private static double[] initPos;
	private static double[][] initRv;
	
	//static value
	public static void initNodeListStaticValue(int nodeSize,double posMax,double posMin){
		//initialize InitPos
		initPos = new double[nodeSize*2];
		for	(int i = 0;i<initPos.length;i++){
			initPos[i] = r.nextDouble()*(posMax-posMin)+posMin;
		}
		//initialize InitRv
		initRv = new double[nodeSize][];
		for (int i = 0; i < nodeSize; i++) {
			IrregularSensedNode newNode = new IrregularSensedNode(0, 0);
			initRv[i] = newNode.getRv();
		}
	}
	
	public static void setInitPos(int nodeSize,double posMax,double posMin){
		initPos = new double[nodeSize*2];
		for	(int i = 0;i<initPos.length;i++){
			initPos[i] = r.nextDouble()*(posMax-posMin)+posMin;
		}
	}
	
	public static void setInitRv(int nodeSize){
		//initialize InitRv
		for (int i = 0; i < nodeSize; i++) {
			IrregularSensedNode node = new IrregularSensedNode(0, 0);
			initRv[i] = node.getRv();
		}
	}
	
	public static double[] getIniPos(){
		return initPos;
	}
	
	public static double[][] getRv(){
		return initRv;
	}
	
	//dynamic value
	private List<Node> nodes;
	
	public NodeList(){
		nodes = new ArrayList<Node>();
		for(int i = 0; i < initRv.length; i++){
			IrregularSensedNode newNode = new IrregularSensedNode(initPos[i*2], initPos[i*2+1], initRv[i]);
			nodes.add(newNode);
		}
	}
	
	public List<Node> updateNodePos(double[] pos){
		if(pos.length!=nodes.size()*2){
			try {
				throw new Exception("node size not match."+pos.length/2+"!="+nodes.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int index = 0;
		for(Node node:nodes){
			node.setPosX(pos[index]);
			node.serPosY(pos[index+1]);
			index+=2;
		}
		return nodes;
	}
	
	public List<Node> getNodes(){
		return this.nodes;
	}
	
	//–Ú¡–ªØ
	public static void write(ObjectOutputStream out) throws Exception{
		out.writeObject(NodeList.initPos);
		out.writeObject(NodeList.initRv);
		out.writeObject(NodeList.r);
	}
		
	public static void read(ObjectInputStream in) throws Exception{
		NodeList.initPos = (double[]) in.readObject();
		NodeList.initRv =  (double[][]) in.readObject();
		NodeList.r =  (Random) in.readObject();
	}
}
