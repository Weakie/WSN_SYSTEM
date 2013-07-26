package node;

public interface Node extends PaintedNode,java.io.Serializable{
	/**
	 * ��ȡ���Ӱ뾶
	 * @return
	 */
	public double getConnectedRi();
	/**
	 * �õ�X����
	 * @return
	 */
	public double getPosX();
	/**
	 * �õ�Y����
	 * @return
	 */
	public double getPosY();
	/**
	 * ����X����
	 * @param x
	 */
	public void setPosX(double x);
	/**
	 * ����Y����
	 * @param y
	 */
	public void serPosY(double y);
	/**
	 * �õ���Ŀ���֮��ľ���
	 * @param x
	 * @param y
	 * @return
	 */
	public double getDistance(double x,double y);
	
	/**
	 * �õ���ڵ�node֮��ľ���
	 * @param node
	 * @return
	 */
	public double getDistance(Node node);
	/**
	 * �õ���Ŀ���ĸ�֪����
	 * @param x
	 * @param y
	 * @return
	 */
	public double getSensedPi(double x,double y);
	/**
	 * �ж�Ŀ����Ƿ��ڸв�������
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInSensedRegion(double x,double y);
	/**
	 * �����ڵ��Ƿ�����ͨ��
	 * @param node
	 * @return
	 */
	public boolean isConnected(Node node);

}
