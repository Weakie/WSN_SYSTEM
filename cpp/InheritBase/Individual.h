#include <iostream>
#include <vector>
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
	double fitDegree;	//这个染色体的适应度
	double dis;
	double cov;
public:
	bool mutate;		//是否进行变异
	bool crossover;	//是否进行交叉
public:
	Individual():nodePos(nodeNum*2,0){
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
		for(int i=0;i<nodeNum*2;i++){
			nodePos[i] = (posmin+chromosome[i]*(posmax-posmin));
		}
		vector<Node>& nodes = NodeList::getInstance().initNodes(nodePos);
		dis = EvaluateCalculator::getInstance().getMoveDiatance(nodes);
		cov = EvaluateCalculator::getInstance().getUnionCoverage(nodes);
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

	static double f1;//适应度函数
	static double f2;

	static int nodeNum;
	static int posmax;
	static int posmin;
public:
	static void initStaticValues(int nodeNum,int posmax,int posmin,double f1,double f2);
	static bool compareByFitDegree(const Individual *a, const Individual *b); 
	static bool compareByDis(const Individual *a, const Individual *b); 
	static bool compareByCov(const Individual *a, const Individual *b); 
};

randomNumber Individual::rnd;

double Individual::f1 = 1;
double Individual::f2 = 100;

int Individual::nodeNum;
int Individual::posmax;
int Individual::posmin;

void Individual::initStaticValues(int nodeNum,int posmax,int posmin,double f1,double f2){
	Individual::nodeNum = nodeNum;
	Individual::posmax = posmax;
	Individual::posmin = posmin;
	Individual::f1 = f1;
	Individual::f2 = f2;
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