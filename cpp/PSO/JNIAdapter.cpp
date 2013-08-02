#include "NodeList.h"
#include "Particle.h"
#include "myutil.h"
#include "PSO.h"
#include "EvaluateCalculator.h"
#include <time.h>
#include <fstream>
using namespace std;

void test();

int main(){
	//test();
	ofstream out("time.txt",ios_base::app);
	clock_t start, finish;

		start = clock();

		int nodeSize = 24;
		double posMax = 300;
		double posMin = 100;
		double grid = 5;
		double sensedCth = 0.8;
		Node::initializeStaticValue(50,20,30,0.050,360,10);
		NodeList::getInstance().initialize(nodeSize);
		EvaluateCalculator::getInstance().initialize(nodeSize,posMax,posMin,grid,sensedCth);
		Particle::initStaticVaules(nodeSize*2,posMax,posMin);

		PSO pso(20,500);
		pso.run();
		cout<<Particle::getGlobalFitness()<<endl;
		
		double cov = EvaluateCalculator::getInstance().getUnionCoverage(NodeList::getInstance().initNodes(Particle::getGlobalBestPos()));
		cout<<cov<<endl;

		finish = clock();
		out<< (double)(finish - start) / CLOCKS_PER_SEC<<endl;

	out.close();
}
void test(){
	vector<double> v;

	vectorInit(v,100,0.0);
	NodeList::getInstance().initialize(50);
	vector<Node>& list1 = NodeList::getInstance().initNodes(v);
	for(unsigned i=0;i<list1.size();i++){
		cout<<list1[i].getPosX()<<" "<<list1[i].getPosY()<<" ";
	}
	cout<<endl<<list1.size()<<endl;
	NodeList::getInstance().getNodes()[0].setPosX(100);
	//vectorInit(v,100,2.0);
	//NodeList::getInstance().initNodes(v);
	vector<Node>& list2 = NodeList::getInstance().getNodes();
	for(unsigned i=0;i<list2.size();i++){
		cout<<list2[i].getPosX()<<" "<<list2[i].getPosY()<<" ";
	}
	cout<<endl<<list2.size()<<endl;
	for(unsigned i=0;i<list1.size();i++){
		cout<<list1[i].getPosX()<<" "<<list1[i].getPosY()<<" ";
	}
	system("PAUSE");
}