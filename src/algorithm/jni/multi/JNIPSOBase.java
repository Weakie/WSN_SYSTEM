package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIPSOBaseParam;

public class JNIPSOBase extends AbstractAlgorithm{
	
	//static
	private static String algorothmName = "Base PSO";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNIPSOBase");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName, JNIPSOBase.class.getName());
	}
	
	//
	public JNIPSOBase(){
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
	private JNIPSOBaseParam param = new JNIPSOBaseParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	
}
