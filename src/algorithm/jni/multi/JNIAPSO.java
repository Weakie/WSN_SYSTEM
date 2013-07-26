package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIAPSOParam;

public class JNIAPSO extends AbstractAlgorithm {
	
	//static
	private static String algorothmName = "APSO";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNIAPSO");
	}
	// register
	static {
		GlobalMainControl.algorithms.put(algorothmName,JNIAPSO.class.getName());
	}
	
	
	public JNIAPSO(){
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
	private JNIAPSOParam param = new JNIAPSOParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	
}
