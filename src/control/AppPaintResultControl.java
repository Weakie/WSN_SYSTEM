package control;

import java.util.List;
import java.util.Map;

public interface AppPaintResultControl {

	/**
	 * ��ʾ����ͼ-->fit,cov,dis,time
	 * @param results
	 */
	public void showLineCharts(String title,String xName,Map<String,Map<String,List<Double>>> results);
	/**
	 * ��ʾ��״ͼ
	 * @param title
	 * @param xName
	 * @param results
	 */
	public void showBarCharts(String title,String xName,Map<String,List<Double>> result);
	//�Ƴ����л��õĽ��
	public void removeAll();
	//�Ƿ�ɼ�
	public void setVisable(boolean visable);
}
