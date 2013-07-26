package control;

import java.util.List;
import java.util.Map;

public interface AppDrawResultControl {
	//画所有结果
	public void drawResults(String name,List<Map<String,Object>> results);
	//移除所有画好的结果
	public void removeAll();
	//是否可见
	public void setVisable(boolean visable);
}
