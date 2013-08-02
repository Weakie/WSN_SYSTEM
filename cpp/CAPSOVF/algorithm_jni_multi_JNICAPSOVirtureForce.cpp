#include "jni.h"
#include "algorithm_jni_multi_JNICAPSOVirtureForce.h"
#include "NodeList.h"
#include "Particle.h"
#include "myutil.h"
#include "CAPSOVF.h"
#include "EvaluateCalculator.h"
#include <time.h>
#include <fstream>
using namespace std;

JNIEXPORT void JNICALL Java_algorithm_jni_multi_JNICAPSOVirtureForce_runNativePSO
(JNIEnv *env, jobject obj, jint parSize, jint num){
	jclass cls = env->GetObjectClass(obj);
	jfieldID paramFid = env->GetFieldID(cls,"param","Lalgorithm/jni/param/JNICAPSOVirtureForceParam;");
	//get the object of field : param
	jobject paramObj = env->GetObjectField(obj,paramFid);
	jclass paramClass = env->GetObjectClass(paramObj);
	//get the param 's field id
	jfieldID wMaxFid = env->GetFieldID(paramClass,"wMax","D");
	jfieldID wMinFid = env->GetFieldID(paramClass,"wMin","D");
	jfieldID c1BeginFid = env->GetFieldID(paramClass,"c1Begin","D");
	jfieldID c2BeginFid = env->GetFieldID(paramClass,"c2Begin","D");
	jfieldID c3BeginFid = env->GetFieldID(paramClass,"c3Begin","D");
	jfieldID c1EndFid = env->GetFieldID(paramClass,"c1End","D");
	jfieldID c2EndFid = env->GetFieldID(paramClass,"c2End","D");
	jfieldID c3EndFid = env->GetFieldID(paramClass,"c3End","D");
	jfieldID f1Fid = env->GetFieldID(paramClass,"f1","D");
	jfieldID f2Fid = env->GetFieldID(paramClass,"f2","D");

	jfieldID nodeSizeFid = env->GetFieldID(paramClass,"nodeSize","I");
	jfieldID posMaxFid = env->GetFieldID(paramClass,"posMax","D");
	jfieldID posMinFid = env->GetFieldID(paramClass,"posMin","D");
	jfieldID gridFid = env->GetFieldID(paramClass,"grid","D");
	jfieldID sensedCthFid = env->GetFieldID(paramClass,"sensedCth","D");

	jfieldID angleFid = env->GetFieldID(paramClass,"Angle","I");
	jfieldID dFid = env->GetFieldID(paramClass,"D","I");
	jfieldID RiFid = env->GetFieldID(paramClass,"connectedRi","D");
	jfieldID RcFid = env->GetFieldID(paramClass,"Rc","D");
	jfieldID RpFid = env->GetFieldID(paramClass,"Rp","D");
	jfieldID sFid = env->GetFieldID(paramClass,"s","D");

	jfieldID rvFid = env->GetFieldID(paramClass,"Rv","[[D");
	jfieldID initPositionFid = env->GetFieldID(paramClass,"initPosition","[D");
	//get the param 's field value
	double wMax = env->GetDoubleField(paramObj,wMaxFid);
	double wMin = env->GetDoubleField(paramObj,wMinFid);
	double c1Begin = env->GetDoubleField(paramObj,c1BeginFid);
	double c2Begin = env->GetDoubleField(paramObj,c2BeginFid);
	double c3Begin = env->GetDoubleField(paramObj,c3BeginFid);
	double c1End = env->GetDoubleField(paramObj,c1EndFid);
	double c2End = env->GetDoubleField(paramObj,c2EndFid);
	double c3End = env->GetDoubleField(paramObj,c3EndFid);
	double f1 = env->GetDoubleField(paramObj,f1Fid);
	double f2 = env->GetDoubleField(paramObj,f2Fid);

	int nodeSize = env->GetIntField(paramObj,nodeSizeFid);
	double posMax = env->GetDoubleField(paramObj,posMaxFid);
	double posMin = env->GetDoubleField(paramObj,posMinFid);
	double grid = env->GetDoubleField(paramObj,gridFid);
	double sensedCth = env->GetDoubleField(paramObj,sensedCthFid);
	cout<<num<<" nodeSize "<<nodeSize<<" posMax "<<posMax<<" posMin "<<posMin<<" grid "<<grid<<" sensedCth "<<sensedCth<<endl;

	int angle = env->GetIntField(paramObj,angleFid);;
	int d = env->GetIntField(paramObj,dFid);
	double Ri = env->GetDoubleField(paramObj,RiFid);
	double Rc = env->GetDoubleField(paramObj,RcFid);
	double Rp = env->GetDoubleField(paramObj,RpFid);
	double s = env->GetDoubleField(paramObj,sFid);

	//--- get iniPos
	jdoubleArray initPostionArray = (jdoubleArray)env->GetObjectField(paramObj,initPositionFid);
	double* iniPositionPointer = env->GetDoubleArrayElements(initPostionArray,NULL);
	vector<double> initPosition;
	for(int i=0;i<nodeSize*2;i++){
		initPosition.push_back((double)iniPositionPointer[i]);
	}
	env->ReleaseDoubleArrayElements(initPostionArray,iniPositionPointer,0);
	
	//--- get rv
	vector<vector<double>> rv2D;
	jobjectArray rvObj = (jobjectArray)env->GetObjectField(paramObj,rvFid);
	for(int i=0;i<nodeSize;i++){
		jdoubleArray rvArray = (jdoubleArray)env->GetObjectArrayElement(rvObj,i);
		double* rvPointer = env->GetDoubleArrayElements(rvArray,NULL);

		vector<double> rvTmp;
		for(int j=0;j<angle;j++){
			rvTmp.push_back(rvPointer[j]);
		}
		rv2D.push_back(rvTmp);

		env->ReleaseDoubleArrayElements(rvArray,rvPointer,0);
		env->DeleteLocalRef(rvArray);
	}
	env->DeleteLocalRef(rvObj);

	//initialize
	Node::initializeStaticValue(Ri,Rc,Rp,s,angle,d);
	NodeList::getInstance().initialize(nodeSize);
	NodeList::getInstance().initNodes(rv2D);
	EvaluateCalculator::getInstance().initialize(nodeSize,posMax,posMin,grid,sensedCth,initPosition);
	Particle::initStaticVaules(nodeSize*2,posMax,posMin);
	Particle::initStaticParams(num,wMax,wMin,c1Begin,c1End,c2Begin,c2End,c3Begin,c3End,f1,f2);
	
	//run
	CAPSOVF pso(parSize,num);
	pso.run();

	//set
	jfieldID resultFid = env->GetFieldID(cls,"result","Lalgorithm/Result;");
	//get the object of field : result
	jobject resultObj = env->GetObjectField(obj,resultFid);
	jclass resultClass = env->GetObjectClass(resultObj);
	//get the result 's field id
	jfieldID bestPosFid = env->GetFieldID(resultClass,"bestPos","[D");
	jfieldID globalBestFitFid = env->GetFieldID(resultClass,"globalBestFit","[D");
	jfieldID globalBestCovFid = env->GetFieldID(resultClass,"globalBestCov","[D");
	jfieldID globalBestDisFid = env->GetFieldID(resultClass,"globalBestDis","[D");

	//--- set bestPos
	jdoubleArray bestPosObj = env->NewDoubleArray(Particle::getGlobalBestPos().size());
	env->SetDoubleArrayRegion(bestPosObj,0,Particle::getGlobalBestPos().size(),&(Particle::getGlobalBestPos()[0]));
	env->SetObjectField(resultObj,bestPosFid,bestPosObj);
	env->DeleteLocalRef(bestPosObj);
	//--- set globalBest
	jdoubleArray globalBestFitObj = env->NewDoubleArray(pso.getGlobalBestFit().size());
	env->SetDoubleArrayRegion(globalBestFitObj,0,pso.getGlobalBestFit().size(),&(pso.getGlobalBestFit()[0]));
	env->SetObjectField(resultObj,globalBestFitFid,globalBestFitObj);
	env->DeleteLocalRef(globalBestFitObj);
	//--- set globalbest cov
	jdoubleArray globalBestCovObj = env->NewDoubleArray(pso.getGlobalBestCov().size());
	env->SetDoubleArrayRegion(globalBestCovObj,0,pso.getGlobalBestCov().size(),&(pso.getGlobalBestCov()[0]));
	env->SetObjectField(resultObj,globalBestCovFid,globalBestCovObj);
	env->DeleteLocalRef(globalBestCovObj);
	//--- set globalbest dis
	jdoubleArray globalBestDisObj = env->NewDoubleArray(pso.getGlobalBestDis().size());
	env->SetDoubleArrayRegion(globalBestDisObj,0,pso.getGlobalBestDis().size(),&(pso.getGlobalBestDis()[0]));
	env->SetObjectField(resultObj,globalBestDisFid,globalBestDisObj);
	env->DeleteLocalRef(globalBestDisObj);

	ofstream out("out.txt",ios_base::app);
	double cov = EvaluateCalculator::getInstance().getUnionCoverage(NodeList::getInstance().initNodes(Particle::getGlobalBestPos()));
	out<<cov<<" "<<Particle::getGlobalFitness()<<endl;
}