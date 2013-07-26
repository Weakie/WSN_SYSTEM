package algorithm.jni.param;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Param;
import algorithm.ParamConvertAdapter;

public class JNICAPSOParam extends Param {

	private double wMax = 0.9;
	private double wMin = 0.4;
	// for CAPSO
	private double c1Begin = 2.25;
	private double c2Begin = 0.75;
	private double c1End = 0.75;
	private double c2End = 1.25;

	// for fitness
	private double f1 = 1;
	private double f2 = 0;// f2 = 0,²»¿¼ÂÇÒÆ¶¯¾àÀë
	
	private JNICAPSOParam getSelf(){
		return this;
	}
	private class JNICAPSOParamConvert implements ParamConvertAdapter {
		@Override
		public Map<String, Object> toMap() {
			Map<String,Object> map = new HashMap<String,Object>();
			Field[] fields = JNICAPSOParam.class.getDeclaredFields();
			for(Field field:fields){
				try {
					field.setAccessible(true);
					if(field.getType().equals(Double.TYPE))
						map.put(field.getName(), field.getDouble(getSelf()));
					field.setAccessible(false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return map;
		}

		@Override
		public void fromMap(Map<String, Object> param) {
			Field[] fields = JNICAPSOParam.class.getDeclaredFields();
			for(Field field:fields){
				try {
					String fieldStr = (String) param.get(field.getName());
					if(fieldStr != null){
						field.setAccessible(true);
						if(field.getType().equals(Double.TYPE)){
							field.setDouble(getSelf(), Double.parseDouble(fieldStr));
						}
						field.setAccessible(false);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void toFile(String fileName) {
			// TODO Auto-generated method stub

		}

		@Override
		public void fromFile(String fileName) {
			// TODO Auto-generated method stub

		}

		@Override
		public List<String> getToopTip() {
			String[] toolTip = {
					"wMax",
					"wMin",
					"c1Begin",
					"c1End",
					"c2Begin",
					"c2End",
					"f1",
					"f2"
			};
			return Arrays.asList(toolTip);
		}

		@Override
		public List<Object> getType() {
			Object[] type = new Object[8];
			return Arrays.asList(type);
		}

		@Override
		public List<String> getKeyByIndex() {
			String[] key = {"wMax","wMin","c1Begin","c1End","c2Begin","c2End","f1","f2"};
			return Arrays.asList(key);
		}
	}

	@Override
	public ParamConvertAdapter getConvert() {
		return new JNICAPSOParamConvert();
	}
	
	public String toString(){
		return "\n  wMax="+wMax+"\n  wMin="+wMin+"\n  c1Begin="+c1Begin+
				"\n  c1End="+c1End+"\n  c2Begin="+c2Begin+"\n c2End="+c2End+"\n  f1="+f1+"\n  f2="+f2;
	}
}
