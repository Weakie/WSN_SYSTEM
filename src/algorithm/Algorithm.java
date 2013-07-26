package algorithm;

import node.NodeList;

public interface Algorithm{
	
	public void setScaleNum(int scale);
	public void setLoopNum(int loop);
	public int getScaleNum();
	public int getLoopNum();
	
	public String getName();
	
	public Param getRunParams();
	public Result getRunResult();
	public void runAlgorithm();
	public void getResult(NodeList nodeList);
	public long getTotalRunTime();
	
	public double[] getGlobalBestFit();
	public double[] getGlobalBestDis();
	public double[] getGlobalBestCov();
	
	public double getBestFit();
	public double getBestDis();
	public double getBestCov();
}
