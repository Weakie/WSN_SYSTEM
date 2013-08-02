#include <vector>
#include "MethodSelect.h"
#include "MethodCrossOver.h"
#include "MethodMutate.h"
#include "Individual.h"

using namespace std;

#ifndef INHERITMETHOD
#define INHERITMETHOD

/*
* 遗传算法选择,交叉,变异方法
*/

class InheritMethod{
public:
	InheritMethod(int selectMethod,int k,int choiceProbMethod,double q,double a,double b,
		int crossOverMethod,double pc,
		int mutateMethod,double pm,int maxLoopNum):
	select(selectMethod,k,choiceProbMethod,q,a,b),
	cross(crossOverMethod,pc),
	mutate(mutateMethod,pm,maxLoopNum)
	{}
	/**
	 * 计算种群的每个个体的适应度
	 * @param population 表示一个种群
	 */
	void calcuFitDegree(vector<Individual*>& population){
		for(int i=0;i<population.size();i++){
			population[i]->calculateFitDegree();
		}
	}
	/**
	 * 根据个体选择概率确定种群中哪些个体被选择,
	 * 必须使用拷贝构造函数产生新的个体给下一代，否则大家都使用同一个引用
	 * @param population 表示一个种群,执行完后组成新的总群
	 */
	void selectPopulations(vector<Individual*>& population){
		select.selectPopulation(population);
	}
	/**
	 * 对传入的种群进行交叉,修改引用值
	 * 直接修改population数组,对其进行两两交叉
	 * 必须是偶数的
	 */
	void crossPopulations(vector<Individual*>& population){
		cross.crossOverPopulation(population);
	}
	/**
	 * 对传入的种群进行变异
	 */
	void mutatePopulations(vector<Individual*>& population,int loop){
		mutate.mutatePopulation(population,loop);
	}
private:
	SelectPopulation select;
	CrossOverPopulation cross;
	MutatePopulation mutate;
};
#endif