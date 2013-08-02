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
	vector<Particle> pars;		// ȫ������
	vector<double> global_bestFit;	// ȫ�����Ž�
	vector<double> global_bestCov;	// ȫ�����Ž�
	vector<double> global_bestDis;	// ȫ�����Ž�
	
public:
	/**
	 * ����Ⱥ��ʼ��
	 * n ���ӵ�����
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
	 * ����Ⱥ������
	 */
	void run() {
		for (int k=0;k<runNumber;k++) {
			int index = -1;
			double globalBestTmp = -1;
			// ÿ�����Ӹ���λ�ú���Ӧֵ
			for (int i = 0; i < pars.size(); i++) {
				pars[i].updateChaosPos();
				pars[i].updatePosSpeed();
				pars[i].evaluate();
				//���ֵ�ȫ�����Ž�
				if (globalBestTmp < pars[i].getFitness()) {
					globalBestTmp = pars[i].getFitness();
					index = i;
				}
			}
			// ����ȫ�����Ž�
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
				//��̬��ʾ����
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