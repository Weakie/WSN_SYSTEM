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

	static int dims; // 产生的维数,2*节点数
	static vector<double> gbest;// 所有粒子找到的最好位置
	static double gbest_fitness;

	static int generation;//迭代次数,固定不变
	static double w;	//w动态调整,APSO
	static double wMax;
	static double wMin;
	static double c1;
	static double c2;

	static double f1;//适应度函数
	static double f2;

	static double posmax;
	static double posmin;
	static double vmax;
	static double vmin;

	/**
	* 返回low 到uper之间的随机数,low<uper
	*/
	static double rand(double low, double uper);

public:
	/**
	* 初始化静态运行参数
	*/
	static void initStaticParams(int generation,double wMax,double wMin,double c1,double c2,double f1,double f2);
	static void initStaticVaules(int dims,double posMax,double posMin);
	/**
	* 每轮结束后统计全局最优解
	*/
	static void updateGlobalFitness(vector<Particle>& pars);
	/*
	* 每次迭代更新w的值
	*/
	static void updateWitValues(int gen);
	/**
	* 获得全局历史最优值
	*/
	static double getGlobalFitness();
	static vector<double>& getGlobalBestPos();

//dynamic
private:
	vector<double> pos;	 // 粒子的位置，求解问题多少维，则此数组为多少维
	vector<double> v;	 // 粒子的速度，维数同位置
	double fitness;		 // 粒子的适应度
	double dis;
	double cov;
	vector<double> pbest;// 粒子的历史最好位置
	double pbest_fitness;// 历史最优解
	
public:
	
	Particle():pos(dims,posmin),v(dims,0),pbest(dims,posmax){
		fitness = 0;
		pbest_fitness = 0;
	}

	void setIniPos(vector<double>& iniPos) {
		vectorCopy(iniPos, 0, pos, 0, dims);
	}

	/**
	* 评估函数值,同时记录历史最优位置
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
	* 更新速度和位置
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
double Particle::wMax = 0.9;
double Particle::wMin = 0.4;
double Particle::c1 = 1;
double Particle::c2 = 1;

double Particle::f1 = 1;
double Particle::f2 = 100;


double Particle::rand(double low, double uper) {
	return rnd.frandom() * (uper - low) + low;
}
void Particle::initStaticParams(int generation,double wMax,double wMin,double c1,double c2,double f1,double f2){
	Particle::generation = generation;
	Particle::wMax = wMax;
	Particle::wMin = wMin;
	Particle::c1 = c1;
	Particle::c2 = c2;
	Particle::f1 = f1;
	Particle::f2 = f2;
	//设置w值,随着迭代次数变化,APSO算法
	Particle::w = Particle::wMax - (Particle::wMax - Particle::wMin) / generation; 
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

void Particle::updateWitValues(int gen){
	//设置w值,随着迭代次数变化,APSO算法
	Particle::w = Particle::wMax - gen * (Particle::wMax - Particle::wMin) / generation; 
}

void Particle::updateGlobalFitness(vector<Particle>& pars){
	double global_best = 0;
	int index = -1;
	for (int i = 0; i < pars.size(); i++) {
		// 全局最优解
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