package node;

import global.GlobalVariables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
/**
 * �������֪ģ��
 * @author dell
 *
 */
public class IrregularSensedNode extends BaseNode {
	private static final long serialVersionUID = 1L;
	private static int Angle = 360;	//�Ƕ�����
	private static int D = 10;		//Rc-Rp ֮����  D�� block
	private static double Rc = 20;	//�ɿ��в���
	private static double Rp = 30;	//Բ��
	private static double s = 0.050; //˥������
	private static Random r = new Random();
	
	private double[] Rv;//ÿ���Ƕ��ϵĲ��ɿ��в�����뾶
	
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
		//���������֪����
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
		//���ɸ�����
		g2d.setStroke(stroke2);
		g2d.setColor(Color.yellow);
		g2d.drawOval((int)(posx2-(Rc*size)), (int)(posy2-(Rc*size)), (int)(Rc*size)*2, (int)(Rc*size)*2);
		//���ڵ�
		g2d.setColor(Color.red);
		g2d.fillOval((int)(posx2-2*size), (int)(posy2-2*size), (int)(4*size), (int)(4*size));
		//��ͨ�ŷ�Χ
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
	 * ��ʼ���ڵ�Ŀɸ���
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
	 * ����ڵ�ĸв����
	 * @param d ��ڵ����d
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
	 * ����Ŀ������(x,y)��p��ĽǶȷ�λ
	 * @param x
	 * @param y
	 * @return
	 */
	private double calcuAngle(double x,double y){
		return (Math.atan2(y-posy,x-posx)/Math.PI*Angle+Angle)%Angle;
	}
	
	/**
	 * ����ÿ���Ƕ��ϵĲ��ɿ��в�뾶
	 * @param angle �Ƕ�
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
