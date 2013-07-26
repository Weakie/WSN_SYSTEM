package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNICAPSOVirtureForceParam;

public class JNICAPSOVirtureForce extends AbstractAlgorithm{

	//static
	private static String algorothmName = "CAPSO Virture Force";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNICAPSOVF");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName, JNICAPSOVirtureForce.class.getName());
	}

	//
	public JNICAPSOVirtureForce(){
		super();
	}
	
	@Override
	protected void run(){
		runNativePSO(scaleSize,loopNum);
	}

	@Override
	public String getName() {
		return algorothmName;
	}
	

	//run param
	private JNICAPSOVirtureForceParam param = new JNICAPSOVirtureForceParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	

}
