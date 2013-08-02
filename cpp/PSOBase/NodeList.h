#include <vector>
#include "Node.h"

#ifndef NODELIST
#define NODELIST

class NodeList{
private:
	static NodeList nodeList;
public:
	static NodeList& getInstance();

private:
	vector<Node> nodes;
	NodeList(){}
public:
	void initialize(int size){
		if(nodes.size()!=size){
			nodes.clear();
			nodes.assign(size,Node(0,0));
		}
	}

	vector<Node>& initNodes(vector<double>& pos){
		if(pos.size()!=nodes.size()*2){
			return vector<Node>();
		}
		for(unsigned i=0;i<nodes.size();i++){
			nodes[i].setPosX(pos[2*i]);
			nodes[i].serPosY(pos[2*i+1]);
		}
		return nodes;
	}

	vector<Node>& initNodes(vector<vector<double>>& rv){
		if(rv.size()!=nodes.size()*2){
			return vector<Node>();
		}
		for(unsigned i=0;i<nodes.size();i++){
			nodes[i].setRv(rv[i]);
		}
		return nodes;
	}

	vector<Node>& getNodes(){
		return nodes;
	}
};

NodeList NodeList::nodeList;

NodeList& NodeList::getInstance(){
	return NodeList::nodeList;
}
#endif