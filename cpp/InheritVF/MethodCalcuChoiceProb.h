#include <vector>
#include <algorithm>
#include <cmath>
#include "Individual.h"

using namespace std;

#ifndef METHODCALCUCHOICEPROB
#define METHODCALCUCHOICEPROB

//选择概率,只用来做轮盘赌选择使用
class CalcuChoiceProb{
public:
	CalcuChoiceProb(int method,double q){
		this->choiceMethod = method;
		this->q = q;
		this->a = 2;
		this->b = 1;
	}
	CalcuChoiceProb(int method,double q,double a,double b){
		this->choiceMethod = method;
		this->q = q;
		this->a = a;
		this->b = b;
	}
	/**
	 * 计算种群的每个个体的选择概率,对每个个体的choiceProb赋值
	 * @param population 表示一个种群
	 */
	vector<double> calculateChoiceProb(vector<Individual*>& population){
		//选择概率,与population的顺序严格对应
		vector<double> choiceProb(population.size(),0);
		switch(choiceMethod){
			case 0:
				fitnessPropModel(population,choiceProb);
				break;
			case 1:
				linerRankBasedModel(population,choiceProb);
				break;
			case 2:
				unLinerRankBasedModel(population,choiceProb);
				break;
		}
		return choiceProb;
	}
private:
	//适应度比例
	void fitnessPropModel(vector<Individual*>& population,vector<double>& choiceProb){
		//计算总的适应度
		double fitM = 0;
		for(int i=0;i<population.size();i++){
			fitM += population[i]->getFitDegree();
		}
		//计算每个个体的选择概率
		for(int i=0;i<population.size();i++){
			choiceProb[i] = population[i]->getFitDegree()/fitM;
		}
	}
	//线性排序方法
	void linerRankBasedModel(vector<Individual*>& population,vector<double>& choiceProb){
		//按适应度大小从好到坏排序,降序排序
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		int M = population.size();
		for(int i=0;i<population.size();i++){
			choiceProb[i] = (a-b*(i+1))/(M*(M+1));//i=1,2,,,
		}
	}
	//非线性排序方法
	void unLinerRankBasedModel(vector<Individual*>& population,vector<double>& choiceProb){
		//按适应度大小从好到坏排序,降序排序
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		int M = population.size();
		for(int i=0;i<M-1;i++){//i=0,1,,M-2
			choiceProb[i] = q * pow((1-q), (i+1)-1);//i=1,2,,,M-1
		}
		choiceProb[M-1] = pow(1-q, M-1);
	}
private:
	//计算方法
	int choiceMethod;
	//线性排序,常数a,b
	double a;
	double b;	
	//非线性排序,最好个体选择概率 
	double q;
};
#endif