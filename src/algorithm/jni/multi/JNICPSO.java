package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNICPSOParam;

public class JNICPSO extends AbstractAlgorithm{
	
	//static
	private static String algorothmName = "CPSO";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNICPSO");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName,JNICPSO.class.getName());
	}
	
	//
	public JNICPSO(){
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
	private JNICPSOParam param = new JNICPSOParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}
	
}
