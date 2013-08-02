#include <vector>
#include <algorithm>
#include "Individual.h"
#include "d_random.h"

using namespace std;

#ifndef METHODCROSSOVER
#define METHODCROSSOVER

//交叉算子
class CrossOverPopulation{
public:
	CrossOverPopulation(int method,double pc){
		this->crossOverMethod = method;
		this->pc = pc;
	}
	/**
	 * 对传入的种群进行交叉,修改引用值
	 * 直接修改population数组,对其进行两两交叉
	 * 个数必须是偶数的
	 */
	void crossOverPopulation(vector<Individual*>& population) {
		//按适应度大小从好到坏排序,降序排序
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		//交叉
		for (int i = 0; i < population.size(); i += 2) {
			//先判断是否需要交叉-两者中有一个不交叉就不交叉
			if (!population[i]->crossover || !population[i+1]->crossover) {
				population[i]->crossover = true;
				population[i+1]->crossover = true;
				continue;
			}
			//按照交叉概率来决定是否交叉
			if (rnd.random(PRECISION) + 1 < ((int) (pc * PRECISION))) {
				// 修改population元素引用的对象的属性
				crossOverIndividuals(*population[i], *population[i + 1]);
			}
		}
	}

private:
	//对两个个体进行交叉
	void crossOverIndividuals(Individual& a,Individual& b){
		switch(crossOverMethod){
			case 0:
				onePointCrossover(a,b);
				break;
			case 1:
				twoPointCrossover(a,b);
				break;
			case 2:
				uniformdCrossOver(a,b);
				break;
			case 3:
				arithmeticPartCrossover(a,b);
				break;
			case 4:
				arithmeticFullCrossover(a,b);
				break;
		}
	}
	//单点交叉
	void onePointCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		int index = rnd.random(n);
		//交叉,交换posa和posb中index之后的元素
		swap_ranges(posa.begin()+index,posa.end(),posb.begin()+index);
	}
	//两点交叉
	void twoPointCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		//index2>=index1,交叉位置
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		if(index1>index2){
			swap(index1,index2);
		}
		//交叉,交换posa和posb中index1之后,index2之前的元素
		swap_ranges(posa.begin()+index1, posa.begin()+index2, posb.begin()+index1);
	}
	//均匀交叉
	void uniformdCrossOver(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		//交叉
		for (int i = 0; i < n; i++) {
			if (rnd.frandom() > 0.5) {
				swap(posa[rnd.random(n)],posb[rnd.random(n)]);
			}
		}
	}
	//整体算术交叉
	void arithmeticFullCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();

		//交叉
		int n = posa.size();
		double rand = rnd.frandom();
		for(int i = 0; i < n; i++){
			if(i % 2 == 0){
				double tmpa = posa[i];
				double tmpb = posb[i];
				posa[i] = rand*tmpa+(1-rand)*tmpb;
				posb[i] = rand*tmpb+(1-rand)*tmpa;
			} else {
				double tmpa = posa[i];
				double tmpb = posb[i];
				posa[i] = tmpb+rand*(tmpa-tmpb);
				posb[i] = tmpa+rand*(tmpb-tmpa);
			}
		}
	}
	//部分算术交叉
	void arithmeticPartCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();

		//交叉
		int n = posa.size();
		double rand = rnd.frandom();
		for(int i = rnd.random(n); i < n; i++){
			if(i % 2 == 0){
				posa[i] *= rand;
				posb[i] *= rand;
			} else {
				swap(posa[i],posb[i]);
				posa[i] *= (1-rand);
				posb[i] *= (1-rand);
			}
		}
	}
private:
	int crossOverMethod;
	double pc;	//交叉概率
	randomNumber rnd;
	const static int PRECISION=10000;//概率的精度,表示pc精度
};
#endif