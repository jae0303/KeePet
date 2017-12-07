from __future__ import print_function # Python 2/3 compatibility
import boto3
import json
import decimal
import serial
import string
import time
from boto3.dynamodb.conditions import Key, Attr
from botocore.exceptions import ClientError

# Helper class to convert a DynamoDB item to JSON.
class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)

dynamodb = boto3.resource("dynamodb", region_name='us-west-2')

table = dynamodb.Table('Control')
count=0
CarNum = "5"
test=serial.Serial("/dev/ttyUSB0",9600)
#test.open()
try:
    while True:
    	response = table.get_item(
        	Key={
            	    'CarNum': CarNum
        	}
    	)
	item = response['Item']
	#print("GetItem success")
	value_x = json.dumps(item["x"], indent=4, cls=DecimalEncoder)
	value_y = json.dumps(item["y"], indent=4, cls=DecimalEncoder)
	value_cmd = json.dumps(item["cmd"], indent=4, cls=DecimalEncoder)
	
	value = value_x.replace('"',"")+"/"+value_y.replace('"',"")+"/"+value_cmd.replace('"',"")
	#value = "%d/%d/%d"%(value_x,value_y,value_cmd)
	if value=="0/0/0" and count==0:
		count=1
		print(value)
		test.write(value)
		#time.sleep(0.5)
	elif value=="0/0/0" and count==1:
		print("ignore")
		#test.write(value)
	else:
		print(value)
		test.write(value)
		#time.sleep(0.5)
		count=0
	time.sleep(0.5)
except ClientError as e:
    print(e.response['Error']['Message'])
#else:
    #item = response['Item']
    #print("GetItem succeeded:")
    #value_x = json.dumps(item["x"], indent=4, cls=DecimalEncoder)
    #value_y = json.dumps(item["y"], indent=4, cls=DecimalEncoder)
    #value_cmd = json.dumps(item["cmd"], indent=4, cls=DecimalEncoder)
    # value = "123"
    # print(value)
    #rot13 = string.maketrans(
#	"ABCDEFGHIJKLMabcdefghijklmNOPQRSTUVWXYZnopqrstuvwxyz",
#	"NOPQRSTUVWXYZnopqrstuvwxyzABCDEFGHIJKLMabcdefghijklm")
    # test=serial.Serial("/dev/ttyUSB0",9600)
    # test.open()
 #   try:
	#while True:
		# line = test.readline()
	# print(value)
	# print(value)
	#value = value_x+"/"+value_y+"/"+value_cmd
	#print value
	# test.write(value)
	#test.write(bytes(value.encode("ascii")))
	# timer.start()
  #  except KeyboardInterrupt:
#	pass # do cleanup here

    # test.close()
