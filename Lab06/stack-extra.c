
#include <stdio.h>
#include <string.h>

#define PASSWORD "1234567"
 
int verify_password(char * password)
{
    char buffer[8];
	int authenticated;
	//add local buff to be overflowed
	
	authenticated=strcmp(password,PASSWORD);
	strcpy(buffer,password);
	return authenticated;
}

int succeed(){
    printf("YAY HACKED\n");
}

int fail(){
    printf("you are fked up\n");
}
int main()
{
	int valid_flag = 0;
	char password[1024];
	while(1)
	{
		printf("please input password:");
		scanf("%s",password);
		valid_flag = verify_password(password);
		if(valid_flag)
		{
			fail();
		}
		else
		{
			succeed();
			break;
		}
	}
	return 0;
}