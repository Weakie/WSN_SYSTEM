package node;


public abstract class BaseNode implements Node {
	private static final long serialVersionUID = 1L;

	protected static double connectedRi = 50;// 通信半径
	
	protected double posx,posy;	//节点位置
	
	public BaseNode(double x,double y){
		posx = x;
		posy = y;
	}
	
	@Override
	public double getConnectedRi(){
		return connectedRi;
	}
	
	@Override
	public double getPosX() {
		return posx;
	}

	@Override
	public double getPosY() {
		return posy;
	}

	@Override
	public void setPosX(double x) {
		this.posx = x;
	}

	@Override
	public void serPosY(double y) {
		this.posy = y;
	}

	@Override
	public double getDistance(double x, double y) {
		//return Math.sqrt(Math.pow(x-posx,2)+Math.pow(y-posy,2));
		return Math.sqrt((x-posx)*(x-posx)+(y-posy)*(y-posy));
	}
	
	@Override
	public double getDistance(Node node){
		//return Math.sqrt(Math.pow(node.getPosX()-posx,2)+Math.pow(node.getPosY()-posy,2));
		return Math.sqrt((node.getPosX()-posx)*(node.getPosX()-posx)+(node.getPosY()-posy)*(node.getPosY()-posy));
	}
	@Override
	public boolean isConnected(Node node) {
		return getDistance(node)<=connectedRi;
	}
	
}
