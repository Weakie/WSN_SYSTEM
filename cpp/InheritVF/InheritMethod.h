#include <vector>
#include "MethodSelect.h"
#include "MethodCrossOver.h"
#include "MethodMutate.h"
#include "Individual.h"

using namespace std;

#ifndef INHERITMETHOD
#define INHERITMETHOD

/*
* �Ŵ��㷨ѡ��,����,���췽��
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
	 * ������Ⱥ��ÿ���������Ӧ��
	 * @param population ��ʾһ����Ⱥ
	 */
	void calcuFitDegree(vector<Individual*>& population){
		for(int i=0;i<population.size();i++){
			population[i]->calculateFitDegree();
		}
	}
	/**
	 * ���ݸ���ѡ�����ȷ����Ⱥ����Щ���屻ѡ��,
	 * ����ʹ�ÿ������캯�������µĸ������һ���������Ҷ�ʹ��ͬһ������
	 * @param population ��ʾһ����Ⱥ,ִ���������µ���Ⱥ
	 */
	void selectPopulations(vector<Individual*>& population){
		select.selectPopulation(population);
	}
	/**
	 * �Դ������Ⱥ���н���,�޸�����ֵ
	 * ֱ���޸�population����,���������������
	 * ������ż����
	 */
	void crossPopulations(vector<Individual*>& population){
		cross.crossOverPopulation(population);
	}
	/**
	 * �Դ������Ⱥ���б���
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