#include <iostream>
#include <vector>
#include <cmath>
#include "d_random.h"
using namespace std;

#ifndef NODE
#define NODE

static const double PI = 3.141592654;
class Node
{
private:
	double posx,posy;	//�ڵ�λ��
	vector<double> Rv;	//ÿ���Ƕ��ϵĲ��ɿ��в�����뾶
private:
	static int Angle;	//�Ƕ�����
	static int D;		//Rc-Rp ֮����  50 block
	static double connectedRi;	//ͨ�Ű뾶
	static double Rc;	//�ɿ��в���
	static double Rp;	//Բ��
	static double s;	//˥������
	static randomNumber r;
public:
	//static
	static void initializeStaticValue(double ri,double rc,double rp,double s,int angle,int d);
public:
	Node(double x,double y):Rv(Angle,0){
		iniRv();
		posx = x;
		posy = y;
	}
	double getRc(){
		return Rc;
	}
	double getConnectedRi(){
		return connectedRi;
	}
	double getPosX() {
		return posx;
	}
	double getPosY() {
		return posy;
	}
	void setPosX(double x) {
		posx = x;
	}
	void serPosY(double y) {
		posy = y;
	}
	double getDistance(double x, double y) {
		return sqrt( (x-posx)*(x-posx) + (y-posy)*(y-posy) );
	}
	double getDistance(Node& node){
		return sqrt( (node.posx-posx)*(node.posx-posx) + (node.posy-posy)*(node.posy-posy) );
	} 
	bool isConnected(Node& node){
		return getDistance(node) <= connectedRi;
	}

	double getSensedPi(double x, double y) {
		double d = getDistance(x, y);
		if(d>Rp){
			return 0;
		}
		if(d<Rc){
			return 1;
		}
		double angle = calcuAngle(x, y);
		double rv = getRvByAngle(angle);
		if(d>rv){
			return 0;
		}
		return calcuPi(d);
	}

	bool isInSensedRegion(double x,double y){
		double angle = calcuAngle(x, y);
		double rv = getRvByAngle(angle);
		return getDistance(x, y)<rv;
	}

	vector<double>& getRv(){
		return this->Rv;
	}

	void setRv(vector<double>& rv){
		this->Rv = rv;
	}
private:
	void iniRv(){
		for(unsigned i=0;i<Rv.size();i++){
			int j = 0;
			for(j=0;j<D;j++){
				double pi = calcuPi((Rp-Rc)/D*j+Rc);
				if(pi<r.frandom()){
					break;
				}
			}
			Rv[i] = (Rp-Rc)/D*j+Rc;
		}
	}
	
	/**
	 * ����Ŀ������(x,y)��p��ĽǶȷ�λ
	 * @param x
	 * @param y
	 * @return
	 */
	double calcuAngle(double x,double y){
		return ((int)(atan2(y-posy,x-posx)/PI*Angle+Angle))%Angle;
	}
	
	/**
	 * ����ĳ���Ƕ��ϵĲ��ɿ��в�뾶
	 * @param angle �Ƕ�
	 * @return
	 */
	double getRvByAngle(double angle){
		int angle1 = (int)angle%Angle;
		int angle2 = (angle1+1)%Angle;
		double d1 = Rv[angle1];
		double d2 = Rv[angle2];
		return (d1+d2)/2;
	}

	static double calcuPi(double d){
		if(d>Rp)
			return 0;
		if(d<Rc)
			return 1;
		return exp(-s*(d-Rc));
	}
};
int Node::Angle = 360;
int Node::D = 10;
double Node::connectedRi = 50;
double Node::Rc = 20;
double Node::Rp = 30;
double Node::s = 0.050;
randomNumber Node::r;

void Node::initializeStaticValue(double ri,double rc,double rp,double s,int angle,int d){
	Node::Angle = angle;
	Node::D = d;
	Node::connectedRi = ri;
	Node::Rc = rc;
	Node::Rp = rp;
	Node::s = s;
}
#endif
