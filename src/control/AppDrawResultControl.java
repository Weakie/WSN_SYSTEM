package control;

import java.util.List;
import java.util.Map;

public interface AppDrawResultControl {
	//�����н��
	public void drawResults(String name,List<Map<String,Object>> results);
	//�Ƴ����л��õĽ��
	public void removeAll();
	//�Ƿ�ɼ�
	public void setVisable(boolean visable);
}
