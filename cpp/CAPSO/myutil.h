#include <algorithm>
#include <vector>
#include "d_random.h"
using namespace std;

#ifndef MYUTIL
#define MYUTIL

//----------------------------------------------------------------------------------//
template <class T>
void vectorInit(vector<T>& v,int dim,T value);
//
void vectorInit(vector<double>& v,int dim,double max,double min);
//
template <class T>
void vectorFill(vector<T>& v,T element);
//
void vectorFill(vector<double>& v,double max,double min);
//
template <class T>
void vectorCopy(vector<T>& src,int begin1,vector<T>& des,int begin2,int length);
//----------------------------------------------------------------------------------//
template <class T>
void vectorInit(vector<T>& v,int dim,T value){
	v.clear();
	v.assign(dim,value);
}
void vectorInit(vector<double>& v,int dim,double max,double min){
	randomNumber rnd;
	v.clear();
	for(int i=0;i<dim;i++){
		v.push_back(rnd.frandom()*(max-min)+min);
	}
}
template <class T>
void vectorFill(vector<T>& v,T element){
	for(unsigned i=0;i<v.size();i++){
		v[i] = element;
	}
}
void vectorFill(vector<double>& v,double max,double min){
	randomNumber rnd;
	for(unsigned i=0;i<v.size();i++){
		v[i] = rnd.frandom()*(max-min)+min;
	}
}
template <class T>
void vectorCopy(vector<T>& src,int begin1,vector<T>& des,int begin2,int length){
	for(int i=0;i<length;i++){
		des[begin2+i] = src[begin1+i];
	}
}

#endif