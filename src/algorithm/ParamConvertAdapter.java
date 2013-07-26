package algorithm;

import java.util.List;
import java.util.Map;

public interface ParamConvertAdapter {
	public Map<String,Object> toMap(); 
	public void fromMap(Map<String,Object> param);
	public void toFile(String fileName);
	public void fromFile(String fileName);
	public List<String> getToopTip();
	public List<Object> getType();
	public List<String> getKeyByIndex();
}
