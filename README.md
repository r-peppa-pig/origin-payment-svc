FORMAT: 1A

# Origin Payment Service

Provides functionality to save payment

###### Request
1. referenceNumber : cannot be null, min number of digits 5, max number of digits 10
- bsb : cannot be null, can only be 6 digits long
- accountNumber : cannot be null, can only be 6 digits long
- amount : cannot be null, min 0.01, max 2000.00

###### Response
- message

###### Error
Error object representing error response sent to the API call
- code : error code representing the configured code
- message : user friendly message providing short explanation
- timestamp : current datetime
