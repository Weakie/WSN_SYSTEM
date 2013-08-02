#include <vector>
#include "Particle.h"
#include "EvaluateCalculator.h"
#include "Node.h"

using namespace std;

#ifndef CPSOCLASS
#define CPSOCLASS

class CPSO {
private:
	double runNumber;	//
	vector<Particle> pars;		// 全体粒子
	vector<double> global_bestFit;	// 全局最优解
	vector<double> global_bestCov;	// 全局最优解
	vector<double> global_bestDis;	// 全局最优解
	
public:
	/**
	 * 粒子群初始化
	 * n 粒子的数量
	 */
	CPSO(int n,int runNumber):
	  pars(n,Particle()),global_bestFit(runNumber,0),global_bestCov(runNumber,0),global_bestDis(runNumber,0)
	{
		this->runNumber = runNumber;
		vector<double>& iniPos = EvaluateCalculator::getInstance().getIniPos();
		for (int i = 0; i < n; ++i) {
			pars[i].setIniPos(iniPos);
		}
	}

	/**
	 * 粒子群的运行
	 */
	void run() {
		for (int k=0;k<runNumber;k++) {
			int index = -1;
			double globalBestTmp = -1;
			// 每个粒子更新位置和适应值
			for (int i = 0; i < pars.size(); i++) {
				pars[i].updateChaosPos();
				pars[i].updatePosSpeed();
				pars[i].evaluate();
				//这轮的全局最优解
				if (globalBestTmp < pars[i].getFitness()) {
					globalBestTmp = pars[i].getFitness();
					index = i;
				}
			}
			// 更新全局最优解
			Particle::updateGlobalFitness(pars);

			if(index!=-1){
				global_bestFit[k] = pars[index].getFitness();
				global_bestCov[k] = pars[index].getCov();
				global_bestDis[k] = pars[index].getDis();
				cout<<k<<" : "<<global_bestFit[k]<<"   "<<global_bestCov[k]<<"   "<<global_bestDis[k]<<endl;
			}
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