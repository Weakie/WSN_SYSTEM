#include <vector>
#include <algorithm>
#include <cmath>
#include "d_random.h"
#include "Individual.h"
using namespace std;
#ifndef METHIDMUTATE
#define METHIDMUTATE
//����
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
			//�ж��Ƿ���б���--��Ӣ����
			if(!population[i]->mutate){
				population[i]->mutate = true;
				continue;
			}
			//���ݱ�����ʾ����Ƿ���б���
			if(rnd.random(PRECISION) + 1 < ((int)(pm*PRECISION))){
				//�޸�populationԪ�����õĶ��������
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
	//������ת
	void areaExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//ѡȡ�����ĵ�,index1!=index2
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		while(index2 == index1){
			index2 = rnd.random(n);
		}
		//��֤index1<index2
		if(index1 > index2){
			swap(index1,index2);
		}
		//���以��
		while(index1<index2){
			swap(seq[index1++],seq[index2--]);
		}
	}
	//���㻥��
	void twoPointsExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//ѡȡ�����ĵ�,index1!=index2
		int index1 = rnd.random(n);
		int index2 = rnd.random(n);
		while(index2 == index1){
			index2 = rnd.random(n);
		}
		swap(seq[index1],seq[index2]);
	}
	//�߽绥��
	void borderExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		//ѡȡ�����ĵ�,index1!=index2
		int index1 = rnd.random(n);
		int index2 = (index1+1)%n;
		swap(seq[index1],seq[index2]);
	}
	//ʵ������,�����Ա���
	void averageMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		int k = rnd.random(n);
		seq[k] = rnd.frandom();
	}
	/**
	* �ڵ�ĸ�������ڵ��˳��û�й�ϵ,���ǽڵ���ƶ�����ͺ�˳���й���,
	* ������췽����һ�����ʽ����ƶ��������������ڵ�λ��,��֤�����ʲ��������½ڵ��ƶ������С
	*/
	void averageMutateExchange(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		int n = seq.size();
		int k = rnd.random(n);
		seq[k] = rnd.frandom();
		
		//���԰����Ա仯,Ҳ���԰���ƽ���仯,��ƽ������ʹ��ǰ�ڸı���ʽϴ�
		double rand = rnd.frandom();
		if(rand>((double)loop*loop)/((double)maxLoopNum*maxLoopNum)){
			vector<double>& iniPos = EvaluateCalculator::getInstance().getIniPos();
			vector<double>& curPos = indiv.getPos();
			//�������
			vector<double> dis(iniPos.size()/2,0);
			for(int i=0;i<dis.size();i++){
				dis[i] = (iniPos[2*i]-curPos[2*i])*(iniPos[2*i]-curPos[2*i])+
					(iniPos[2*i+1]-curPos[2*i+1])*(iniPos[2*i+1]-curPos[2*i+1]);
			}
			//�����ʼλ�������Զ�Ľڵ��������һ���ڵ㻥��λ��
			int index1 = max_element(dis.begin(),dis.end()) - dis.begin();
			int index2 = rnd.random(dis.size());
			swap(seq[index1*2],seq[index2*2]);
			swap(seq[index1*2+1],seq[index2*2+1]);
		}
	}
	//����ƶ����뽻��
	void maxDisExchangeMutate(Individual& indiv,int loop){
		vector<double>& seq = indiv.getChromosome();
		//���԰����Ա仯,Ҳ���԰���ƽ���仯,��ƽ������ʹ��ǰ�ڸı���ʽϴ�
		double rand = rnd.frandom();
		if(rand>((double)loop*loop)/((double)maxLoopNum*maxLoopNum)){
			vector<double>& iniPos = EvaluateCalculator::getInstance().getIniPos();
			vector<double>& curPos = indiv.getPos();
			//�������
			vector<double> dis(iniPos.size()/2,0);
			for(int i=0;i<dis.size();i++){
				dis[i] = (iniPos[2*i]-curPos[2*i])*(iniPos[2*i]-curPos[2*i])+
					(iniPos[2*i+1]-curPos[2*i+1])*(iniPos[2*i+1]-curPos[2*i+1]);
			}
			//�����ʼλ�������Զ�Ľڵ��������һ���ڵ㻥��λ��
			int index1 = max_element(dis.begin(),dis.end()) - dis.begin();
			int index2 = rnd.random(dis.size());
			swap(seq[index1*2],seq[index2*2]);
			swap(seq[index1*2+1],seq[index2*2+1]);
		}
	}
	//��һ���Ա���
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
	//����Ӧ����
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
	double pm;	//�������
	int maxLoopNum;		//����������
	double maxAdapter;	//= -1,���õ������Ӧ��ֵ
	randomNumber rnd;	//�����
	const static int PRECISION = 10000;//���ʵľ���,��ʾpm����
};
#endif