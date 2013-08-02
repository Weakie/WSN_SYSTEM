#include <vector>
#include "myutil.h"
#include "Node.h"
using namespace std;

#ifndef EVALUATECALCUTATOR
#define EVALUATECALCUTATOR

class EvaluateCalculator {
private:
	static EvaluateCalculator evaCal;
public:
	static EvaluateCalculator& getInstance();
private:
	vector<double> iniPos;
	vector<vector<bool>> grids;
	double posmax,posmin,grid,sensedCth;

	EvaluateCalculator(){}

	/**
	 * 计算未覆盖点和传感器之间的虚拟力
	 */
	void calculateVFForRegion(vector<Node>& nodes,vector<double>& forceX,vector<double>& forceY){
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
	}
	
	/**
	 * 计算传感器节点之间的虚拟力，加速迭代速度
	 */
	void calculateVFForNodes(vector<Node>& nodes,vector<double>& forceX,vector<double>& forceY){
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
		
	}
	
public:
	void initialize(int nodeSize,double posMax,double posMin,double grid,double sensedCth){
		this->posmax = posMax;
		this->posmin = posMin;
		this->grid = grid;
		this->sensedCth = sensedCth;
		int num = (int) ((posMax-posMin)/grid);
		vectorInit(grids,num,vector<bool>(num,false));
		vectorInit(iniPos,nodeSize*2,posMax,posMin);
	}

	void initialize(int nodeSize,double posMax,double posMin,double grid,double sensedCth,vector<double>& iniPosition){
		this->posmax = posMax;
		this->posmin = posMin;
		this->grid = grid;
		this->sensedCth = sensedCth;
		int num = (int) ((posMax-posMin)/grid);
		vectorInit(grids,num,vector<bool>(num,false));
		vectorInit(iniPos,nodeSize*2,0.0);
		vectorCopy(iniPosition,0,iniPos,0,nodeSize*2);
	}

	vector<double>& getIniPos(){
		return iniPos;
	}

	void setIniPos(vector<double>& pos){
		if(pos.size()!=iniPos.size()){
			vectorInit(iniPos,pos.size(),0.0);
		}
		vectorCopy(pos,0,iniPos,0,pos.size());
	}

	/*
	* 计算移动距离
	*/
	double getMoveDiatance(vector<Node>& nodes){
		double distance = 0;
		for(int i=0;i<nodes.size();i++){
			distance+=nodes[i].getDistance(iniPos[i*2], iniPos[i*2+1]);
		}
		return distance;
	}

	/*
	* 先计算覆盖率,更新未覆盖的区域
	*/
	double getUnionCoverage(vector<Node>& nodes){
		int coveredArea = 0;
		for(unsigned i=0;i<grids.size();i++){
			for(unsigned j=0;j<grids[i].size();j++){
				double x = grid*i+posmin;
				double y = grid*j+posmin;
				double unSensedP = 1;
				for(unsigned k=0;k<nodes.size();k++){
					unSensedP*=(1.0-nodes[k].getSensedPi(x, y));
				}
				if(1.0-unSensedP > sensedCth){
					coveredArea++;
					grids[i][j] = true;
				}else{
					grids[i][j] = false;
				}
			}
		}
		return (double)coveredArea/(double)grids[1].size()/(double)grids.size();
	}

	/*
	* 接着根据未覆盖区域计算虚拟力,计算的虚拟力用作下次迭代更新位置
	*/
	void updateVirtualForce(vector<Node>& nodes,vector<double>& force){
		vector<double> forceX(nodes.size(),0);
		vector<double> forceY(nodes.size(),0);
		calculateVFForRegion(nodes,forceX,forceY);
		calculateVFForNodes(nodes,forceX,forceY);

		vectorInit(force,nodes.size()*2,0.0);
		for(unsigned i=0;i<nodes.size();i++){
			force[i*2] = forceX[i];
			force[i*2+1] = forceY[i];
		}
	}
	
};

EvaluateCalculator EvaluateCalculator::evaCal;

EvaluateCalculator& EvaluateCalculator::getInstance(){
	return EvaluateCalculator::evaCal;
}
#endif