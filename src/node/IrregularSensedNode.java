package node;

import global.GlobalVariables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
/**
 * 不规则感知模型
 * @author dell
 *
 */
public class IrregularSensedNode extends BaseNode {
	private static final long serialVersionUID = 1L;
	private static int Angle = 360;	//角度总数
	private static int D = 10;		//Rc-Rp 之间有  D个 block
	private static double Rc = 20;	//可靠感测区
	private static double Rp = 30;	//圆外
	private static double s = 0.050; //衰竭银子
	private static Random r = new Random();
	
	private double[] Rv;//每个角度上的不可靠感测区域半径
	
	public IrregularSensedNode(double x, double y) {
		super(x, y);
		iniRv();
	}
	
	public IrregularSensedNode(double x, double y, double[] rv){
		super(x, y);
		this.Rv = rv;
	}

	public static void setStaticValue(int angle,int d,double s,double ri,double rc,double rp){
		IrregularSensedNode.Angle = angle;
		IrregularSensedNode.D = d;
		IrregularSensedNode.Rc = rc;
		IrregularSensedNode.Rp = rp;
		IrregularSensedNode.connectedRi = ri;
		IrregularSensedNode.s = s;
	}
	
	public double[] getRv(){
		return this.Rv;
	}
	@Override
	public void draw(Graphics g, double size) {
		double posx2 = (super.posx-GlobalVariables.posMin)*size+GlobalVariables.posMin*size;
		double posy2 = (super.posy-GlobalVariables.posMin)*size+GlobalVariables.posMin*size;
		Graphics2D g2d = (Graphics2D)g;
		//画不规则感知区域
		double[] x = getXArray();
		double[] y = getYArray();
		int X[] = new int[Angle+1];
		int Y[] = new int[Angle+1];
		for(int i=0;i<Angle;i++){
			X[i] = (int) ( (x[i]*size)+posx2);
			Y[i] = (int) ( (y[i]*size)+posy2);
		}
		X[Angle] = X[0];
		Y[Angle] = Y[0];
		g2d.setStroke(stroke1);
		g2d.setColor(Color.green);
		g2d.drawPolyline(X, Y, X.length);
		//画可感区域
		g2d.setStroke(stroke2);
		g2d.setColor(Color.yellow);
		g2d.drawOval((int)(posx2-(Rc*size)), (int)(posy2-(Rc*size)), (int)(Rc*size)*2, (int)(Rc*size)*2);
		//画节点
		g2d.setColor(Color.red);
		g2d.fillOval((int)(posx2-2*size), (int)(posy2-2*size), (int)(4*size), (int)(4*size));
		//画通信范围
		if(GlobalVariables.paintSensedRi){
			g2d.setStroke(stroke3);
			g2d.setColor(Color.BLUE);
			g2d.drawOval((int)(posx2-connectedRi*size), (int)(posy2-connectedRi*size), (int)(connectedRi*size*2), (int)(connectedRi*size*2));
		}
		g2d.setStroke(stroke1);
	}

	@Override
	public double getSensedPi(double x, double y) {
		double d = super.getDistance(x, y);
		if(d>Rp){
			return 0;
		}
		if(d<Rc){
			return 1;
		}
		double angle = this.calcuAngle(x, y);
		double rv = this.getRvByAngle(angle);
		if(d>rv){
			return 0;
		}
		return calcuPi(d);
	}
	
	public boolean isInSensedRegion(double x,double y){
		double angle = this.calcuAngle(x, y);
		double rv = this.getRvByAngle(angle);
		return this.getDistance(x, y)<rv;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<Angle;i++){
			builder.append(Rv[i]+" ");
		}
		builder.append("\n");
		return builder.toString();
	}
	
	/**
	 * 初始化节点的可感区
	 */
	private void iniRv(){
		Rv =new double[Angle];
		for(int i=0;i<Rv.length;i++){
			int j = 0;
			for(j=0;j<D;j++){
				double pi = calcuPi((Rp-Rc)/D*j+Rc);
				if(pi<r.nextDouble()){
					break;
				}
			}
			Rv[i] = (Rp-Rc)/D*j+Rc;
		}
	}
	/**
	 * 计算节点的感测概率
	 * @param d 与节点相距d
	 * @return
	 */
	private static double calcuPi(double d){
		if(d>Rp)
			return 0;
		if(d<Rc)
			return 1;
		return Math.exp(-s*(d-Rc));
	}
	
	/**
	 * 计算目标坐标(x,y)在p点的角度方位
	 * @param x
	 * @param y
	 * @return
	 */
	private double calcuAngle(double x,double y){
		return (Math.atan2(y-posy,x-posx)/Math.PI*Angle+Angle)%Angle;
	}
	
	/**
	 * 返回每个角度上的不可靠感测半径
	 * @param angle 角度
	 * @return
	 */
	private double getRvByAngle(double angle){
		
		int angle1 = (int)angle%Angle;
		int angle2 = (angle1+1)%Angle;
		try{
		double d1 = Rv[angle1];
		double d2 = Rv[angle2];
		return (d1+d2)/2;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	

	private double[] getXArray(){
		double X[] = new double[Angle];
		for(int i=0;i<Angle;i++){
			X[i]=Math.cos((double)i/Angle*2*Math.PI)*Rv[i];
		}
		return X;
	}
	private double[] getYArray(){
		double Y[] = new double[Angle];
		for(int i=0;i<Angle;i++){
			Y[i]=Math.sin((double)i/Angle*2*Math.PI)*Rv[i];
		}
		return Y;
	}
}
