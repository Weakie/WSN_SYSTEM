#include <vector>
#include "myutil.h"
#include "Node.h"
using namespace std;

#ifndef COVERAGEEVALUATE
#define COVERAGEEVALUATE

class CoverageEvaluate {
private:
	static CoverageEvaluate coverageEva;
public:
	static CoverageEvaluate& getInstance();
private:
	vector<vector<bool>> grids;
	double posmax,posmin,grid,sensedCth;

	CoverageEvaluate(){}
	void resetGrids(){
		for(unsigned i=0;i<grids.size();i++){
			vectorFill(grids[i], false);
		}
	}
public:
	void initialize(double posMax,double posMin,double grid,double sensedCth){
		this->posmax = posMax;
		this->posmin = posMin;
		this->grid = grid;
		this->sensedCth = sensedCth;
		grids.clear();
		int num = (int) ((posMax-posMin)/grid);
		vector<bool> tmp(num,false);
		for(int i=0;i<num;i++){
			grids.push_back(vector<bool>(tmp));
		}
	}

	double getUnionCoverage(vector<Node>& nodes){
		int coveredArea = 0;
		//resetGrids();
		for(unsigned i=0;i<grids.size();i++){
			for(unsigned j=0;j<grids[i].size();j++){
				double x = grid*i+posmin;
				double y = grid*j+posmin;
				double unSensedP = 1;
				for(unsigned k=0;k<nodes.size();k++){
					unSensedP*=(1.0-nodes[k].getSensedPi(x, y));
				}
				if(1.0-unSensedP > sensedCth){
					//grids[i][j] = true;
					coveredArea++;
				}
			}
		}
		return (double)coveredArea/(double)grids[1].size()/(double)grids.size();
	}
};

CoverageEvaluate CoverageEvaluate::coverageEva;

CoverageEvaluate& CoverageEvaluate::getInstance(){
	return CoverageEvaluate::coverageEva;
}
#endif