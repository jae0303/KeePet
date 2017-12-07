#include <Servo.h> 
#include <DFPlayer_Mini_Mp3.h>
#include <PS2X_lib.h>  //for v1.6
#include <math.h>

Servo myServo; 
PS2X ps2x; // create PS2 Controller Class

#define LEAST_SPEED 0


char incomingByte = 0;   // 들어오는 문자
String str = ""; // 저장되는 문자열
String getX = ""; // x값
String getY = ""; // y 값
String cmd = ""; // 커멘드값
int servoPin = 40; // 서보모터 핀
int laserPin = 34; // 레이저 핀
int pos = 0; // 서보모터 제일 처음 위치

///입력받은 좌표값

double degree = 0;
double qPi = 3.141592/4;

double FLV; // 앞왼속도
double BLV; // 뒤왼속도
double FRV; // 앞오속도
double BRV; // 뒤오속도

int spd = 100; //WHEEL SPEED


void Split(String sData, char cSeparator)
{  
  int nCount = 0;
  int nGetIndex = 0 ;

  //임시저장
  String sTemp = "";

  //원본 복사
  String sCopy = sData;

///////////////////////////////////////////////////
    //구분자 찾기
    nGetIndex = sCopy.indexOf(cSeparator);

    //리턴된 인덱스가 있나?
    if(-1 != nGetIndex)
    {
      //있다.

      //데이터 넣고
      getX = sCopy.substring(0, nGetIndex);

      Serial.println( sTemp );
    
      //뺀 데이터 만큼 잘라낸다.
      sCopy = sCopy.substring(nGetIndex + 1);
    }

    //다음 문자로~
    ++nCount;
/////////////////////////////////////////////////////////////
        nGetIndex = sCopy.indexOf(cSeparator);

    //리턴된 인덱스가 있나?
    if(-1 != nGetIndex)
    {
      //있다.

      //데이터 넣고
      getY = sCopy.substring(0, nGetIndex);

      Serial.println( sTemp );
    
      //뺀 데이터 만큼 잘라낸다.
      cmd = sCopy.substring(nGetIndex + 1);
    }

      //없으면 마무리 한다.
}
  

void forward()
{
  analogWrite(3, spd);      // FL M1
  analogWrite(5, 0);        // FL M1
  analogWrite(6, spd);      // BL M2
  analogWrite(8, 0);        // BL M2

  analogWrite(9, 0);        // BR M3
  analogWrite(10, spd);     // BR M3
  analogWrite(11, 0);       // FR M4
  analogWrite(12, spd);     // FR M4
}

void forward_right()
{
  analogWrite(3, spd);      // FL
  analogWrite(5, 0);     // FL
  analogWrite(6, 0);      // BL
  analogWrite(8, 0);     // BL

  analogWrite(9, 0);     // BR
  analogWrite(10, spd);    // BR
  analogWrite(11, 0);     // FR
  analogWrite(12, 0);    // FR
}
void forward_left()
{
  analogWrite(3, 0);      // FL
  analogWrite(5, 0);     // FL
  analogWrite(6, spd);      // BL
  analogWrite(8, 0);     // BL

  analogWrite(9, 0);     // BR
  analogWrite(10, 0);    // BR
  analogWrite(11, 0);     // FR
  analogWrite(12, spd);    // FR
}

void backward()
{
  analogWrite(3, 0);     // FL
  analogWrite(5, spd);    // FL
  analogWrite(6, 0);     // BL
  analogWrite(8, spd);    // BL

  analogWrite(9, spd);    // BR
  analogWrite(10, 0);     // BR
  analogWrite(11, spd);    // FR
  analogWrite(12, 0);     // FR
}

void backward_left()
{
  analogWrite(3, 0);     // FL
  analogWrite(5, spd);    // FL
  analogWrite(6, 0);     // BL
  analogWrite(8, 0);    // BL

  analogWrite(9, spd);    // BR
  analogWrite(10, 0);     // BR
  analogWrite(11, 0);    // FR
  analogWrite(12, 0);     // FR
}

void backward_right()
{
  analogWrite(3, 0);     // FL
  analogWrite(5, 0);    // FL
  analogWrite(6, 0);     // BL
  analogWrite(8, spd);    // BL

  analogWrite(9, 0);    // BR
  analogWrite(10, 0);     // BR
  analogWrite(11, spd);    // FR
  analogWrite(12, 0);     // FR
}
void right()
{
  analogWrite(3, spd);      // FL
  analogWrite(5, 0);     // FL
  analogWrite(6, 0);      // BL
  analogWrite(8, spd);     // BL

  analogWrite(9, 0);   // BR
  analogWrite(10, spd);    // BR
  analogWrite(11, spd);    // FR
  analogWrite(12, 0);   // FR
}
void left()
{
  analogWrite(3, 0);      // FL
  analogWrite(5, spd);     // FL
  analogWrite(6, spd);      // BL
  analogWrite(8, 0);     // BL

  analogWrite(9, spd);   // BR
  analogWrite(10, 0);    // BR
  analogWrite(11, 0);    // FR
  analogWrite(12, spd);   // FR
}
void turn_left()
{
  analogWrite(3, 0);      // FL
  analogWrite(5, spd);     // FL
  analogWrite(6, 0);      // BL
  analogWrite(8, spd);     // BL

  analogWrite(9, 0);     // BR
  analogWrite(10, spd);    // BR
  analogWrite(11, 0);     // FR
  analogWrite(12, spd);    // FR
}

void turn_right()
{
  analogWrite(3, spd);      // FL
  analogWrite(5, 0);     // FL
  analogWrite(6, spd);      // BL
  analogWrite(8, 0);     // BL

  analogWrite(9, spd);     // BR
  analogWrite(10, 0);    // BR
  analogWrite(11, spd);     // FR
  analogWrite(12, 0);    // FR
}

void stop_wheel()
{
  analogWrite(3, 0);      // FL
  analogWrite(5, 0);     // FL
  analogWrite(6, 0);      // BL
  analogWrite(8, 0);     // BL

  analogWrite(9, 0);   // BR
  analogWrite(10, 0);    // BR
  analogWrite(11, 0);    // FR
  analogWrite(12, 0);   // FR
}

void FLF()
{
  if(FLV > 255)
  {
    FLV = 255;
  }
  analogWrite(3, FLV);      // 
  analogWrite(5, 0);     // 
}

void FLB()
{
  if(FLV > 255)
  {
    FLV = 255;
  }
  analogWrite(3, 0);      // 
  analogWrite(5, FLV);     // 
}

void BLF()
{
  if(BLV > 255)
  {
    BLV = 255;
  }
  analogWrite(6, BLV);      // 
  analogWrite(8, 0);     // 
}
void BLB()
{
  if(BLV > 255)
  {
    BLV = 255;
  }
  analogWrite(6, 0);      // 
  analogWrite(8, BLV);     // 
}

void FRF()
{
  if(FRV > 255)
  {
    FRV = 255;
  }
  analogWrite(11, FRV);      
  analogWrite(12, 0);     //
}
void FRB()
{
  if(FRV > 255)
  {
    FRV = 255;
  }
  analogWrite(11, 0);      // 
  analogWrite(12, FRV);     // 
}
void BRF()
{
  if(BRV > 255)
  {
    BRV = 255;
  }
  analogWrite(10, BRV);      // FL
  analogWrite(9, 0);     // FL
}
void BRB()
{
  if(BRV > 255)
  {
    BRV = 255;
  }
  analogWrite(10, 0);      // FL
  analogWrite(9, BRV);     // FL
}

void calc()
{

  degree = atan2(getY.toInt(),-getX.toInt()); //calc degree of car


  FLV = 255*sin(degree -qPi);
  FRV = -255*cos(degree -qPi);  
  BLV = 255*cos(degree -qPi);
  BRV = 255*sin(degree -qPi);   //equations 1 but not same with doc

/*
  FLV = 255*sin(degree ) - mapR; 
  FRV = 255*cos(degree )  mapR;
  BLV = 255*cos(degree) - mapR; 
  BRV = 255*sin(degree ) - mapR;  //equations 1 but not same with doc

*/

if(getX.toInt()==0 && getY.toInt()==0)
  {
    stop_wheel();
  }
  else
  {
    if(FLV >= 0)
    {
      if(FLV < LEAST_SPEED)
      {
        FLV = LEAST_SPEED; 
      }
        FLF();
    }
  else if(FLV< 0)
  {
    FLV = -FLV;
    if(FLV < LEAST_SPEED)
    {
      FLV = LEAST_SPEED; 
    }
    FLB();  
  }

  if(FRV >= 0)
  {
    if(FRV < LEAST_SPEED)
    {
      FRV = LEAST_SPEED; 
    }
    FRF();
  }
  else if(FRV< 0)
  {
    FRV = -FRV;
    if(FRV < LEAST_SPEED)
    {
      FRV = LEAST_SPEED; 
    }
    FRB();  
  }

  if(BLV >= 0)
  {
    if(BLV < LEAST_SPEED)
    {
      BLV = LEAST_SPEED; 
    }
    BLF();
  }
  else if(BLV< 0)
  {
    BLV = -BLV;
    if(BLV < LEAST_SPEED)
    {
      BLV = LEAST_SPEED; 
    }
    BLB();  
  }

  if(BRV >= 0)
  {
    if(BRV < LEAST_SPEED)
    {
      BRV = LEAST_SPEED; 
    }
    BRF();
  }
  else if(BRV< 0)
  {
    BRV = -BRV;
    if(BRV < LEAST_SPEED)
    {
      BRV = LEAST_SPEED; 
    }
    BRB();  
  }
  
}
}





    void setup() {
     Serial.begin(9600);     // opens serial port, sets data rate to 9600 bps
     Serial3.begin (9600);
     
       for (int i = 3; i < 13; i++) // 차량 핀번호 3~12
    pinMode(i, OUTPUT); //set to output

      myServo.attach(servoPin); // 9번핀에 서보모터
      
      pinMode(laserPin, OUTPUT); // 레이저 포인터
        
     mp3_set_serial (Serial3);      // DFPlayer-mini mp3 module 시리얼 세팅
     mp3_set_volume (40);          // 볼륨조절 값 0~30
     delay(10);                     // 볼륨값 적용을 위한 delay

    }

    void loop() {

            while (Serial.available() > 0) {
                    // read the incoming byte:
                    incomingByte = (char)Serial.read();
                    str += incomingByte;

                    // say what you got:
                    Serial.print("I received: ");
                    Serial.println(incomingByte);
                    Serial.println(str);
            }
           
Split(str, '/');
Serial.print("들어오는 값");
Serial.println(str);
  Serial.print("x : ");

Serial.println(getX);
  Serial.print("y : ");

Serial.println(getY);
  Serial.print("cmd : ");

Serial.println(cmd);
Serial.println("끝");

calc();
  Serial.print("atan : ");
  Serial.println(degree);
  Serial.print("V1 : ");
  Serial.println(FLV);
  Serial.print("    V2 : ");
  Serial.println(FRV);
  Serial.print("    V3 : ");
  Serial.println(BLV);
  Serial.print("    V4 : ");
  Serial.println(BRV);

if(cmd == "1") // 서보모터 켜기
{
   for (pos = 0; pos <= 180; pos += 1) 
    { // goes from 0 degrees to 180 degrees 
     // in steps of 1 degree 
     myServo.write(pos);              // tell servo to go to position in variable 'pos' 
     delay(15);                       // waits 15ms for the servo to reach the position 
    } 
       for (pos = 180; pos >= 0; pos -= 1) 
    { // goes from 0 degrees to 180 degrees 
     // in steps of 1 degree 
     myServo.write(pos);              // tell servo to go to position in variable 'pos' 
     delay(15);                       // waits 15ms for the servo to reach the position 
    } 

}
if(cmd == "2") // 서보모터 끄기
{
   
     myServo.write(0);              // tell servo to go to position in variable 'pos' 
     delay(15);                       // waits 15ms for the servo to reach the position 
    

}

if(cmd == "3") // 레이저 포인터  켬
{
  digitalWrite(laserPin, HIGH);   // turn the LED on (HIGH is the voltage level)
  delay(1);                       // wait for a second

 

}
if(cmd == "4") // 레이저 포인터 끔
{
 digitalWrite(laserPin, LOW);    // turn the LED off by making the voltage LOW
  delay(1);                       // wait for a second

}

if(cmd == "5") // 시계방향
{
turn_right();
  delay(2000);                       // wait for a second

}
if(cmd == "6") // 반시계방향
{
turn_left();
  delay(2000);                       // wait for a second

}

if(cmd == "7") // 스피커
{
  delay (10);
    //mp3_play ();
    mp3_play (1);    //고양이 파일 플레이
    delay (1000);
    mp3_stop ();

}
///////////////////////이거 없애야 함!
str = "";
//getX = "";
//getY = "";
//cmd = "";
       
    }

 
