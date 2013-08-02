/*#include <iostream>
#include "Node.h"
#include "NodeList.h"
//#include "myutil.h"
#include "Particle.h"
using namespace std;

int test(){
	vector<double> v;
	vectorInit(v,100,0,0);
	for(unsigned i=0;i<v.size();i++){
		v[i]=i;
	}

	NodeList& l = NodeList::getInstance();
	l.initialize(50);
	vector<Node>& list = l.initNodes(v);
	for(unsigned i=0;i<list.size();i++){
		cout<<list[i].getPosX()<<" "<<list[i].getPosY()<<" ";
	}
	cout<<endl<<list.size()<<endl;

	
	Particle::initStaticVaules(20,200,100);
	vectorInit(v,40,0,0);
	NodeList::getInstance().initNodes(v);
	cout<<NodeList::getInstance().getNodes().size()<<endl;
	list = NodeList::getInstance().initNodes(v);
	for(unsigned i=0;i<list.size();i++){
		cout<<list[i].getPosX()<<" "<<list[i].getPosY()<<" ";
	}
	system("pause");
	return 0;
}*/