#include <vector>
#include <cmath>
#include "Node.h"
#include "myutil.h"

using namespace std;

#ifndef VIRTUALFORCECALCULATOR
#define VIRTUALFORCECALCULATOR

class VirtualForceCalcutator {
private:
	static VirtualForceCalcutator vfc;
public:
	static VirtualForceCalcutator& getInstance();

private:
	vector<vector<bool>> grids;
	double posmax,posmin,grid,sensedCth;

	VirtualForceCalcutator(){}

	void updateGridsCoverage(vector<Node>& nodes) {
		// resetGrids
		for (int i = 0; i < grids.size(); i++) {
			vectorFill(grids[i], false);
		}
		// coverage
		for (int i = 0; i < grids.size(); i++) {
			for (int j = 0; j < grids[i].size(); j++) {
				double x = grid * i + posmin;
				double y = grid * j + posmin;
				double unSensedP = 1;
				for (int k=0;k<nodes.size();k++) {
					unSensedP *= (1 - nodes[k].getSensedPi(x, y));
				}
				if (1.0 - unSensedP > sensedCth) {
					grids[i][j] = true;
				}
			}
		}
	}
	/**
	 * 计算未覆盖点和传感器之间的虚拟力
	 */
	vector<double> calculateVFForRegion(vector<Node>& nodes){
		//update state
		updateGridsCoverage(nodes);
		//calculate VF
		vector<double> forceX(nodes.size(),0);
		vector<double> forceY(nodes.size(),0);
		for(int i=0;i<grids.size();i++){
			for(int j=0;j<grids[i].size();j++){
				//如果已经覆盖了,则不计算
				if(grids[i][j])
					continue;
				double x = grid*i+posmin;
				double y = grid*j+posmin;
				for(int k=0;k<nodes.size();k++){
					double x1 = nodes[k].getPosX();
					double y1 = nodes[k].getPosY();
					double distance2 = pow(x-x1,2)+pow(y-y1,2)+0.1;
					if(distance2<pow(nodes[k].getConnectedRi(),2)&&!nodes[k].isInSensedRegion(x, y)){
						forceX[k] += ((x-x1)/sqrt(distance2))/distance2;
						forceY[k] += ((y-y1)/sqrt(distance2))/distance2;
					}
				}
				
			}
		}
		vector<double> force(nodes.size()*2,0);
		for(int i=0;i<nodes.size();i++){
			force[i*2] = forceX[i];
			force[i*2+1] = forceY[i];
		}
		return force;
	}
	
	/**
	 * 计算传感器节点之间的虚拟力，加速迭代速度
	 * @param pos
	 * @param Ri
	 */
	vector<double> calculateVFForNodes(vector<Node>& nodes){
		vector<double> forceX(nodes.size(),0);
		vector<double> forceY(nodes.size(),0);
		for(unsigned i=0;i<nodes.size();i++){
			double x1 = nodes[i].getPosX();
			double y1 = nodes[i].getPosY();
			
			for(unsigned j=i+1;j<nodes.size();j++){
				//distance>Ri
				if(!nodes[j].isConnected(nodes[i]))
					continue;
				
				double x2 = nodes[j].getPosX();
				double y2 = nodes[j].getPosY();
				double distance2 = pow(x1-x2,2)+pow(y1-y2,2)+0.1;
				//((x1-x2)/Math.sqrt(distance2)) 表示力在X轴的方向,带有正负号,单位向量在X轴的分量
				//((y1-y2)/Math.sqrt(distance2)) 表示力在Y轴的方向,带有正负号,单位向量在Y轴的分量
				double forceInX = ((x1-x2)/sqrt(distance2))/distance2;
				double forceInY = ((y1-y2)/sqrt(distance2))/distance2;
				forceX[i] += forceInX;
				forceY[i] += forceInY;
				forceX[j] -= forceInX;
				forceY[j] -= forceInY;
			}
		}
		vector<double> force(nodes.size()*2,0);
		for(unsigned i=0;i<nodes.size();i++){
			force[i*2] = forceX[i];
			force[i*2+1] = forceY[i];
		}
		return force;
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

	vector<double> calculateVirtualForce(vector<Node>& nodes){
		vector<double>& forceR = calculateVFForRegion(nodes);
		vector<double>& forceN = calculateVFForNodes(nodes);
		vector<double> force(nodes.size()*2,0);
		for(int i=0;i<force.size();i++){
			force[i] = forceR[i] + forceN[i];
		}
		return force;
	}
	
};

VirtualForceCalcutator VirtualForceCalcutator::vfc;

VirtualForceCalcutator& VirtualForceCalcutator::getInstance(){
	return VirtualForceCalcutator::vfc;
}

#endif