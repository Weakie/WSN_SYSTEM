package global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.AlgorithmConstants;
import constants.ChartConstants;

public class CompareAlgorithmUtil {

	//Y Name
	public static final	Map<String,String> yNames = new HashMap<String,String>();
	static{
		yNames.put(ChartConstants.CHART_FIT, ChartConstants.CHART_Y_NAME_FIT);
		yNames.put(ChartConstants.CHART_COV, ChartConstants.CHART_Y_NAME_COV);
		yNames.put(ChartConstants.CHART_DIS, ChartConstants.CHART_Y_NAME_DIS);
		yNames.put(ChartConstants.CHART_TIME, ChartConstants.CHART_Y_NAME_TIME);
	}
	private static final Map<String,Double> valueTransform = new HashMap<String,Double>();
	static{
		//simple
		valueTransform.put(AlgorithmConstants.RESULT_BESTFIT, ChartConstants.CHART_Y_VALUE_FIT);
		valueTransform.put(AlgorithmConstants.RESULT_BESTCOV, ChartConstants.CHART_Y_VALUE_COV);
		valueTransform.put(AlgorithmConstants.RESULT_BESTDIS, ChartConstants.CHART_Y_VALUE_DIS);
		valueTransform.put(AlgorithmConstants.RESULT_RUNTIME, ChartConstants.CHART_Y_VALUE_TIME);
		//array
		valueTransform.put(AlgorithmConstants.RESULT_BESTFITS, ChartConstants.CHART_Y_VALUE_FIT);
		valueTransform.put(AlgorithmConstants.RESULT_BESTCOVS, ChartConstants.CHART_Y_VALUE_COV);
		valueTransform.put(AlgorithmConstants.RESULT_BESTDISS, ChartConstants.CHART_Y_VALUE_DIS);
	}
	/**
	 * 将resultType类型的结果按算法的运行次数按顺序返回
	 */
	static Map<String,List<Double>> getResultByRunTime(List<String> algoName,String resultType){
		//value transform to chart value
		double transform = valueTransform.get(resultType);
		//prepare value
		Map<String,List<Double>> result = new HashMap<String,List<Double>>();
		for(String name : algoName){
			List<Double> resultList = new ArrayList<Double>();
			List<Map<String,Object>> algoResult = GlobalMainControl.getInstance().getResult(name);
			for(Map<String,Object> resultMap : algoResult){
				//get value from result map
				Object valueObj = resultMap.get(resultType);
				double value = 1;
				if(valueObj instanceof Double){
					value = (Double)valueObj;
				}else if(valueObj instanceof Long){
					value = (double)((long)(Long)valueObj);
				}else if(valueObj instanceof Integer){
					value = (double)((long)(Integer)valueObj);
				}
				//put value to result list
				resultList.add(value*transform);
			}
			result.put(name, resultList);
		}
		return result;
	}
	/**
	 * 将算法第一次运行的结果按resultType以迭代次数返回
	 * @param algoName
	 * @param resultType
	 * @return
	 */
	static Map<String,List<Double>> getResultByLoop(List<String> algoName,String resultType){
		//value transform to chart value
		double transform = valueTransform.get(resultType);
		//prepare value
		Map<String,List<Double>> result = new HashMap<String,List<Double>>();
		for(String name : algoName){
			List<Double> resultList = new ArrayList<Double>();
			//get value from result map
			Map<String,Object> algoResultFirst = GlobalMainControl.getInstance().getResult(name).get(0);
			double[] valueArray = (double[]) algoResultFirst.get(resultType);
			for(double value:valueArray){
				//put value to result list
				resultList.add(value*transform);
			}
			result.put(name, resultList);
		}
		return result;
	}
	
	/**
	 * 将算法第一次运行的结果的收敛性
	 * @param algoName
	 * @param resultType
	 * @return
	 */
	static Map<String,List<Double>> getResultByLoopMax(List<String> algoName,String resultType){
		//value transform to chart value
		double transform = valueTransform.get(resultType);
		//prepare value
		Map<String,List<Double>> result = new HashMap<String,List<Double>>();
		for(String name : algoName){
			List<Double> resultList = new ArrayList<Double>();
			//get value from result map
			Map<String,Object> algoResultFirst = GlobalMainControl.getInstance().getResult(name).get(0);
			double[] valueArray = (double[]) algoResultFirst.get(resultType);
			double valueHisMax = valueArray[0]*transform;
			for(double value:valueArray){
				valueHisMax = Math.max(valueHisMax, value*transform);
				//put value to result list
				resultList.add(valueHisMax);
			}
			result.put(name, resultList);
		}
		return result;
	}
	
	/**
	 * 根据最优,最差,平均,均方差,返回算法结果
	 */
	static Map<String,Map<String,Double>> getResultByCategory(List<String> algoName,String resultType){
		//value transform to chart value
		double transform = valueTransform.get(resultType);
		//prepare value
		Map<String,Map<String,Double>> result = new HashMap<String,Map<String,Double>>();
		for(String name : algoName){
			List<Double> resultList = new ArrayList<Double>();
			//get run result
			List<Map<String,Object>> algoResult = GlobalMainControl.getInstance().getResult(name);
			for(Map<String,Object> resultMap : algoResult){
				//get value from result map
				Object valueObj = resultMap.get(resultType);
				double value = 1;
				if(valueObj instanceof Double){
					value = (Double)valueObj;
				}else if(valueObj instanceof Long){
					value = (double)((long)(Long)valueObj);
				}else if(valueObj instanceof Integer){
					value = (double)((long)(Integer)valueObj);
				}
				//put value to result list
				resultList.add(value*transform);
			}
			//add category value
			result.put(name, calcutaleCategory(resultList));
		}
		return result;
	}
	private static Map<String,Double> calcutaleCategory(List<Double> values){
		//calculate
		double best = values.get(0);
		double worst = best;
		double sum = 0;
		for(double value : values){
			best = Math.max(best, value);
			worst = Math.min(worst, value);
			sum += value;
		}
		double aveg = sum/values.size();
		// standard deviation
		double sum2dis = 0;
		for(double value : values){
			sum2dis+=(value-aveg)*(value-aveg);
		}
		double sd = Math.pow(sum2dis/values.size(), 0.5);
		//put
		Map<String,Double> map = new HashMap<String,Double>();
		map.put(ChartConstants.CHART_BSET, best);
		map.put(ChartConstants.CHART_WORST, worst);
		map.put(ChartConstants.CHART_AVEG, aveg);
		map.put(ChartConstants.CHART_STDDIV, sd);
		return map;
	}
}
