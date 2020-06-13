#include <stdio.h>
//2017212206
int main(){
	char *a = "#include <stdio.h>%c//2017212206%cint main(){%c%cchar *a = %c%s%c;%c%cprintf( a, 10, 10, 10, 9, 34, a, 34, 10, 9, 10, 9, 10 );%c%creturn 0;%c}";
	printf( a, 10, 10, 10, 9, 34, a, 34, 10, 9, 10, 9, 10 );
	return 0; 
}

