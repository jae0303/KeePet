//include system librarys
#include <stdio.h> //for printf
#include <stdint.h> //uint8_t definitions
#include <stdlib.h> //for exit(int);
#include <string.h> //for errno
#include <errno.h> //error output
 
//wiring Pi
#include <wiringPi.h>
#include <wiringSerial.h>
 
// Find Serial device on Raspberry with ~ls /dev/tty*
// ARDUINO_UNO "/dev/ttyACM0"
// FTDI_PROGRAMMER "/dev/ttyUSB0"
// HARDWARE_UART "/dev/ttyAMA0"
char device[]= "/dev/ttyACM0";
// filedescriptor
int fd;


unsigned long baud = 9600;
unsigned long time=0;
 
void setup(){
 
  printf("Raspberry Startup!\n");
  fflush(stdout);
 
  //get filedescriptor
  if ((fd = serialOpen (device, baud)) < 0){
    fprintf (stderr, "Unable to open serial device\n") ;
    exit(1); //error
  }
 
  //setup GPIO in wiringPi mode
  if (wiringPiSetup () == -1){
    fprintf (stdout, "Unable to start wiringPi: %s\n", strerror (errno)) ;
    exit(1); //error
  }
 
}
	
void loop(){
	
    if(serialDataAvail(fd)){
    	char newChar =  serialGetchar(fd);
    	printf("%c", newChar);
    	fflush(stdout);
	}	
}
// main function for normal c++ programs on Raspberry
int main(){
  setup();
  //printf("Start sensor message printing!\n");
  while(1) loop();
  return 0;
}
