ACMS Example REST Service
-

This application is an example REST API that REST services can be modeled after. It includes several basic features of a modern production level REST service.

#### This Branch Features a call to an api that consumes/produces XML

These basic features include:
1. Basic package structure
2. Spring Security - Basic Authentication
3. Swagger
4. Spring Boot Actuator
5. Unit Test examples
6. Spring Profiles
7. Enabled Configuration Properties
8. Custom Exception Handler for error handling
9. Logback
10. XML request/response integration

## Instructions
Please clone this repo to your run the code to see how it works. Play around with it! Experiment with all the above noted features to get a deeper understanding of how they work so that you can confidently implement them into your own code. 

## Swagger
        http://localhost:8080/swagger-ui.html

## Spring Actuator
##### GET `/actuator/health`
get status of application
##### GET `/actuator/info`
get git information of application


## API Endpoints

#### POST `/callXmlApiTest`

###### Required Headers

| header name   | value             | description           | required  |
| :----         | :----             | :----                 | :----     |
| Content-Type  | application/json  | request object type   | true      |
| Authorization | -                 | Basic Authentication  | true      |

###### curl Request
```
curl -i -X POST \
  http://localhost:8080/callXmlApiTest \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Authorization: Basic dGVzdDp0ZXN0MTAw' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 27' \
  -H 'Content-Type: application/json' \
  -H 'Cookie: JSESSIONID=639473E390EE936177B1C7BEDEFFC42F' \
  -H 'Host: localhost:8080' \
  -H 'User-Agent: PostmanRuntime/7.16.3' \
  -d '{
	"msisdn": "1234567890"
}'
```
###### curl Response
```
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 15 Sep 2019 08:11:18 GMT

{"status":"Success"}
```

###### Request Body
```
{
    "msisdn": "1234567890"
}
```

###### Response Body
```
{
    "status": "Success"
}
```

###### Failure Response
```
{
    "code": "String",
    "reason": "String",
    "explanation": "String"
}
```

#### POST `/external/xml/api`

###### Required Headers

| header name   | value             | description           | required  |
| :----         | :----             | :----                 | :----     |
| Content-Type  | application/xml  | request object type   | true      |
| Authorization | -                 | Basic Authentication  | false      |

###### curl Request
```
curl -i -X POST \
  http://localhost:8080/external/xml/api \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Authorization: Basic dGVzdDp0ZXN0MTAw' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 101' \
  -H 'Content-Type: application/xml' \
  -H 'Cookie: JSESSIONID=639473E390EE936177B1C7BEDEFFC42F' \
  -H 'Host: localhost:8080' \
  -H 'User-Agent: PostmanRuntime/7.16.3' \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<XmlTestRequest>
	<msisdn>123456789</msisdn>
</XmlTestRequest>'
```
###### curl Response
        HTTP/1.1 200
        X-Content-Type-Options: nosniff
        X-XSS-Protection: 1; mode=block
        Cache-Control: no-cache, no-store, max-age=0, must-revalidate
        Pragma: no-cache
        Expires: 0
        X-Frame-Options: DENY
        Content-Type: application/xml
        Transfer-Encoding: chunked
        Date: Sun, 15 Sep 2019 19:53:09 GMT
        
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?><XmlTestResponse><status>Success</status></XmlTestResponse>

###### curl Fail Response
        HTTP/1.1 400
        X-Content-Type-Options: nosniff
        X-XSS-Protection: 1; mode=block
        Cache-Control: no-cache, no-store, max-age=0, must-revalidate
        Pragma: no-cache
        Expires: 0
        X-Frame-Options: DENY
        Content-Type: application/xml
        Transfer-Encoding: chunked
        Date: Sun, 15 Sep 2019 19:51:29 GMT
        Connection: close
        
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?><XmlTestErrorResponse><code>4000</code><reason>Bad Request</reason><explanation>Msisdn must be 10 or 11 digits long. Given msisdn: [123456789]</explanation></XmlTestErrorResponse>

###### Request Body
```
<?xml version="1.0" encoding="UTF-8"?>
<XmlTestRequest>
	<msisdn>string</msisdn>
</XmlTestRequest>
```

###### Response Body
```
<?xml version="1.0" encoding="UTF-8"?>
<XmlTestResponse>
	<status>string</status>
</XmlTestResponse>
```

###### Failure Response
```
<?xml version="1.0" encoding="UTF-8"?>
<XmlTestErrorResponse>
	<code>string</code>
	<explanation>string</explanation>
	<reason>string</reason>
</XmlTestErrorResponse>
```

