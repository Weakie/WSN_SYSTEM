#include <vector>
#include <algorithm>
#include <cmath>
#include "d_random.h"
#include "Individual.h"
using namespace std;
#ifndef METHIDMUTATE
#define METHIDMUTATE
//变异
class MutatePopulation{
public:
	MutatePopulation(int method,double pm,int maxLoopNum){
		this->mutateMethod = method;
		this->maxLoopNum = maxLoopNum;
		this->pm = pm;
		this->maxAdapter = -1;
	}
	void mutatePopulation(vector<Individual*>& population,int loop){
		for(int i=0;i<population.size();i++){
			//判断是否进行变异--精英策略
			if(!population[i]->mutate){
				population[i]->mutate = true;
				continue;
			}
			//根据变异概率决定是否进行变异
			if(rnd.random(PRECISION) + 1 < ((int)(pm*PRECISION))){
				//修改population元素引用的对象的属性
				this->mutateIndividual(*population[i],loop);
			}
		}
	}
private:
	void mutateIndividual(Individual& indiv,int loop){
		switch(mutateMethod){
			case 0:
				areaExchange(indiv,loop);
				break;
			case 1:
				twoPointsExchange(indiv,loop);
				break;
			case 2:
				borderExchange(indiv,loop);
				break;
			case 3:
				averageMutate(indiv,loop);
				break;
			case 4:
				averageMutateExchange(indiv,loop);
				break;
			case 5:
				maxDisExchangeMutate(indiv,loop);
				break;
			case 6:
				disagreeMutate(indiv,loop);
				break;
			case 7:
				adapterSelfMutate(indiv,loop);
				break;
		}
	}
	//区间逆转
	void areaExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//选取互换的点,index1!=index2
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		while(index2 == index1){
			index2 = rnd.random(n);
		}
		//保证index1<index2
		if(index1 > index2){
			swap(index1,index2);
		}
		//区间互换
		while(index1<index2){
			swap(seq[index1++],seq[index2--]);
		}
	}
	//两点互换
	void twoPointsExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//选取互换的点,index1!=index2
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		while(index2 == index1){
			index2 = rnd.random(n);
		}
		swap(seq[index1],seq[index2]);
	}
	//边界互换
	void borderExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//选取互换的点,index1!=index2
		int index1 = rnd.random(n);
		int index2 = (index1+1)%n;
		swap(seq[index1],seq[index2]);
	}
	//实数变异,均匀性变异
	void averageMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		int k = rnd.random(n);
		seq[k] = rnd.frandom();
	}
	/**
	* 节点的覆盖率与节点的顺序没有关系,但是节点的移动距离就和顺序有关了,
	* 这个变异方法按一定概率交换移动距离最大的两个节点位置,保证覆盖率不变的情况下节点移动距离减小
	*/
	void averageMutateExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		int k = rnd.random(n);
		seq[k] = rnd.frandom();
		
		//可以按线性变化,也可以按照平方变化,按平方可以使得前期改变概率较大
		double rand = rnd.frandom();
		if(rand>((double)loop*loop)/((double)maxLoopNum*maxLoopNum)){
			vector<double>& iniPos = EvaluateCalculator::getInstance().getIniPos();
			vector<double>& curPos = indiv.getPos();
			//计算距离
			vector<double> dis(iniPos.size()/2,0);
			for(int i=0;i<dis.size();i++){
				dis[i] = (iniPos[2*i]-curPos[2*i])*(iniPos[2*i]-curPos[2*i])+
					(iniPos[2*i+1]-curPos[2*i+1])*(iniPos[2*i+1]-curPos[2*i+1]);
			}
			//将与初始位置离得最远的节点与随机的一个节点互换位置
			int index1 = max_element(dis.begin(),dis.end()) - dis.begin();
			int index2 = rnd.random(dis.size());
			swap(seq[index1*2],seq[index2*2]);
			swap(seq[index1*2+1],seq[index2*2+1]);
		}
	}
	//最大移动距离交换
	void maxDisExchangeMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		//可以按线性变化,也可以按照平方变化,按平方可以使得前期改变概率较大
		double rand = rnd.frandom();
		if(rand>((double)loop*loop)/((double)maxLoopNum*maxLoopNum)){
			vector<double>& iniPos = EvaluateCalculator::getInstance().getIniPos();
			vector<double>& curPos = indiv.getPos();
			//计算距离
			vector<double> dis(iniPos.size()/2,0);
			for(int i=0;i<dis.size();i++){
				dis[i] = (iniPos[2*i]-curPos[2*i])*(iniPos[2*i]-curPos[2*i])+
					(iniPos[2*i+1]-curPos[2*i+1])*(iniPos[2*i+1]-curPos[2*i+1]);
			}
			//将与初始位置离得最远的节点与随机的一个节点互换位置
			int index1 = max_element(dis.begin(),dis.end()) - dis.begin();
			int index2 = rnd.random(dis.size());
			swap(seq[index1*2],seq[index2*2]);
			swap(seq[index1*2+1],seq[index2*2+1]);
		}
	}
	//非一致性变异
	void disagreeMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int k = rnd.random(seq.size());
		if(rnd.random(2) == 0){
			seq[k] += function_1(loop,1-seq[k]);
		}else{
			seq[k] += function_1(loop,1);
		}
	}
	double function_1(int t,double y){
		double e = (1-((double)t)/((double)maxLoopNum))*(1-((double)t)/((double)maxLoopNum))*(1-((double)t)/((double)maxLoopNum));
		return y*(1-pow(rnd.frandom(), e));
	}
	//自适应变异
	void adapterSelfMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		maxAdapter = max(maxAdapter, indiv.getFitDegree());
		int k = rnd.random(seq.size());
		if(rnd.random(2) == 0){
			seq[k] += function_2(indiv.getFitDegree(),1-seq[k]);
		}else{
			seq[k] += function_2(indiv.getFitDegree(),1);
		}
	}
	double function_2(double t,double y){
		double e = (1-t/maxAdapter)*(1-t/maxAdapter)*(1-t/maxAdapter);
		return y*(1-pow(rnd.frandom(), e));
	}
private:
	int mutateMethod;
	double pm;	//变异概率
	int maxLoopNum;		//最大迭代次数
	double maxAdapter;	//= -1,所得的最大适应度值
	randomNumber rnd;	//随机数
	const static int PRECISION = 10000;//概率的精度,表示pm精度
};
#endif