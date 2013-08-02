#include <iostream>
#include <vector>
#include <cmath>
#include "d_random.h"
#include "NodeList.h"
#include "EvaluateCalculator.h"
#include "myutil.h"

using namespace std;

#ifndef INDIVIDUAL
#define INDIVIDUAL

/*
* 遗传算法中的个体
*/

class Individual{
//dynamic
private:
	vector<double> chromosome;//代表一个染色体
	vector<double> nodePos;
	vector<double> force;
	double fitDegree;	//这个染色体的适应度
	double dis;
	double cov;
public:
	bool mutate;		//是否进行变异
	bool crossover;	//是否进行交叉
public:
	Individual():nodePos(nodeNum*2,0),force(nodeNum*2,0){
		mutate = true;
		crossover = true;
		for (int i = 0; i < nodeNum*2; i++) {
			chromosome.push_back(rnd.frandom());
		}
	}

	//必须返回引用
	vector<double>& getChromosome(){
		return chromosome;
	}

	void setChromosome(vector<double>& chromosome){
		this->chromosome = chromosome;
	}

	double getFitDegree(){
		return this->fitDegree;
	}

	void calculateFitDegree() {
		//计算虚拟力
		for(int i=0;i<nodeNum*2;i++){
			nodePos[i] = (posmin+chromosome[i]*(posmax-posmin));
		}
		EvaluateCalculator::getInstance().updateVirtualForce(NodeList::getInstance().initNodes(nodePos),force);
		for(int i=0;i<nodeNum*2;i++){
			nodePos[i] += force[i] * c1;
			nodePos[i] = min(nodePos[i],posmax);
			nodePos[i] = max(nodePos[i],posmin);
			chromosome[i] = (nodePos[i]-posmin)/((double)(posmax-posmin));
		}
		//计算适应度
		vector<Node>& nodes = NodeList::getInstance().initNodes(nodePos);
		dis = EvaluateCalculator::getInstance().getMoveDiatance(nodes);
		cov = EvaluateCalculator::getInstance().getUnionCoverage(nodes);
		// 设置适应度
		// 设置适应度
		//this->fitDegree =  f1*cov+f2/dis;;
		double NL = (posmax-posmin)*(nodeNum);
		this->fitDegree = f1*cov + f2*NL/(dis+NL);
		this->fitDegree = this->fitDegree/(f1+f2);
	}

	vector<double>& getPos(){
		for(int i=0;i<nodeNum*2;i++){
			nodePos[i] = (posmin+chromosome[i]*(posmax-posmin));
		}
		return nodePos;
	}

	double getCov(){
		return this->cov;
	}

	double getDis(){
		return this->dis;
	}

//static
private:
	static randomNumber rnd;

	static double c1;//虚拟力强度
	static double f1;//适应度函数
	static double f2;

	static int nodeNum;
	static double posmax;
	static double posmin;
public:
	static void initStaticValues(int nodeNum,double posmax,double posmin,double f1,double f2,double c1);
	static bool compareByFitDegree(const Individual *a, const Individual *b); 
	static bool compareByDis(const Individual *a, const Individual *b); 
	static bool compareByCov(const Individual *a, const Individual *b); 
};

randomNumber Individual::rnd;
double Individual::c1 = 160;
double Individual::f1 = 1;
double Individual::f2 = 100;

int Individual::nodeNum;
double Individual::posmax;
double Individual::posmin;

void Individual::initStaticValues(int nodeNum,double posmax,double posmin,double f1,double f2,double c1){
	Individual::nodeNum = nodeNum;
	Individual::posmax = posmax;
	Individual::posmin = posmin;
	Individual::f1 = f1;
	Individual::f2 = f2;
	Individual::c1 = c1;
}
//按适应度大小从好到坏排序,降序排序,a>b return true
bool Individual::compareByFitDegree(const Individual *a, const Individual *b){
	return a->fitDegree > b->fitDegree;
}
bool Individual::compareByDis(const Individual *a, const Individual *b){
	return a->dis > b->dis;
}
bool Individual::compareByCov(const Individual *a, const Individual *b){
	return a->cov > b->cov;
}
#endif