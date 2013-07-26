package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIPSOVirtureForceParam;

public class JNIPSOVirtureForce extends AbstractAlgorithm{

	//static
	private static String algorothmName = "PSO Virture Force";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNIPSOVF");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName, JNIPSOVirtureForce.class.getName());
	}

	//
	public JNIPSOVirtureForce(){
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
	private JNIPSOVirtureForceParam param = new JNIPSOVirtureForceParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	

}
