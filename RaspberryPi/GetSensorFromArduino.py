from __future__ import print_function
import boto3
import json
import decimal
import serial
import datetime
import time
# Helper class to convert a DynamoDB item to JSON.
class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)

dynamodb = boto3.resource('dynamodb', region_name='us-west-2')
CarNum = "5"
table_log = dynamodb.Table('Log')
table_option = dynamodb.Table('Option')
table_macro = dynamodb.Table('Macro')
ser_sensor = serial.Serial('/dev/ttyACM0',9600,timeout=1)
ser_keepet = serial.Serial('/dev/ttyUSB0',9600,timeout=1)
#ser.open()
while True:
	response_arduino = ""
	response_arduino = ser_sensor.readline()
	print(response_arduino)
	weight=1
	if response_arduino=='F':
		response_arduino=""
	if 'S' in response_arduino:
		weight=0
	if 'B' in response_arduino:
		ser_keepet.write('0/90/0')
		time.sleep(0.5)
		ser_keepet.write('0/0/0')
	if 'L' in response_arduino:
		ser_keepet.write('90/0/0')
		time.sleep(0.5)
		ser_keepet.write('0/0/0')
	if 'R' in response_arduino:
		ser_keepet.write('-90/0/0')
		time.sleep(0.5)
		ser_keepet.write('0/0/0')
	if response_arduino!="" and weight==0:
		now = datetime.datetime.now()
		result_log = table_log.put_item(
		    Item={
			 'CarNum': CarNum,
			 'Date': now.strftime('%Y-%m-%d %H:%M:%S'),
			 'value': response_arduino
		    }
		)
		#print(result_log)
		result_option = table_option.get_item(
			Key={
		    	    'CarNum':CarNum
			}
		)
		option = result_option['Item']
		macroON = json.dumps(option["option1"], indent=4, cls=DecimalEncoder)
		print(macroON)
		if macroON.replace('"',"")=="1":
			for i in range(0,20):
			# 0 to 20 repetition add 
				Num =str(i)
				#print(Num)
				result_macro = table_macro.get_item(
					Key={
				    	    'Num':Num
					}
				)
				macro = result_macro['Item']
				macro_x = json.dumps(macro["x"], indent=4, cls=DecimalEncoder)
				macro_y = json.dumps(macro["y"], indent=4, cls=DecimalEncoder)
				macro_cmd = json.dumps(macro["cmd"], indent=4, cls=DecimalEncoder)
				macro_result = macro_x.replace('"',"")+"/"+macro_y.replace('"',"")+"/"+macro_cmd.replace('"',"")
				#print(macro_result)
				ser_keepet.write(macro_result)
				ser_sensor.flush()
				time.sleep(0.5)
		#serial.flush()
