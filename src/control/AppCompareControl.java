package control;

import java.util.List;
import java.util.Map;

public interface AppCompareControl {

	/**
	 * 显示曲线图-->fit,cov,dis,time
	 * @param results
	 */
	public void showCompLineCharts(String title,String xName,Map<String,Map<String,List<Double>>> results);
	/**
	 * 显示柱状图
	 * @param title
	 * @param xName
	 * @param results
	 */
	public void showCompBarCharts(String title,String xName,Map<String,Map<String,Map<String,Double>>> results);
	//移除所有画好的结果
	public void removeAll();
	//是否可见
	public void setVisable(boolean visable);
}
