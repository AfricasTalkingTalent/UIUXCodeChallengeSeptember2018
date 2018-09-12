//Import the required libraries
querystring = require('querystring');
https       = require('https');
var username = "kimanimbuguah@gmail.com";
var apiKey   = "16f614c976276acdc4234cc7170ac7eac9f44212c182fe31c8d191d63f4fd3ba";
//create the send airime function
function sendAirtime(recipients_) {
    //Build post string from object
    
 var post_data = querystring.stringify({
       'username'   : username,
       'recipients' : recipients_
 });
 //create the request paramater object
 var request_parameters = {
      host    : 'api.africastalking.com',
      port    : 443,
      path    : '/version1/airtime/send',
      
      rejectUnauthorized : false,
      requestCert        : true,
      agent              : false,
      
      method  : 'POST',
      
      headers : {
                     
             'Content-Type'   : 'application/x-www-form-urlencoded',
             'Content-Length' : post_data.length,
             'apikey'         : apiKey,
             'Accept'         : 'application/json'
          }
   }
                         
  request = https.request(request_parameters, function (response) {
     response.setEncoding('utf8');
     response.on('data', function (data_chunk) {
          try {
         if(response.statusCode != 201)
            throw data_chunk;
         var jsObject       = JSON.parse(data_chunk);
         var result_details = jsObject.responses;
         var logStr = "";
         for (recipient in result_details) {
           logStr    += "\nStatus => "       + result_details[recipient].status;
           logStr    += ";phone number => "  + result_details[recipient].phoneNumber;
           logStr    += ";amount => "        + result_details[recipient].amount;
           logStr    += ";cost => "          + result_details[recipient].cost;
           logStr    += ";discount => "      + result_details[recipient].discount;
           logStr    += ";error message => " + result_details[recipient].errorMessage;
         }
          console.log(logStr);
        }
        catch (error) {
         console.log("Error: " + error);
        }
     });
  });
  
  request.write(post_data);
  request.end();
}
// Specify an array to hold airtime recipients and amount
var airtime, RecipientsList = [];
// Specify the first recipients to a json object and add it to the airtimeRecipientsList
// Please ensure you include the country code for phone numbers (+254 for Kenya in this case)
// Please ensure you include the country code for phone numbers (KES for Kenya in this case)
airtimeRecipientsList[0] = {phoneNumber : '+254725136849', amount : 'KES XXX'};
// Specify the second recipient
airtimeRecipientsList[1] = {phoneNumber : '+254723414862', amount : 'KES YYY'};
// Convert the airtimeRecipientsList to a string
// NB: The expected string format is: [{phoneNumber : '+254711XXXYYY', amount : 'KES XXX'}, {phoneNumber : '+254733YYYZZZ', amount : 'KES YYY'}]
var recipient_string = JSON.stringify(airtimeRecipientsList);
//call the function
sendAirtime(recipient_string);