#include <vector>
#include <algorithm>
#include "Individual.h"
#include "d_random.h"

using namespace std;

#ifndef METHODCROSSOVER
#define METHODCROSSOVER

//��������
class CrossOverPopulation{
public:
	CrossOverPopulation(int method,double pc){
		this->crossOverMethod = method;
		this->pc = pc;
	}
	/**
	 * �Դ������Ⱥ���н���,�޸�����ֵ
	 * ֱ���޸�population����,���������������
	 * ����������ż����
	 */
	void crossOverPopulation(vector<Individual*>& population) {
		//����Ӧ�ȴ�С�Ӻõ�������,��������
		sort(population.begin(),population.end(),Individual::compareByFitDegree);
		//����
		for (int i = 0; i < population.size(); i += 2) {
			//���ж��Ƿ���Ҫ����-��������һ��������Ͳ�����
			if (!population[i]->crossover || !population[i+1]->crossover) {
				population[i]->crossover = true;
				population[i+1]->crossover = true;
				continue;
			}
			//���ս�������������Ƿ񽻲�
			if (rnd.random(PRECISION) + 1 < ((int) (pc * PRECISION))) {
				// �޸�populationԪ�����õĶ��������
				crossOverIndividuals(*population[i], *population[i + 1]);
			}
		}
	}

private:
	//������������н���
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
	//���㽻��
	void onePointCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		int index = rnd.random(n);
		//����,����posa��posb��index֮���Ԫ��
		swap_ranges(posa.begin()+index,posa.end(),posb.begin()+index);
	}
	//���㽻��
	void twoPointCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		//index2>=index1,����λ��
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		if(index1>index2){
			swap(index1,index2);
		}
		//����,����posa��posb��index1֮��,index2֮ǰ��Ԫ��
		swap_ranges(posa.begin()+index1, posa.begin()+index2, posb.begin()+index1);
	}
	//���Ƚ���
	void uniformdCrossOver(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();
		int n = posa.size();
		//����
		for (int i = 0; i < n; i++) {
			if (rnd.frandom() > 0.5) {
				swap(posa[rnd.random(n)],posb[rnd.random(n)]);
			}
		}
	}
	//������������
	void arithmeticFullCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();

		//����
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
	//������������
	void arithmeticPartCrossover(Individual& a,Individual& b){
		vector<double>& posa = a.getChromosome();
		vector<double>& posb = b.getChromosome();

		//����
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
	double pc;	//�������
	randomNumber rnd;
	const static int PRECISION=10000;//���ʵľ���,��ʾpc����
};
#endif