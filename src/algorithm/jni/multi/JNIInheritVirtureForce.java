package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIInheritVirtureForceParam;

public class JNIInheritVirtureForce extends AbstractAlgorithm {

	//static
	private static String algorothmName = "GA Virture Force";
	//native
	private native void runNativeInherit(int popSize,int runNum);
	static{
		System.loadLibrary("JNIINHERITVF");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName, JNIInheritVirtureForce.class.getName());
	}
	
	//
	public JNIInheritVirtureForce(){
		super();
	}
	
	@Override
	protected void run(){
		runNativeInherit(scaleSize,loopNum);
	}

	@Override
	public String getName() {
		return algorothmName;
	}


	//run param
	private JNIInheritVirtureForceParam param = new JNIInheritVirtureForceParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	
}
