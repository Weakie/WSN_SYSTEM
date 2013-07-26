package global;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import node.NodeList;

import org.eclipse.swt.widgets.Display;

import algorithm.Algorithm;
import constants.AlgorithmConstants;
import constants.ChartConstants;
import control.AppCompareControl;
import control.AppControl;
import control.AppDrawResultControl;
import control.AppPaintResultControl;


public class GlobalMainControl {
	// initialize algorithms
	public static Map<String, String> algorithms = new HashMap<String, String>();
	static {
		String algorotmClassName = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("algorithm.properties"));
			while ((algorotmClassName = reader.readLine()) != null) {
				try {
					Class.forName(algorotmClassName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// instance
	private static GlobalMainControl instance = new GlobalMainControl();
	private GlobalMainControl(){}
	public static GlobalMainControl getInstance(){
		return instance;
	}
	//
	private HashMap<String,Algorithm> runAlgorim = new HashMap<String,Algorithm>();
	private HashMap<String,List<Map<String,Object>>> result = new HashMap<String,List<Map<String,Object>>>(); 
	private int algorimRunTime = 1;
	private volatile boolean stopFlag = false;
	
	// ���д���
	public int getAlgorithmRunTime() {
		return algorimRunTime;
	}

	public void setAlgorithmRunTime(int value) {
		algorimRunTime = value;
	}
	// �㷨����
	public void addAlgorithm(Map<String,Object> param) throws Exception{
		String name = (String) param.get(AlgorithmConstants.ALGORITHM_NAME);
		String type = (String) param.get(AlgorithmConstants.ALGORITHM_TYPE);
		int loopNum = (Integer) param.get(AlgorithmConstants.ALGORITHM_LOOPNUM);
		int scaleNum = (Integer) param.get(AlgorithmConstants.ALGORITHM_SCALE);
		String className = algorithms.get(type);
		try {
			Algorithm algo = (Algorithm) Class.forName(className).newInstance();
			algo.setLoopNum(loopNum);
			algo.setScaleNum(scaleNum);
			if(runAlgorim.containsKey(name)){
				name = name+"_1";
			}
			runAlgorim.put(name, algo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<String> getConfigedAlgorithmNames(){
		return this.runAlgorim.keySet();
	}
	
	public Algorithm getAlgorithm(String name){
		return this.runAlgorim.get(name);
	}
	
	public void deleteAlgorithm(String name){
		this.runAlgorim.remove(name);
	}
	//������
	public void saveRunResult(String fileName) throws Exception{
		PrintWriter writer = new PrintWriter(fileName);
		Set<String> algoName = result.keySet();
		for(String name : algoName){
			writer.println("----------------------");
			writer.println("Name : " + name);
			writer.println(String.format("%1$3s  %2$10s  %3$10s  %4$10s  %5$10s", " ","runTime","bestFit","bestCov","bestDis"));
			List<Map<String,Object>> results = result.get(name);
			int i = 0;
			for(Map<String,Object> map : results){
				long runTime = (Long) map.get(AlgorithmConstants.RESULT_RUNTIME);
				double bestFit = (Double) map.get(AlgorithmConstants.RESULT_BESTFIT);
				double bestCov = (Double) map.get(AlgorithmConstants.RESULT_BESTCOV);
				double bestDis = (Double) map.get(AlgorithmConstants.RESULT_BESTDIS);
				writer.println(String.format("%1$3d  %2$10d  %3$10.6f  %4$10.6f  %5$10.2f", i++,runTime,bestFit,bestCov,bestDis));
			}
		}
		writer.close();
	}
	
	public void saveRunResultSeri(String fileName) throws Exception{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		NodeList.write(out);
		GlobalVariables.write(out);
		out.writeObject(result);
		out.close();
	}
	
	//��ȡ
	@SuppressWarnings("unchecked")
	public void readRunResultSeri(String fileName) throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		NodeList.read(in);
		GlobalVariables.read(in);
		result = (HashMap<String, List<Map<String, Object>>>) in.readObject();
		in.close();
	}
	
	// ����
	public void runAlgorihm(List<String> algoName,AppControl control) throws Exception{
		control.initProgressBar(algoName.size()*algorimRunTime);
		control.initPrintPanel();
		new Thread(new MyThread(algoName,control)).start();
	}
	public void stop(){
		this.stopFlag = true;
	}
	// �õ����
	public Set<String> getResultNames(){
		return this.result.keySet();
	}
	public HashMap<String,List<Map<String,Object>>> getResult(){
		return this.result;
	}
	public List<Map<String,Object>> getResult(String name){
		return this.result.get(name);
	}
	public void deleteAlgoResult(String name){
		this.result.remove(name);
	}
	public void drawNodesResult(List<String> algoName,AppDrawResultControl control) throws Exception{
		control.setVisable(false);
		control.removeAll();
		for(String name : algoName){
			control.drawResults(name,result.get(name));
		}
		control.setVisable(true);
	}
	// �õ����
	/**
	 * �������㷨��ÿ�����ֵ
	 * @param algoName
	 * @param control
	 */
	public void paintAlgoResult(String algoName,AppPaintResultControl control) throws Exception{
		//
		Map<String,List<Double>> paintResultsFit = new HashMap<String,List<Double>>();
		Map<String,List<Double>> paintResultsDis = new HashMap<String,List<Double>>();
		Map<String,List<Double>> paintResultsCov = new HashMap<String,List<Double>>();
		//
		List<Map<String,Object>> algoResult = result.get(algoName);
		int i = 0;
		for(Map<String,Object> result:algoResult){
			//
			double[] fits = (double[]) result.get(AlgorithmConstants.RESULT_BESTFITS);
			List<Double> fitList = new ArrayList<Double>();
			for(double v:fits){
				fitList.add(v*100);
			}
			paintResultsFit.put("��"+i+"��",fitList);
			//
			double[] covs = (double[]) result.get(AlgorithmConstants.RESULT_BESTCOVS);
			List<Double> covList = new ArrayList<Double>();
			for(double v:covs){
				covList.add(v*100);
			}
			paintResultsCov.put("��"+i+"��",covList);
			//
			double[] diss = (double[]) result.get(AlgorithmConstants.RESULT_BESTDISS);
			List<Double> disList = new ArrayList<Double>();
			for(double v:diss){
				disList.add(v);
			}
			paintResultsDis.put("��"+i+"��",disList);
			i++;
		}
		//
		Map<String,Map<String,List<Double>>> preparedResult = new HashMap<String,Map<String,List<Double>>>();
		preparedResult.put(ChartConstants.CHART_FIT, paintResultsFit);
		preparedResult.put(ChartConstants.CHART_COV, paintResultsCov);
		preparedResult.put(ChartConstants.CHART_DIS, paintResultsDis);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showLineCharts(algoName+"ÿ�����ֵ�仯����","��������",preparedResult);
		control.setVisable(true);
	}
	/**
	 * �����д�����ʾ�����㷨��״ͼ
	 * @param algoName
	 * @param control
	 */
	public void paintAlgoResultByRuntime(String algoName,AppPaintResultControl control) throws Exception{
		//
		List<Double> paintResultsFit = new ArrayList<Double>();
		List<Double> paintResultsDis = new ArrayList<Double>();
		List<Double> paintResultsCov = new ArrayList<Double>();
		//
		List<Map<String,Object>> algoResult = result.get(algoName);
		for(Map<String,Object> result:algoResult){
			double fit = (Double) result.get(AlgorithmConstants.RESULT_BESTFIT);
			paintResultsFit.add(fit*100);
			double cov = (Double) result.get(AlgorithmConstants.RESULT_BESTCOV);
			paintResultsCov.add(cov*100);
			double dis = (Double) result.get(AlgorithmConstants.RESULT_BESTDIS);
			paintResultsDis.add(dis);
		}
		//
		Map<String,List<Double>> preparedResult = new HashMap<String,List<Double>>();
		preparedResult.put(ChartConstants.CHART_FIT, paintResultsFit);
		preparedResult.put(ChartConstants.CHART_COV, paintResultsCov);
		preparedResult.put(ChartConstants.CHART_DIS, paintResultsDis);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showBarCharts(algoName+"ÿ�����н���仯����","���д���",preparedResult);
		control.setVisable(true);
	}
	// �Ƚ��㷨
	/**
	 * �����㷨���д����Ƚ��㷨
	 */
	public void compareAlgoByRunTime(List<String> algoName,AppCompareControl control) throws Exception{
		//prepare
		Map<String,List<Double>> fits = CompareAlgorithmUtil.getResultByRunTime(algoName, AlgorithmConstants.RESULT_BESTFIT);
		Map<String,List<Double>> covs = CompareAlgorithmUtil.getResultByRunTime(algoName, AlgorithmConstants.RESULT_BESTCOV);
		Map<String,List<Double>> diss = CompareAlgorithmUtil.getResultByRunTime(algoName, AlgorithmConstants.RESULT_BESTDIS);
		Map<String,List<Double>> time = CompareAlgorithmUtil.getResultByRunTime(algoName, AlgorithmConstants.RESULT_RUNTIME);
		//put by type name
		Map<String,Map<String,List<Double>>> preparedResult = new HashMap<String,Map<String,List<Double>>>();
		preparedResult.put(ChartConstants.CHART_FIT, fits);
		preparedResult.put(ChartConstants.CHART_COV, covs);
		preparedResult.put(ChartConstants.CHART_DIS, diss);
		preparedResult.put(ChartConstants.CHART_TIME, time);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showCompLineCharts("�����д����仯����","���д���",preparedResult);
		control.setVisable(true);
	}
	/**
	 * ���ݵ��������Ƚϵ�һ���㷨���е� fit cov dis����ÿ�����ֵ�仯����
	 * @author dell
	 *
	 */
	public void compareAlgoByLoop(List<String> algoName,AppCompareControl control) throws Exception{
		//prepare
		Map<String,List<Double>> fits = CompareAlgorithmUtil.getResultByLoop(algoName, AlgorithmConstants.RESULT_BESTFITS);
		Map<String,List<Double>> covs = CompareAlgorithmUtil.getResultByLoop(algoName, AlgorithmConstants.RESULT_BESTCOVS);
		Map<String,List<Double>> diss = CompareAlgorithmUtil.getResultByLoop(algoName, AlgorithmConstants.RESULT_BESTDISS);
		//put by type name
		Map<String,Map<String,List<Double>>> preparedResult = new HashMap<String,Map<String,List<Double>>>();
		preparedResult.put(ChartConstants.CHART_FIT, fits);
		preparedResult.put(ChartConstants.CHART_COV, covs);
		preparedResult.put(ChartConstants.CHART_DIS, diss);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showCompLineCharts("ÿ�����ֵ�仯����","��������",preparedResult);
		control.setVisable(true);
	}
	/**
	 * ���ݵ��������Ƚϵ�һ���㷨���е� fit cov dis��������
	 * @author dell
	 *
	 */
	public void compareAlgoByLoopMax(List<String> algoName,AppCompareControl control) throws Exception{
		//prepare
		Map<String,List<Double>> fits = CompareAlgorithmUtil.getResultByLoopMax(algoName, AlgorithmConstants.RESULT_BESTFITS);
		Map<String,List<Double>> covs = CompareAlgorithmUtil.getResultByLoopMax(algoName, AlgorithmConstants.RESULT_BESTCOVS);
		//put by type name
		Map<String,Map<String,List<Double>>> preparedResult = new HashMap<String,Map<String,List<Double>>>();
		preparedResult.put(ChartConstants.CHART_FIT, fits);
		preparedResult.put(ChartConstants.CHART_COV, covs);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showCompLineCharts("��������","��������",preparedResult);
		control.setVisable(true);
	}
	/**
	 * ��������,���,ƽ��,������,�Ƚ��㷨
	 * @author dell
	 */
	public void compareAlgoByCategory(List<String> algoName,AppCompareControl control) throws Exception{
		//prepare
		Map<String,Map<String,Double>> fit = CompareAlgorithmUtil.getResultByCategory(algoName, AlgorithmConstants.RESULT_BESTFIT);
		Map<String,Map<String,Double>> cov = CompareAlgorithmUtil.getResultByCategory(algoName, AlgorithmConstants.RESULT_BESTCOV);
		Map<String,Map<String,Double>> dis = CompareAlgorithmUtil.getResultByCategory(algoName, AlgorithmConstants.RESULT_BESTDIS);
		Map<String,Map<String,Double>> time = CompareAlgorithmUtil.getResultByCategory(algoName, AlgorithmConstants.RESULT_RUNTIME);
		//put by type name
		Map<String,Map<String,Map<String,Double>>> preparedResult = new HashMap<String,Map<String,Map<String,Double>>>();
		preparedResult.put(ChartConstants.CHART_FIT, fit);
		preparedResult.put(ChartConstants.CHART_COV, cov);
		preparedResult.put(ChartConstants.CHART_DIS, dis);
		preparedResult.put(ChartConstants.CHART_TIME, time);
		//show charts
		control.setVisable(false);
		control.removeAll();
		control.showCompBarCharts("���š���� ��ƽ�� ��������仯����","����",preparedResult);
		control.setVisable(true);
	}
	// �����߳�
	private class MyThread implements Runnable{
		private AppControl control;
		private List<String> algoName;
		private int algoRunTime = algorimRunTime;
		public MyThread(List<String> algoName,AppControl control){
			this.algoName = algoName;
			this.control = control;
		}
		@Override
		public void run() {
			setEnable(false);
			stopFlag = false;
			for(String name:algoName){
				this.setRunningAlgoName(name);
				int count = 0;
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				
				while(!stopFlag&&count++<algoRunTime){
					this.print(AlgorithmConstants.PRINT_CONSOLE,new Date() + ": Begin "+name+","+count);
					try{
						Algorithm algo = runAlgorim.get(name);
						//Reset Parameter's Node property
						algo.getRunParams().resetNodeProperty();
						algo.runAlgorithm();
						NodeList nodeList = new NodeList();
						algo.getResult(nodeList);
						//store result
						Map<String,Object> map = new HashMap<String,Object>();
						map.put(AlgorithmConstants.RESULT_NODELIST,nodeList);
						map.put(AlgorithmConstants.RESULT_RUNTIME,algo.getTotalRunTime());
						map.put(AlgorithmConstants.RESULT_BESTFITS,algo.getGlobalBestFit());
						map.put(AlgorithmConstants.RESULT_BESTCOVS,algo.getGlobalBestCov());
						map.put(AlgorithmConstants.RESULT_BESTDISS,algo.getGlobalBestDis());
						map.put(AlgorithmConstants.RESULT_BESTFIT,algo.getBestFit());
						map.put(AlgorithmConstants.RESULT_BESTCOV,algo.getBestCov());
						map.put(AlgorithmConstants.RESULT_BESTDIS,algo.getBestDis());
						list.add(map);
						this.print(AlgorithmConstants.PRINT_CONSOLE,new Date() +  ": End "+name+","+count+" Time:"+algo.getTotalRunTime()+"\n");
						this.print(AlgorithmConstants.PRINT_OUTPUT,new Date() +  "\n"+ name+":\n"+"  Best Fit:"+algo.getBestFit()+
							"\n  Best Cov:"+algo.getBestCov()+"\n  Best Dis:"+algo.getBestDis()+"\n");
					}catch(Throwable e){
						this.print(AlgorithmConstants.PRINT_CONSOLE,new Date() + "\n" + e.toString());
					}
					this.addProgressBae();
				}
				result.put(name, list);
			}
			stopFlag = true;
			setEnable(true);
		}
		
		private void setRunningAlgoName(final String name){
			Display.getDefault().syncExec(new Runnable(){
				@Override
				public void run() {
					control.setLabelRunning(name);
				}
			});
		}
		private void setEnable(final boolean enable){
			Display.getDefault().syncExec(new Runnable(){
				@Override
				public void run() {
					control.setEnable(enable);
				}
			});
		}
		private void addProgressBae(){
			Display.getDefault().syncExec(new Runnable(){
				@Override
				public void run() {
					control.addProgressBar();
				}
			});
		}
		private void print(final String type,final String message){
			Display.getDefault().syncExec(new Runnable(){
				@Override
				public void run() {
					control.print(type,message);
				}
			});
		}
		
	}
}
