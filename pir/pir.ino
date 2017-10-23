#define SNDSENSORPIN A0
 
//int ledPin = 13; // LED 연결핀
int pirDegitalPin = 2; // 센서 시그널핀
int pirState = LOW; // PIR 초기상태
int pirVal = 0; // Signal 입력값

int soundValue = 0;
int soundValueP = 0;

String soundMessage = "S";
 
void setup() {
    //pinMode(ledPin, OUTPUT); // LED Output 설정
    pinMode(pirDegitalPin, INPUT); // 센서 Input 설정
    
    pinMode(A0, INPUT);
    
    Serial.begin(9600);
}

void loop(){
    pirVal = digitalRead(pirDegitalPin); // 센서값 읽기
    if (pirVal == HIGH) { // 인체감지시
      Serial.println("P1");
        pirState = LOW;
    }
    /*else{
      Serial.println("P0");
    }*/

    soundValue = analogRead(A0);
    soundMessage.concat(soundValue);
    
    if((soundValue - soundValueP)>=100){
      Serial.println(soundMessage);
    }
    
    soundMessage = "S";
    
    soundValueP = soundValue;
    
    delay(500);
}
 /*
void loop(){
    val = digitalRead(inputPin); // 센서값 읽기
    if (val == HIGH) { // 인체감지시
        digitalWrite(ledPin, HIGH); // LED ON
        if (pirState == LOW) {
        // 시리얼모니터에 메시지 출력
        Serial.println("Motion detected!");
        pirState = HIGH;
        }
    } else {
        digitalWrite(ledPin, LOW); // LED OFF
        if (pirState == HIGH){        
            // 시리얼모니터에 메시지 출력            
            Serial.println("Motion ended!");
            pirState = LOW;
        }
    }
}
*/
