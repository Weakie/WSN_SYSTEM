package node;

public interface Node extends PaintedNode,java.io.Serializable{
	/**
	 * 获取连接半径
	 * @return
	 */
	public double getConnectedRi();
	/**
	 * 得到X坐标
	 * @return
	 */
	public double getPosX();
	/**
	 * 得到Y坐标
	 * @return
	 */
	public double getPosY();
	/**
	 * 设置X坐标
	 * @param x
	 */
	public void setPosX(double x);
	/**
	 * 设置Y坐标
	 * @param y
	 */
	public void serPosY(double y);
	/**
	 * 得到与目标点之间的距离
	 * @param x
	 * @param y
	 * @return
	 */
	public double getDistance(double x,double y);
	
	/**
	 * 得到与节点node之间的距离
	 * @param node
	 * @return
	 */
	public double getDistance(Node node);
	/**
	 * 得到对目标点的感知概率
	 * @param x
	 * @param y
	 * @return
	 */
	public double getSensedPi(double x,double y);
	/**
	 * 判断目标点是否在感测区域内
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInSensedRegion(double x,double y);
	/**
	 * 两个节点是否是连通的
	 * @param node
	 * @return
	 */
	public boolean isConnected(Node node);

}
