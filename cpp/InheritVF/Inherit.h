#include <vector>
#include <algorithm>
#include "InheritMethod.h"
#include "Individual.h"

using namespace std;

#ifndef INHERIT
#define INHERIT

/*
* 遗传算法主控制程序
*/
class Inherit{
public:
	Inherit(int n,int runNumber,InheritMethod& inheritMtd):
	  inheritMethod(inheritMtd),
	  global_bestFit(runNumber,0),global_bestCov(runNumber,0),global_bestDis(runNumber,0)
	{
		this->runNumber = runNumber;
		for(int i=0;i<n;i++){
			population.push_back(new Individual());
		}
	}

	~Inherit(){
		for(int i=0;i<population.size();i++){
			delete population[i];
		}
	}

	void run(){
		this->bestFitdegree = -1;
		for(int k = 0;k < runNumber; k++){
			//计算适应度
			inheritMethod.calcuFitDegree(population);
			//统计数据
			Individual* best = *max_element(population.begin(),population.end(),Individual::compareByFitDegree);
			global_bestFit[k] = best->getFitDegree();
			global_bestDis[k] = best->getDis();
			global_bestCov[k] = best->getCov();
			if(bestFitdegree < best->getFitDegree()){
				bestFitdegree = best->getFitDegree();
				global_bestPos = best->getPos();
			}
			cout<<k<<" : "<<global_bestFit[k]<<"   "<<global_bestCov[k]<<"   "<<global_bestDis[k]<<endl;
			//选择
			inheritMethod.selectPopulations(population);
			//交叉
			inheritMethod.crossPopulations(population);
			//变异
			inheritMethod.mutatePopulations(population,k);
		}
	}

	vector<double> getGlobalBestFit(){
		return global_bestFit;
	}
	vector<double> getGlobalBestCov(){
		return global_bestCov;
	}
	vector<double> getGlobalBestDis(){
		return global_bestDis;
	}
	vector<double> getGlobalBestPos(){
		return global_bestPos;
	}
	double getBestFiDegree(){
		return bestFitdegree;
	}
private:
	int runNumber;			// 迭代次数
	double bestFitdegree;	// 最大适应度
	vector<Individual*> population;	// 全体个体
	vector<double> global_bestFit;	// 全局最优解
	vector<double> global_bestCov;	// 全局最优解
	vector<double> global_bestDis;	// 全局最优解
	vector<double> global_bestPos;	// 全局最优位置
	InheritMethod& inheritMethod;	// 遗传算子

};
#endif
/****
				//动态显示代码
				jclass cls = env->GetObjectClass(obj);
				jfieldID resultFid = env->GetFieldID(cls,"result","Lalgorithm/jni/Result;");
				//get the object of field : param
				jobject resultObj = env->GetObjectField(obj,resultFid);
				jclass resultClass = env->GetObjectClass(resultObj);
				//get the param 's field id
				jfieldID bestPosFid = env->GetFieldID(resultClass,"bestPos","[D");
				jfieldID rvFid = env->GetFieldID(resultClass,"rv","[[D");
				//--- set rv
				jclass dArrCls = env->FindClass("[D");
				jobjectArray rvObj = env->NewObjectArray(24,dArrCls,NULL);
				for(int i=0;i<24;i++){
					Node& node = NodeList::getInstance().getNodes()[i];
					jdoubleArray dArr = env->NewDoubleArray(node.getRv().size());
					env->SetDoubleArrayRegion(dArr,0,node.getRv().size(),&(node.getRv()[0]));
					env->SetObjectArrayElement(rvObj,i,dArr);
					env->DeleteLocalRef(dArr);
				}
				env->SetObjectField(resultObj,rvFid,rvObj);
				env->DeleteLocalRef(rvObj);
				//---
				jdoubleArray bestPosObj = env->NewDoubleArray(pars[index].getPos().size());
				env->SetDoubleArrayRegion(bestPosObj,0,pars[index].getPos().size(),&(pars[index].getPos()[0]));
				env->SetObjectField(resultObj,bestPosFid,bestPosObj);
				env->DeleteLocalRef(bestPosObj);
				//---
				jmethodID methodID = env->GetMethodID(cls,"show","()V");
				env->CallVoidMethod(obj,methodID);
				****/