'use strict';

console.log('Loading function');
var AWS = require('aws-sdk');
var dynamo = new AWS.DynamoDB.DocumentClient();
//var table = "Macro";

exports.handler = function(event, context, callback) {
    //console.log('Received event:', JSON.stringify(event, null, 2));
   if(event.table=='Option'){
       var params = {
                        TableName:event.table,
                        Key:{
                                "CarNum": event.CarNum
                            }
                    };
   }else if(event.table=='Macro'){
       var params = {
                        TableName:event.table,
                        Key:{
                                "Num": event.Num
                            }
                    };
   }     
   

    console.log("Gettings IoT device details...");
    dynamo.get(params, function(err, data) {
    if (err) {
        console.error("Unable to get device details. Error JSON:", JSON.stringify(err, null, 2));
        context.fail();
    } else {
        console.log("Student data:", JSON.stringify(data, null, 2));
           //context.done(null,data);
           context.succeed(data);
    }
    
});
}
