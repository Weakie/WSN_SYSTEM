package algorithm.jni.param;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Param;
import algorithm.ParamConvertAdapter;

public class JNIAPSOParam extends Param {

	// for APSO
	private double wMax = 0.9;
	private double wMin = 0.4;

	private double c1 = 1; // 1-vf
	private double c2 = 1; // 1-vf

	// for fitness
	private double f1 = 1;
	private double f2 = 0;// f2 = 0,不考虑移动距离
	
	private JNIAPSOParam getSelf(){
		return this;
	}
	private class JNIAPSOParamConvert implements ParamConvertAdapter {
		@Override
		public Map<String, Object> toMap() {
			Map<String,Object> map = new HashMap<String,Object>();
			Field[] fields = JNIAPSOParam.class.getDeclaredFields();
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
			Field[] fields = JNIAPSOParam.class.getDeclaredFields();
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
					"w最大值",
					"w最小值",
					"c1",
					"c2",
					"f1",
					"f2"
			};
			return Arrays.asList(toolTip);
		}

		@Override
		public List<Object> getType() {
			Object[] type = new Object[6];
			return Arrays.asList(type);
		}

		@Override
		public List<String> getKeyByIndex() {
			String[] key = {"wMax","wMin","c1","c2","f1","f2"};
			return Arrays.asList(key);
		}
	}

	@Override
	public ParamConvertAdapter getConvert() {
		return new JNIAPSOParamConvert();
	}
	
	public String toString(){
		return "\n  wMax="+wMax+"\n  wMin="+wMin+
				"\n  c1="+c1+"\n  c2="+c2+"\n  f1="+f1+"\n  f2="+f2;
	}
}
