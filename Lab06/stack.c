#include <stdio.h>

void func(int a, int b, int c){
    char buffer[8];
    int *r;
    r= (int *)(buffer + 10);
    (*r) += 7;
}

int main(){
    int x;
    x =0;
    func(1,2,3);
    x=1;
    printf("%d\n",x);
    return 0;
}