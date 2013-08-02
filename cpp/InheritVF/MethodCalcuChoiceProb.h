#include <vector>
#include <algorithm>
#include <cmath>
#include "Individual.h"

using namespace std;

#ifndef METHODCALCUCHOICEPROB
#define METHODCALCUCHOICEPROB

//ѡ�����,ֻ���������̶�ѡ��ʹ��
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
	 * ������Ⱥ��ÿ�������ѡ�����,��ÿ�������choiceProb��ֵ
	 * @param population ��ʾһ����Ⱥ
	 */
	vector<double> calculateChoiceProb(vector<Individual*>& population){
		//ѡ�����,��population��˳���ϸ��Ӧ
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
	//��Ӧ�ȱ���
	void fitnessPropModel(vector<Individual*>& population,vector<double>& choiceProb){
		//�����ܵ���Ӧ��
		double fitM = 0;
		for(int i=0;i<population.size();i++){
			fitM += population[i]->getFitDegree();
		}
		//����ÿ�������ѡ�����
		for(int i=0;i<population.size();i++){
			choiceProb[i] = population[i]->getFitDegree()/fitM;
		}
	}
	//�������򷽷�
	void linerRankBasedModel(vector<Individual*>& population,vector<double>& choiceProb){
		//����Ӧ�ȴ�С�Ӻõ�������,��������
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		int M = population.size();
		for(int i=0;i<population.size();i++){
			choiceProb[i] = (a-b*(i+1))/(M*(M+1));//i=1,2,,,
		}
	}
	//���������򷽷�
	void unLinerRankBasedModel(vector<Individual*>& population,vector<double>& choiceProb){
		//����Ӧ�ȴ�С�Ӻõ�������,��������
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		int M = population.size();
		for(int i=0;i<M-1;i++){//i=0,1,,M-2
			choiceProb[i] = q * pow((1-q), (i+1)-1);//i=1,2,,,M-1
		}
		choiceProb[M-1] = pow(1-q, M-1);
	}
private:
	//���㷽��
	int choiceMethod;
	//��������,����a,b
	double a;
	double b;	
	//����������,��ø���ѡ����� 
	double q;
};
#endif