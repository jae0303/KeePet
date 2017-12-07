var aws = require('aws-sdk');
var ses = new aws.SES({
   region: 'us-west-2'
});

exports.handler = function(event, context) {
    console.log("Incoming: ", event);
   // var output = querystring.parse(event);

    var eParams = {
        Destination: {
            ToAddresses: ["dlsnfl18@gmail.com"]
        },
        Message: {
            Body: {
                Text: {
                    Data: "Pet needs you!"
                }
            },
            Subject: {
                Data: "Please check your pet"
            }
        },
        Source: "jamen1444@gmail.com"
    };

    console.log('===SENDING EMAIL===');
    var email = ses.sendEmail(eParams, function(err, data){
        if(err) console.log(err);
        else {
            console.log("===EMAIL SENT===");
            console.log(data);


            console.log("EMAIL CODE END");
            console.log('EMAIL: ', email);
            context.succeed(event);

        }
    });

};