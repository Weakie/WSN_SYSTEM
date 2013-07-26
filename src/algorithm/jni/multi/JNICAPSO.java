package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNICAPSOParam;

public class JNICAPSO extends AbstractAlgorithm {

	//static
	private static String algorothmName = "CAPSO";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNICAPSO");
	}
	// register
	static {
		GlobalMainControl.algorithms.put(algorothmName,JNICAPSO.class.getName());
	}
	
	
	public JNICAPSO(){
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
	private JNICAPSOParam param = new JNICAPSOParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
}
