'use strict';

console.log('Loading post function');
var AWS = require('aws-sdk');
var dynamo = new AWS.DynamoDB.DocumentClient();
//var table = "Control";

exports.handler = function(event, context, callback) {
    //console.log('Received event:', JSON.stringify(event, null, 2));
    var table = event.table;
   if(event.table=='Control'){
       var params = {
                        TableName: event.table,
                        Item:{
                                "CarNum": event.CarNum,
                                "x": event.x,
                                "y": event.y,
                                "cmd": event.cmd
                            }
       };
   }else if(event.table=='Option'){
       var params = {
                        TableName: event.table,
                        Item:{
                                "CarNum": event.CarNum,
                                "option1": event.option1,
                                "option2": event.option2,
                                "option3": event.option3
                            }
       };
   }else if(event.table=='Macro'){
       var params = {
                        TableName: event.table,
                        Item:{
                                "Num": event.CarNum,
                                "x": event.x,
                                "y": event.y,
                                "cmd": event.cmd
                            }
       };
   }
   

    console.log("Gettings IoT device details...");
    dynamo.put(params, function(err, data) {
    if (err) {
        console.error("Unable to post device details. Error JSON:", JSON.stringify(err, null, 2));
        context.fail();
    } else {
        console.log("keepet data:", JSON.stringify(data, null, 2));
        //context.done(null,data);
        context.succeed('success post');
    }
    
});
}
