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
	 * ����δ���ǵ�ʹ�����֮���������
	 */
	void calculateVFForRegion(vector<Node>& nodes,vector<double>& forceX,vector<double>& forceY){
		//����������ռ��'����'
		double M = grid*grid;
		for(int i=0;i<grids.size();i++){
			for(int j=0;j<grids[i].size();j++){
				//����Ѿ�������,�򲻼���
				if(grids[i][j])
					continue;
				double x = grid*i+posmin;
				double y = grid*j+posmin;
				for(int k=0;k<nodes.size();k++){
					double x1 = nodes[k].getPosX();
					double y1 = nodes[k].getPosY();
					//��ǰ�������ڵ�ľ����ƽ��
					double distance2 = pow(x-x1,2)+pow(y-y1,2)+0.1;
					//��ǰ�������ڵ�ľ���
					double distance = sqrt(distance2);
					//Rc<d<Riʱ,�������ڵ�Ż��ܵ����������(rc*M)/(dis*dis)
					double rc = nodes[k].getRc();
					if(rc < distance && distance < nodes[k].getConnectedRi()/*&&!nodes[k].isInSensedRegion(x, y)*/){
						forceX[k] += ((x-x1)/distance)*(rc*M/distance2);
						forceY[k] += ((y-y1)/distance)*(rc*M/distance2);
						//���׵ķ���,������
						//forceX[k] += ((x-x1)/distance)*(rc*rc/distance2);
						//forceY[k] += ((y-y1)/distance)*(rc*rc/distance2);
					}
				}
				
			}
		}
	}
	
	/**
	 * ���㴫�����ڵ�֮��������������ٵ����ٶ�
	 */
	void calculateVFForNodes(vector<Node>& nodes,vector<double>& forceX,vector<double>& forceY){
		for(unsigned i=0;i<nodes.size();i++){
			double x1 = nodes[i].getPosX();
			double y1 = nodes[i].getPosY();
			
			for(unsigned j=i+1;j<nodes.size();j++){
				//distance>Ri,���ڵ�֮�䲻����������
				if(!nodes[j].isConnected(nodes[i]))
					continue;
				
				//����ڵ�֮��������
				double x2 = nodes[j].getPosX();
				double y2 = nodes[j].getPosY();
				
				//���ڵ�֮���������=(ri*rj)/(d*d)
				double distance2 = pow(x1-x2,2)+pow(y1-y2,2)+0.1;//����ƽ��
				double forceAbsolute = ((nodes[i].getRc() * nodes[j].getRc()) / distance2);
				
				//���׵ķ���,������(ri*ri*rj*rj)/(d*d)
				//double forceAbsolute = ((nodes[i].getRc()*nodes[i].getRc() * nodes[j].getRc()*nodes[j].getRc()) / distance2);
				
				//((x1-x2)/distance) ��ʾ����X��ķ���,����������,��λ������X��ķ���
				//((y1-y2)/distance) ��ʾ����Y��ķ���,����������,��λ������Y��ķ���
				double distance = sqrt(distance2);//����
				double forceInX = ((x1-x2)/distance)*forceAbsolute;
				double forceInY = ((y1-y2)/distance)*forceAbsolute;
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
	* �����ƶ�����
	*/
	double getMoveDiatance(vector<Node>& nodes){
		double distance = 0;
		for(int i=0;i<nodes.size();i++){
			distance+=nodes[i].getDistance(iniPos[i*2], iniPos[i*2+1]);
		}
		return distance;
	}

	/*
	* �ȼ��㸲����,����δ���ǵ�����
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
					if(1.0-unSensedP > sensedCth){
						break;
					}
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
	* ���Ÿ���δ�����������������,����������������´ε�������λ��
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