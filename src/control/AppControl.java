package control;

/**
 * �㷨�����ڼ����MainFrame��������
 * @author dell
 *
 */
public interface AppControl {
	//run
	public void setEnable(boolean enable);
	public void initProgressBar(int max);
	public void addProgressBar();
	public void initPrintPanel();
	public void print(String target,String message);
	public void setLabelRunning(String name);
}
