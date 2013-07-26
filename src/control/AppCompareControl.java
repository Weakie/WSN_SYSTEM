package control;

import java.util.List;
import java.util.Map;

public interface AppCompareControl {

	/**
	 * ��ʾ����ͼ-->fit,cov,dis,time
	 * @param results
	 */
	public void showCompLineCharts(String title,String xName,Map<String,Map<String,List<Double>>> results);
	/**
	 * ��ʾ��״ͼ
	 * @param title
	 * @param xName
	 * @param results
	 */
	public void showCompBarCharts(String title,String xName,Map<String,Map<String,Map<String,Double>>> results);
	//�Ƴ����л��õĽ��
	public void removeAll();
	//�Ƿ�ɼ�
	public void setVisable(boolean visable);
}
