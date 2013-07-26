package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIInheritBaseParam;

public class JNIInheritBase extends AbstractAlgorithm {

	//static
	private static String algorothmName = "Base GA";
	//native
	private native void runNativeInherit(int popSize,int runNum);
	static{
		System.loadLibrary("JNIINHERITBASE");
	}
	// register
	static {
		GlobalMainControl.algorithms.put(algorothmName,JNIInheritBase.class.getName());
	}

	public JNIInheritBase(){
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
	private JNIInheritBaseParam param = new JNIInheritBaseParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	
}
