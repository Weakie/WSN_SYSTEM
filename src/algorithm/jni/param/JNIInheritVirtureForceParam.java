package algorithm.jni.param;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.InheritMethodCalcuProb;
import model.InheritMethodCross;
import model.InheritMethodMutate;
import model.InheritMethodSelect;

import algorithm.Param;
import algorithm.ParamConvertAdapter;

public class JNIInheritVirtureForceParam extends Param {

	// for Inherit
	private int choiceMethod = 2;// 0-2
	private int selectMethod = 1;// 0-3
	private int crossMethod = 4;// 0-4
	private int mutateMethod = 4;// 0-7

	private int k = 10;
	private double q = 0.5;
	private double a = 2;
	private double b = 1;
	private double pc = 0.6;
	private double pm = 0.1;

	// for fitness
	private double f1 = 1;
	private double f2 = 0;// f2 = 0,不考虑移动距离

	// for virture force
	private double c3 = 0.4;// w,c1,c2=0,c3=160仅仅为虚拟力算法

	private JNIInheritVirtureForceParam getSelf(){
		return this;
	}
	private class JNIInheritVirtureForceParamConvert implements ParamConvertAdapter {
		@Override
		public Map<String, Object> toMap() {
			Map<String,Object> map = new HashMap<String,Object>();
			Field[] fields = JNIInheritVirtureForceParam.class.getDeclaredFields();
			for(Field field:fields){
				try {
					field.setAccessible(true);
					if(field.getType().equals(Integer.TYPE))
						map.put(field.getName(), field.getInt(getSelf()));
					else if(field.getType().equals(Double.TYPE))
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
			Field[] fields = JNIInheritVirtureForceParam.class.getDeclaredFields();
			for(Field field:fields){
				try {
					String fieldStr = (String) param.get(field.getName());
					if(fieldStr != null){
						field.setAccessible(true);
						if(field.getType().equals(Integer.TYPE)){
							field.setInt(getSelf(), Integer.parseInt(fieldStr));
						}else if(field.getType().equals(Double.TYPE)){
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
					"适应度函数",
					"选择算子",
					"交叉算子",
					"变异算子",
					"k","q","a","b","pc","pm","f1","f2","c3"};
			return Arrays.asList(toolTip);
		}

		@Override
		public List<Object> getType() {
			Object[] type = {
					new InheritMethodCalcuProb(),
					new InheritMethodSelect(),
					new InheritMethodCross(),
					new InheritMethodMutate(),
					null,null,null,null,null,null,null,null,null};
			return Arrays.asList(type);
		}

		@Override
		public List<String> getKeyByIndex() {
			String[] key = {
					"choiceMethod",
					"selectMethod",
					"crossMethod",
					"mutateMethod",
					"k","q","a","b","pc","pm","f1","f2","c3"};
			return Arrays.asList(key);
		}
	}

	@Override
	public ParamConvertAdapter getConvert() {
		return new JNIInheritVirtureForceParamConvert();
	}
	
	public String toString(){
		return "\n  choiceProbMethod="+new InheritMethodCalcuProb().getItem(choiceMethod)+
				"\n  selectMethod="+new InheritMethodSelect().getItem(selectMethod)+
				"\n  crossMethod="+new InheritMethodCross().getItem(crossMethod)+
				"\n  mutateMethod="+new InheritMethodMutate().getItem(mutateMethod)+
				"\n  k="+k+",  q="+q+"\n  a="+a+",  b="+b+"\n  pc="+pc+",  pm="+pm+"\n  f1="+f1+",  f2="+f2+"\n  c3="+c3;
	}
}
