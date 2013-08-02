#include <vector>
#include <algorithm>
#include "d_random.h"
#include "Individual.h"
#include "MethodCalcuChoiceProb.h"

using namespace std;

#ifndef METHODSELECT
#define METHODSELECT

//ѡ��
class SelectPopulation{
public:
	SelectPopulation(int selectMethod,int k,int choiceProbMethod,double q,double a,double b)
		:choiceProbCal(choiceProbMethod,q,a,b)
	{
		this->selectMethod = selectMethod;
		this->k = k;
	}
	/**
	 * ���ݸ���ѡ�����ȷ����Ⱥ����Щ���屻ѡ��,
	 * ֱ�Ӷ�����Ԫ���޸�--->���Ǵ����
	 * ����ʹ�ÿ������캯�������µĸ������һ���������Ҷ�ʹ��ͬһ������
	 * @param population ��ʾһ����Ⱥ,ִ����󱣴��µ���Ⱥ
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
	*��Ӣ����
	*��ѡ����õı��浽��һ�������仯
	*Ȼ��ʹ�ý���������ѡ��ʣ�µ���Щ
	*/
	void elitistModel(vector<Individual*>& population){
		//��ʱ����
		vector<Individual*> popuBuf = population;
		
		//��ѡ����õı��浽��һ��,���仯
		Individual* best = *max_element(popuBuf.begin(),popuBuf.end(),Individual::compareByFitDegree);
		/*int bestIndex = 0;
		for(int i = 0; i < popuBuf.size(); i++){
			if(popuBuf[i]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
				bestIndex = i;
			}
		}
		//���ŵĲ��������
		population[0] = new Individual(*popuBuf[bestIndex]);*/
		population[0] = new Individual(*best);
		population[0]->crossover = false;
		population[0]->mutate = false;

		int M = popuBuf.size();
		for(int i=1;i<M;i++){
			//��ʾѡ���k������
			int bestIndex = rnd.random(M);
			for(int j=0;j<k;j++){
				int index = rnd.random(M);
				if(popuBuf[index]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
					bestIndex = index;
				}
			}
			//k��������ѡ����Ӧ����ߵı��浽��һ��
			//��������
			population[i] = new Individual(*popuBuf[bestIndex]);
		}

		//ɾ��ԭ���Ķ���
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*���̶�
	*/
	void rouletteWheelSelection(vector<Individual*>& population){
		//��ʱ����
		vector<Individual*> popuBuf = population;
		int M = popuBuf.size();
		//����ѡ�����
		vector<double>& choiceProb = choiceProbCal.calculateChoiceProb(popuBuf);
		//�����ۻ�����
		vector<double> accumulates(M,0);//�ۻ�����
		accumulates[0] = choiceProb[0];
		for(int i = 1; i < M; i++){
			accumulates[i] = accumulates[i-1] + choiceProb[i];
		}

		//ѡ��
		for(int i=0;i<population.size();i++){
			//�����,0-1֮��
			double rd = rnd.frandom();
			//�ҵ���ѡ�еĸ���
			int index = binarySearch(accumulates,rd,0,M-1);
			//����Ŀ����Ⱥ��,������������µ���Ⱥ,��ֹ���������ͬ�Ķ���
			population[i] = new Individual(*popuBuf[index]);
		}

		//ɾ��ԭ���Ķ���
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*������ѡ��
	*���ѡ��K������,������������,����ִ��,ѡ��Ϊֹ
	*/
	void tournamentSelectionModel(vector<Individual*>& population){
		//��ʱ����
		vector<Individual*> popuBuf = population;
		//ѡ��k������,������õ�
		int M = popuBuf.size();
		for(int i=0;i<M;i++){
			//��ʾѡ���k������
			int bestIndex = rnd.random(M-1)+1;
			for(int j=0;j<k;j++){
				int index = rnd.random(M-1)+1;
				if(popuBuf[index]->getFitDegree() > popuBuf[bestIndex]->getFitDegree()){
					bestIndex = index;
				}
			}
			//k��������ѡ����Ӧ����ߵı��浽��һ��
			//��������
			population[i] = new Individual(*popuBuf[bestIndex]);
		}

		//ɾ��ԭ����
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}
	/*
	*�����������
	*����Ľ���������,ÿ�����̶�ѡ������������бȽ�,������õ��Ǹ�,ֱ��ѡ��
	*/
	void stochastivTournamentSelectionModel(vector<Individual*>& population){
		//��ʱ����
		vector<Individual*> popuBuf = population;
		int M = popuBuf.size();
		//����ѡ�����
		vector<double>& choiceProb = choiceProbCal.calculateChoiceProb(popuBuf);
		//�����ۻ�����
		vector<double> accumulates(M,0);//�ۻ�����
		accumulates[0] = choiceProb[0];
		for(int i = 1; i < M; i++){
			accumulates[i] = accumulates[i-1] + choiceProb[i];
		}

		//ѡ��
		for(int i=0;i<population.size();i++){
			//�ҵ���ѡ�еĸ���
			int index1 = binarySearch(accumulates,rnd.frandom(),0,M-1);
			int index2 = binarySearch(accumulates,rnd.frandom(),0,M-1);
			//���̶�ѡ��һ�Ը���,���������Ǹ�
			if(popuBuf[index2]->getFitDegree() > popuBuf[index1]->getFitDegree()){
				index1 = index2;
			}
			//����Ŀ����Ⱥ��,������������µ���Ⱥ,��ֹ���������ͬ�Ķ���
			population[i] = new Individual(*popuBuf[index1]);
		}

		//ɾ��ԭ���Ķ���
		for(int i=0;i<popuBuf.size();i++){
			delete popuBuf[i];
		}
	}

	/**
	 * ���ַ�����Ԫ�ص�λ��
	 * Ŀ������  accumulate ��С�������������
	 * @param rd ���ҵ�Ԫ��
	 * @return	�±�    index1��index2
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
	//ѡ�񷽷�
	int selectMethod;
	//��Ӣ����,������ģ
	int k;
	//�����
	randomNumber rnd;
	//ѡ�����,ֻ�����̶Ĳ�����һ��
	CalcuChoiceProb choiceProbCal;
};
#endif