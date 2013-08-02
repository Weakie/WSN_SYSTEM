#include <vector>
#include <algorithm>
#include "d_random.h"
#include "Individual.h"
#include "MethodCalcuChoiceProb.h"

using namespace std;

#ifndef METHODSELECT
#define METHODSELECT

//选择
class SelectPopulation{
public:
	SelectPopulation(int selectMethod,int k,int choiceProbMethod,double q,double a,double b)
		:choiceProbCal(choiceProbMethod,q,a,b)
	{
		this->selectMethod = selectMethod;
		this->k = k;
	}
	/**
	 * 根据个体选择概率确定种群中哪些个体被选择,
	 * 直接对数组元素修改--->这是错误的
	 * 必须使用拷贝构造函数产生新的个体给下一代，否则大家都使用同一个引用
	 * @param population 表示一个种群,执行完后保存新的总群
	 */
	void selectPopulation(vector<Individual*>& population){
		switch(selectMethod){
			case 0:
				elitistModel(population);
				break;
			case 1:
				rouletteWheelSelection(population);
				break;
			case 2:
				tournamentSelectionModel(population);
				break;
			case 3:
				stochastivTournamentSelectionModel(population);
				break;
		}
	}
private:
	/*
	*精英策略
	*先选择最好的保存到下一代，不变化
	*然后使用锦标赛方法选出剩下的那些
	*/
	void elitistModel(vector<Individual*>& population){
		//临时备份
		vector<Individual*> popuBuf = population;
		
		//先选择最好的保存到下一代,不变化
		Individual* best = *max_element(popuBuf.begin(),popuBuf.end(),Individual::compareByFitDegree);
		/*int bestIndex = 0;
		for(int i = 0; i < popuBuf.size(); i++){
			if(popuBuf[i]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
				bestIndex = i;
			}
		}
		//最优的不交叉变异
		population[0] = new Individual(*popuBuf[bestIndex]);*/
		population[0] = new Individual(*best);
		population[0]->crossover = false;
		population[0]->mutate = false;

		int M = popuBuf.size();
		for(int i=1;i<M;i++){
			//表示选择的k个个体
			int bestIndex = rnd.random(M);
			for(int j=0;j<k;j++){
				int index = rnd.random(M);
				if(popuBuf[index]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
					bestIndex = index;
				}
			}
			//k个个体中选择适应度最高的保存到下一代
			//拷贝构造
			population[i] = new Individual(*popuBuf[bestIndex]);
		}

		//删除原来的对象
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*轮盘赌
	*/
	void rouletteWheelSelection(vector<Individual*>& population){
		//临时备份
		vector<Individual*> popuBuf = population;
		int M = popuBuf.size();
		//计算选择概率
		vector<double>& choiceProb = choiceProbCal.calculateChoiceProb(popuBuf);
		//计算累积概率
		vector<double> accumulates(M,0);//累积概率
		accumulates[0] = choiceProb[0];
		for(int i = 1; i < M; i++){
			accumulates[i] = accumulates[i-1] + choiceProb[i];
		}

		//选择
		for(int i=0;i<population.size();i++){
			//随机数,0-1之间
			double rd = rnd.frandom();
			//找到被选中的个体
			int index = binarySearch(accumulates,rd,0,M-1);
			//放入目标种群中,拷贝构造产生新的种群,防止大家引用相同的对象
			population[i] = new Individual(*popuBuf[index]);
		}

		//删除原来的对象
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*锦标赛选择
	*随机选择K个个体,保留其中最大的,反复执行,选满为止
	*/
	void tournamentSelectionModel(vector<Individual*>& population){
		//临时备份
		vector<Individual*> popuBuf = population;
		//选择k个个体,保留最好的
		int M = popuBuf.size();
		for(int i=0;i<M;i++){
			//表示选择的k个个体
			int bestIndex = rnd.random(M-1)+1;
			for(int j=0;j<k;j++){
				int index = rnd.random(M-1)+1;
				if(popuBuf[index]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
					bestIndex = index;
				}
			}
			//k个个体中选择适应度最高的保存到下一代
			//拷贝构造
			population[i] = new Individual(*popuBuf[bestIndex]);
		}

		//删除原来的
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*随机竞争方法
	*特殊的锦标赛方法,每次轮盘赌选出两个个体进行比较,保留最好的那个,直至选满
	*/
	void stochastivTournamentSelectionModel(vector<Individual*>& population){
		//临时备份
		vector<Individual*> popuBuf = population;
		int M = popuBuf.size();
		//计算选择概率
		vector<double>& choiceProb = choiceProbCal.calculateChoiceProb(popuBuf);
		//计算累积概率
		vector<double> accumulates(M,0);//累积概率
		accumulates[0] = choiceProb[0];
		for(int i = 1; i < M; i++){
			accumulates[i] = accumulates[i-1] + choiceProb[i];
		}

		//选择
		for(int i=0;i<population.size();i++){
			//找到被选中的个体
			int index1 = binarySearch(accumulates,rnd.frandom(),0,M-1);
			int index2 = binarySearch(accumulates,rnd.frandom(),0,M-1);
			//轮盘赌选择一对个体,保留最大的那个
			if(popuBuf[index2]->getFitDegree() > popuBuf[index1]->getFitDegree()){
				index1 = index2;
			}
			//放入目标种群中,拷贝构造产生新的种群,防止大家引用相同的对象
			population[i] = new Individual(*popuBuf[index1]);
		}

		//删除原来的对象
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}

	/**
	 * 二分法搜索元素的位置
	 * 目标数组  accumulate 从小到大排序的数组
	 * @param rd 查找的元素
	 * @return	下标    index1到index2
	 */
	int binarySearch(vector<double>& accumulates,double rd,int index1,int index2){
		if(rd < accumulates[index1]){
			return index1;
		}
		while(index2-index1!=1){
			int middle = (index1+index2)/2;
			if(rd < accumulates[middle]){
				index2 = middle;
			}else{
				index1 = middle;
			}
		}
		return index2;
	}
private:
	//选择方法
	int selectMethod;
	//精英策略,竞赛规模
	int k;
	//随机数
	randomNumber rnd;
	//选择概率,只有轮盘赌才有这一项
	CalcuChoiceProb choiceProbCal;
};
#endif