package algorithm.jni.param;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Param;
import algorithm.ParamConvertAdapter;

public class JNIPSOParam extends Param {
	// for Particle
	private double w = 0.8;
	private double c1 = 1; // 1-vf
	private double c2 = 1; // 1-vf
	// for virture force
	private double c3 = 160;// w,c1,c2=0,c3=160仅仅为虚拟力算法
	// for fitness
	private double f1 = 1;
	private double f2 = 0;// f2 = 0,不考虑移动距离
	
	private JNIPSOParam getSelf(){
		return this;
	}
	private class JNIPSOParamConvert implements ParamConvertAdapter {
		@Override
		public Map<String, Object> toMap() {
			Map<String,Object> map = new HashMap<String,Object>();
			Field[] fields = JNIPSOParam.class.getDeclaredFields();
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
			Field[] fields = JNIPSOParam.class.getDeclaredFields();
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
					"w",
					"c1",
					"c2",
					"c3",
					"f1",
					"f2"};
			return Arrays.asList(toolTip);
		}

		@Override
		public List<Object> getType() {
			Object[] type = {null,null,null,null,null,null};
			return Arrays.asList(type);
		}

		@Override
		public List<String> getKeyByIndex() {
			String[] key = {
					"w",
					"c1",
					"c2",
					"c3",
					"f1",
					"f2"};
			return Arrays.asList(key);
		}
	}

	@Override
	public ParamConvertAdapter getConvert() {
		return new JNIPSOParamConvert();
	}
	
	public String toString(){
		return "\n  w="+w+"\n  c1="+c1+"\n  c2="+c2+"\n  c3="+c3+"\n  f1="+f1+"\n  f2="+f2;
	}
}
