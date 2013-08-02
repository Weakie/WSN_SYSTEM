#include <vector>
#include "d_random.h"
#include "Node.h"
#include "myutil.h"

using namespace std;

#ifndef MOVEDISTEVALUATE
#define MOVEDISTEVALUATE

class MoveDistEvaluate {
private:
	static MoveDistEvaluate mde;
public:
	static MoveDistEvaluate& getInstance();

private:
	vector<double> iniPos;

	MoveDistEvaluate(){}
public:
	vector<double>& getIniPos(){
		return iniPos;
	}

	void setIniPos(vector<double>& pos){
		if(pos.size()!=iniPos.size()){
			vectorInit(iniPos,pos.size(),0.0);
		}
		vectorCopy(pos,0,iniPos,0,pos.size());
	}

	void initialize(int nodeSize,double posMax,double posMin){
		vectorInit(iniPos,nodeSize*2,posMax,posMin);
	}

	double getMoveDiatance(vector<Node>& nodes){
		double distance = 0;
		for(int i=0;i<nodes.size();i++){
			distance+=nodes[i].getDistance(iniPos[i*2], iniPos[i*2+1]);
		}
		return distance;
	}
};

MoveDistEvaluate MoveDistEvaluate::mde;

MoveDistEvaluate& MoveDistEvaluate::getInstance(){
	return MoveDistEvaluate::mde;
}

#endif