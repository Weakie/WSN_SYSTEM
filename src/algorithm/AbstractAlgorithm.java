package algorithm;

import global.GlobalVariables;
import node.NodeList;
import view.PaintPanel;

public abstract class AbstractAlgorithm implements Algorithm {
	//
	protected int scaleSize,loopNum;
	public void setScaleNum(int scale){
		scaleSize = scale;
	}
	public void setLoopNum(int loop){
		loopNum = loop;
	}
	public int getScaleNum(){
		return scaleSize;
	}
	public int getLoopNum(){
		return loopNum;
	}
	//
	protected Result result = new Result();
	protected boolean showMidResult = false;
	//
	private long runTime = 0;
	private PaintPanel midResult;
	private NodeList midNodeList;
	//
	public AbstractAlgorithm(){}

	@Override
	public Result getRunResult(){
		return this.result;
	}
	
	@Override
	public final void runAlgorithm() {
		long begin,end;
		begin = System.currentTimeMillis();
		run();
		end = System.currentTimeMillis();
		runTime = end - begin;
	}

	@Override
	public void getResult(NodeList nodeList) {
		nodeList.updateNodePos(result.bestPos);
	}

	@Override
	public long getTotalRunTime() {
		return this.runTime;
	}
	
	@Override
	public double getBestFit() {
		double[] bestFit = this.result.globalBestFit;
		double best = -1;
		for(double fit:bestFit){
			best = Math.max(best, fit);
		}
		return best;
	}

	@Override
	public double getBestDis() {
		double[] bestDis = this.result.globalBestDis;
		double best = bestDis[0];
		for(double dis:bestDis){
			best = Math.min(best, dis);
		}
		return best;
	}

	@Override
	public double getBestCov() {
		double[] bestCov = this.result.globalBestCov;
		double best = -1;
		for(double cov:bestCov){
			best = Math.max(best, cov);
		}
		return best;
	}
	
	public double[] getGlobalBestFit(){
		return this.result.globalBestFit;
	}
	public double[] getGlobalBestDis(){
		return this.result.globalBestDis;
	}
	public double[] getGlobalBestCov(){
		return this.result.globalBestCov;
	}
	
	/**
	 * 设置是否显示中间结果
	 */
	public void notShowMidResult(){
		this.showMidResult = false;
	}
	
	public void showMidResult(PaintPanel panel,NodeList nodeList){
		this.showMidResult = true;
		this.midResult = panel;
		this.midNodeList = nodeList;
	}
	
	/**
	 * 给子类调用的方法
	 */
	protected void showMidResult(){
		if(showMidResult){
			midNodeList.updateNodePos(result.bestPos);
			midResult.draw(midNodeList, GlobalVariables.PaintSize);
		}
	}
	
	abstract protected void run();
	

}
