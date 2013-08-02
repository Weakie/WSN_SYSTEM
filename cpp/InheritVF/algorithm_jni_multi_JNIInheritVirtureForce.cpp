#include "jni.h"
#include "algorithm_jni_multi_JNIInheritVirtureForce.h"
#include "NodeList.h"
#include "myutil.h"
#include "EvaluateCalculator.h"
#include "Inherit.h"
#include "InheritMethod.h"
#include <time.h>
#include <fstream>
using namespace std;

JNIEXPORT void JNICALL Java_algorithm_jni_multi_JNIInheritVirtureForce_runNativeInherit
(JNIEnv *env, jobject obj, jint popNum, jint loopNum){
	jclass cls = env->GetObjectClass(obj);
	jfieldID paramFid = env->GetFieldID(cls,"param","Lalgorithm/jni/param/JNIInheritVirtureForceParam;");
	//get the object of field : param
	jobject paramObj = env->GetObjectField(obj,paramFid);
	jclass paramClass = env->GetObjectClass(paramObj);

	//get the param 's field id
	jfieldID choiceMethodFid = env->GetFieldID(paramClass,"choiceMethod","I");
	jfieldID selectMethodFid = env->GetFieldID(paramClass,"selectMethod","I");
	jfieldID  crossMethodFid = env->GetFieldID(paramClass, "crossMethod","I");
	jfieldID mutateMethodFid = env->GetFieldID(paramClass,"mutateMethod","I");
	jfieldID kFid = env->GetFieldID(paramClass,"k","I");
	jfieldID qFid = env->GetFieldID(paramClass,"q","D");
	jfieldID aFid = env->GetFieldID(paramClass,"a","D");
	jfieldID bFid = env->GetFieldID(paramClass,"b","D");
	jfieldID pcFid = env->GetFieldID(paramClass,"pc","D");
	jfieldID pmFid = env->GetFieldID(paramClass,"pm","D");

	jfieldID f1Fid = env->GetFieldID(paramClass,"f1","D");
	jfieldID f2Fid = env->GetFieldID(paramClass,"f2","D");
	jfieldID c3Fid = env->GetFieldID(paramClass,"c3","D");

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
	int selectMethod = env->GetIntField(paramObj,selectMethodFid);
	int choiceMethod = env->GetIntField(paramObj,choiceMethodFid);
	int mutateMethod = env->GetIntField(paramObj,mutateMethodFid);
	int crossMethod  = env->GetIntField(paramObj, crossMethodFid);
	int k = env->GetIntField(paramObj,kFid);
	double q = env->GetDoubleField(paramObj,qFid);
	double a = env->GetDoubleField(paramObj,aFid);
	double b = env->GetDoubleField(paramObj,bFid);
	double pc = env->GetDoubleField(paramObj,pcFid);
	double pm = env->GetDoubleField(paramObj,pmFid);

	double f1 = env->GetDoubleField(paramObj,f1Fid);
	double f2 = env->GetDoubleField(paramObj,f2Fid);
	double c3 = env->GetDoubleField(paramObj,c3Fid);

	int nodeSize = env->GetIntField(paramObj,nodeSizeFid);
	double posMax = env->GetDoubleField(paramObj,posMaxFid);
	double posMin = env->GetDoubleField(paramObj,posMinFid);
	double grid = env->GetDoubleField(paramObj,gridFid);
	double sensedCth = env->GetDoubleField(paramObj,sensedCthFid);
	cout<<loopNum<<" popNum "<<popNum<<" nodeSize "<<nodeSize<<" posMax "<<posMax<<" posMin "<<posMin<<" grid "<<grid<<" sensedCth "<<sensedCth<<endl;

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
	Individual::initStaticValues(nodeSize,posMax,posMin,f1,f2,c3);
	InheritMethod method(selectMethod,k,choiceMethod,q,a,b,crossMethod,pc,mutateMethod,pm,loopNum);

	//run
	Inherit inherit(popNum,loopNum,method);
	inherit.run();

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
	jdoubleArray bestPosObj = env->NewDoubleArray(inherit.getGlobalBestPos().size());
	env->SetDoubleArrayRegion(bestPosObj,0,inherit.getGlobalBestPos().size(),&(inherit.getGlobalBestPos()[0]));
	env->SetObjectField(resultObj,bestPosFid,bestPosObj);
	env->DeleteLocalRef(bestPosObj);
	//--- set globalBest
	jdoubleArray globalBestFitObj = env->NewDoubleArray(inherit.getGlobalBestFit().size());
	env->SetDoubleArrayRegion(globalBestFitObj,0,inherit.getGlobalBestFit().size(),&(inherit.getGlobalBestFit()[0]));
	env->SetObjectField(resultObj,globalBestFitFid,globalBestFitObj);
	env->DeleteLocalRef(globalBestFitObj);
	//--- set globalbest cov
	jdoubleArray globalBestCovObj = env->NewDoubleArray(inherit.getGlobalBestCov().size());
	env->SetDoubleArrayRegion(globalBestCovObj,0,inherit.getGlobalBestCov().size(),&(inherit.getGlobalBestCov()[0]));
	env->SetObjectField(resultObj,globalBestCovFid,globalBestCovObj);
	env->DeleteLocalRef(globalBestCovObj);
	//--- set globalbest dis
	jdoubleArray globalBestDisObj = env->NewDoubleArray(inherit.getGlobalBestDis().size());
	env->SetDoubleArrayRegion(globalBestDisObj,0,inherit.getGlobalBestDis().size(),&(inherit.getGlobalBestDis()[0]));
	env->SetObjectField(resultObj,globalBestDisFid,globalBestDisObj);
	env->DeleteLocalRef(globalBestDisObj);

	ofstream out("out.txt",ios_base::app);
	double cov = EvaluateCalculator::getInstance().getUnionCoverage(NodeList::getInstance().initNodes(inherit.getGlobalBestPos()));
	out<<cov<<" "<<inherit.getBestFiDegree()<<endl;
}