#ifdef RaspberryPi 
#include <stdio.h> //for printf
#include <stdint.h> //uint8_t definitions
#include <stdlib.h> //for exit(int);
#include <string.h> //for errno
#include <errno.h> //error output
#include <wiringPi.h>
#include <wiringSerial.h>
 
char device[]= "/dev/ttyACM0";
int fd;
unsigned long baud = 9600;
unsigned long time=0;
 
char strHello[] = "HELLO";
char cTemp[512] = {0};
int ch;
char Endc;
 
//prototypes
int main(void);
void loop(void);
void setup(void);
 
void setup(){
 
  printf("%s \n", "Raspberry Startup!");
  fflush(stdout);
 
  //get filedescriptor
  if ((fd = serialOpen (device, baud)) < 0){
    fprintf (stderr, "Unable to open serial device: %s\n", strerror (errno)) ;
    exit(1); //error
  }
 
  //setup GPIO in wiringPi mode
  if (wiringPiSetup () == -1){
    fprintf (stdout, "Unable to start wiringPi: %s\n", strerror (errno)) ;
    exit(1); //error
  }
}
 
// main function for normal c++ programs on Raspberry
int main(){
  setup();
  FILE * fp=fopen("/home/pi/Desktop/transfer.txt","rt");
  if(fp==NULL){
    printf("file open fail\n");
    return -1;
}else{  
   printf("file open success\n"); 
   while(fgets(cTemp, sizeof(cTemp),fp) != NULL){
      serialPuts (fd, cTemp);
      printf(cTemp);
}
  
if(feof(fp) != 0){
    printf("file upload success\n");
}
 else
    printf("file upload fail\n");
 fclose(fp);
}
 
  return 0;
}
 
#endif //#ifdef RaspberryPi
