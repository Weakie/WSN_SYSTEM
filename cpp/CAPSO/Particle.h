#include <iostream>
#include <vector>
#include "NodeList.h"
#include "EvaluateCalculator.h"
#include "myutil.h"

using namespace std;

#ifndef PPARTICLE
#define PPARTICLE

class Particle{
//static
private:
	static randomNumber rnd;

	static int dims; // ������ά��,2*�ڵ���
	static vector<double> gbest;// ���������ҵ������λ��
	static double gbest_fitness;

	static int generation;//��������,�̶�����
	static double w;	
	static double c1;
	static double c2;
	static double wMax;		//APSO
	static double wMin;
	static double c1Begin;	//CAPSO
	static double c1End;
	static double c2Begin;
	static double c2End;

	static double f1;//��Ӧ�Ⱥ���
	static double f2;

	static double posmax;
	static double posmin;
	static double vmax;
	static double vmin;

	/**
	* ����low ��uper֮��������,low<uper
	*/
	static double rand(double low, double uper);

public:
	/**
	* ��ʼ����̬���в���
	*/
	static void initStaticParams(int generation,double wMax,double wMin,double c1Begin,double c1End,double c2Begin,double c2End,double f1,double f2);
	static void initStaticVaules(int dims,double posMax,double posMin);
	/**
	* ÿ�ֽ�����ͳ��ȫ�����Ž�
	*/
	static void updateGlobalFitness(vector<Particle>& pars);
	/*
	* ÿ�ε�������w��ֵ
	*/
	static void updatePSOParamValues(int gen);
	/**
	* ���ȫ����ʷ����ֵ
	*/
	static double getGlobalFitness();
	static vector<double>& getGlobalBestPos();

//dynamic
private:
	vector<double> pos;	 // ���ӵ�λ�ã�����������ά���������Ϊ����ά
	vector<double> v;	 // ���ӵ��ٶȣ�ά��ͬλ��
	double fitness;		 // ���ӵ���Ӧ��
	double dis;
	double cov;
	vector<double> pbest;// ���ӵ���ʷ���λ��
	double pbest_fitness;// ��ʷ���Ž�
	
public:
	
	Particle():pos(dims,posmin),v(dims,0),pbest(dims,posmax){
		fitness = 0;
		pbest_fitness = 0;
	}

	void setIniPos(vector<double>& iniPos) {
		vectorCopy(iniPos, 0, pos, 0, dims);
	}

	/**
	* ��������ֵ,ͬʱ��¼��ʷ����λ��
	*/
	void evaluate() {
		vector<Node>& nodes = NodeList::getInstance().initNodes(pos);
		dis = EvaluateCalculator::getInstance().getMoveDiatance(nodes);
		cov = EvaluateCalculator::getInstance().getUnionCoverage(nodes);
		//fitness = f1*cov+f2/dis;
		double NL = (posmax-posmin)*(dims/2);
		fitness = f1*cov + f2*NL/(dis+NL);
		fitness = fitness/(f1+f2);
		if (fitness > pbest_fitness) {
			vectorCopy(pos, 0, pbest, 0, dims);
			pbest_fitness = fitness;
		}
	}

	/**
	* �����ٶȺ�λ��
	*/
	void updatePosSpeed() {
		for (int i = 0; i < dims; ++i) {
			v[i] = w * v[i] + c1 * rnd.frandom() * (pbest[i] - pos[i]) + c2 * rnd.frandom() * (gbest[i] - pos[i]);
			if (v[i] > vmax) {
				v[i] = vmax;
			}
			if (v[i] < vmin) {
				v[i] = vmin;
			}
			pos[i] = pos[i] + v[i];
			if(pos[i]<posmin){
				pos[i]=posmin;
			}
			if(pos[i]>posmax){
				pos[i]=posmax;
			}
		}
	}

	double getFitness(){
		return this->fitness;
	}
	double getDis(){
		return this->dis;
	}
	double getCov(){
		return this->cov;
	}

	vector<double>& getPos(){
		return this->pos;
	}
};

randomNumber Particle::rnd;

int Particle::dims;
vector<double> Particle::gbest;
double Particle::gbest_fitness;

double Particle::posmax;
double Particle::posmin;
double Particle::vmax;
double Particle::vmin;

int Particle::generation;
double Particle::w;
double Particle::c1;
double Particle::c2;
double Particle::wMax = 0.9;
double Particle::wMin = 0.4;
double Particle::c1Begin = 2.75;
double Particle::c1End = 1.25;
double Particle::c2Begin = 0.50;
double Particle::c2End = 2.25;

double Particle::f1 = 1;
double Particle::f2 = 100;


double Particle::rand(double low, double uper) {
	return rnd.frandom() * (uper - low) + low;
}
void Particle::initStaticParams(int generation,double wMax,double wMin,double c1Begin,double c1End,double c2Begin,double c2End,double f1,double f2){
	Particle::generation = generation;
	Particle::wMax = wMax;
	Particle::wMin = wMin;
	Particle::c1Begin = c1Begin;
	Particle::c1End = c1End;
	Particle::c2Begin = c2Begin;
	Particle::c2End = c2End;
	Particle::f1 = f1;
	Particle::f2 = f2;
	//����w,c1,c2ֵ,���ŵ��������仯,CAPSO�㷨
	Particle::w = Particle::wMax - (Particle::wMax - Particle::wMin) / generation; 
	Particle::c1 = Particle::c1Begin + (Particle::c1End - Particle::c1Begin) / generation;
	Particle::c2 = Particle::c2Begin + (Particle::c2End - Particle::c2Begin) / generation;
}
void Particle::initStaticVaules(int dims,double posMax,double posMin){
	Particle::dims = dims;
	vectorInit(gbest,dims,posmax,posmin);
	Particle::gbest_fitness = 0;
	Particle::posmax = posMax;
	Particle::posmin = posMin;
	Particle::vmax = rand(0.1,0.2) * (posmax - posmin);// (10%~20%)*(posmax-posmin)
	Particle::vmin = -vmax;
}

void Particle::updatePSOParamValues(int gen){
	//����w,c1,c2ֵ,���ŵ��������仯,CAPSO�㷨
	Particle::w = Particle::wMax - gen * (Particle::wMax - Particle::wMin) / generation; 
	Particle::c1 = Particle::c1Begin + gen * (Particle::c1End - Particle::c1Begin) / generation;
	Particle::c2 = Particle::c2Begin + gen * (Particle::c2End - Particle::c2Begin) / generation; 
}

void Particle::updateGlobalFitness(vector<Particle>& pars){
	double global_best = 0;
	int index = -1;
	for (int i = 0; i < pars.size(); i++) {
		// ȫ�����Ž�
		if (global_best < pars[i].pbest_fitness) {
			global_best = pars[i].pbest_fitness;
			index = i;
		}
	}
	if(index!=-1){
		Particle::gbest_fitness = global_best; 
		vectorCopy(pars[index].pbest, 0, Particle::gbest, 0, Particle::dims);
	}
}
double Particle::getGlobalFitness(){
	return Particle::gbest_fitness;	
}

vector<double>& Particle::getGlobalBestPos(){
	return Particle::gbest;
}

#endif