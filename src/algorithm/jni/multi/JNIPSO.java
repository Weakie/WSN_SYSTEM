package algorithm.jni.multi;

import global.GlobalMainControl;
import algorithm.AbstractAlgorithm;
import algorithm.Param;
import algorithm.jni.param.JNIPSOParam;
/**
 * 目前和JNIPSOVirtureForce实现原理一样
 * @author dell
 *
 */
public class JNIPSO extends AbstractAlgorithm{
	
	//static
	private static String algorothmName = "PSO";
	//native
	private native void runNativePSO(int parSize,int runNum);
	static{
		System.loadLibrary("JNIPSO");
	}
	// register
	static{
		GlobalMainControl.algorithms.put(algorothmName, JNIPSO.class.getName());
	}
	
	//
	public JNIPSO(){
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
	private JNIPSOParam param = new JNIPSOParam();
	
	@Override
	public Param getRunParams() {
		return param;
	}

}