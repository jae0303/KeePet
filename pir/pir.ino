
#define SNDSENSORPIN A0
 
//int ledPin = 13; // LED 연결핀

int soundValue = 0;
int soundValueP = 0;
int switchF = 8;
int switchB = 12;
int switchL = 11;
int switchR = 9;

int buttonCheckF = LOW;
int buttonCheckB = LOW;
int buttonCheckL = LOW;
int buttonCheckR = LOW;

String soundMessage = "S";
String msg = "";
//String pirMessage = "P1";
 
void setup() {
    //pinMode(ledPin, OUTPUT); // LED Output 설정
    //pinMode(pirDegitalPin, INPUT); // 센서 Input 설정
    
    pinMode(A0, INPUT);
    pinMode(switchF, INPUT);
    pinMode(switchB, INPUT);
    pinMode(switchL, INPUT);
    pinMode(switchR, INPUT);
    
    Serial.begin(9600);
}

unsigned long timeChecker = 0;

void loop(){
    //unsigned long currentTime = millis();
  
    //delay(50);

  while(true){
    
    buttonCheckF = digitalRead(switchF);

    if(buttonCheckF == HIGH){
      Serial.println("F");
      buttonCheckF = LOW;
      delay(500);
      break;
    }

    buttonCheckB = digitalRead(switchB);

    if(buttonCheckB == HIGH){
      Serial.println("B");
      buttonCheckB = LOW;
      delay(500);
      break;
    }

    buttonCheckL = digitalRead(switchL);

    if(buttonCheckL == HIGH){
      Serial.println("L");
      buttonCheckL = LOW;
      delay(500);
      break;
    }

    buttonCheckR = digitalRead(switchR);

    if(buttonCheckR == HIGH){
      Serial.println("R");
      buttonCheckR = LOW;
      delay(500);
      break;
    }

    soundMessage = "S";    
    
    soundValue = analogRead(A0);
    
    if(soundValue > 200){
      soundMessage.concat(soundValue);
      
      Serial.println(soundMessage);
      delay(500);
      break;
    }

    buttonCheckF = LOW;
    buttonCheckB = LOW;
    buttonCheckL = LOW;
    buttonCheckR = LOW;
    
    }

    delay(1000);

}
