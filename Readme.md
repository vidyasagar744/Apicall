# REST API example application

This is a bare-bones example of a Sinatra application providing a REST
API to a DataMapper-backed model.

The entire application is contained within the `app.rb` file.

`config.ru` is a minimal Rack configuration for unicorn.

`run-tests.sh` runs a simplistic test and generates the API
documentation below.

It uses `run-curl-tests.rb` which runs each command defined in
`commands.yml`.

## Install

    bundle install

## Run the app

    unicorn -p 9192

# REST API

The REST API to the example app is described below.

## Get list of Students

### Request

`GET /student/`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Connection: close
    Content-Type: application/json


## Create a new Student

### Request

`POST /student/`

    curl -i -H 'Accept: application/json' -d 'studentId = 2506, studentName = name, mailId = example@example.com, phoneNumber = 9999999 & department = EEE' http://localhost:9192/student

### Response

    HTTP/1.1 200 Created
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 201 Created
    Connection: close
    Content-Type: application/json
    
    {"studentId": 2506,"studentName": "name","mailId": "example@example.com","phoneNumber": 9999999,"department": "EEE"}

## Get a specific Student

### Request

`GET /student/id`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/2506

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

## Get a non-existent Student

### Request

`GET /student/id`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/9999

### Response

    HTTP/1.1 404 Not Found
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json

    {"status":404,"reason":"Not found"}

## Create another new Student

### Request

`POST /student/`

    curl -i -H 'Accept: application/json' -d 'name=Bar&junk=rubbish' http://localhost:9192/student

### Response

    HTTP/1.1 201 Created
    Date: Thu, 24 Feb 2011 12:36:31 GMT
    Status: 201 Created
    Connection: close
    Content-Type: application/json
    

## Get list of Students again

### Request

`GET /student/`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:31 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
   
## Change a Student state

### Request

`PUT /student/:id`

    curl -i -H 'Accept: application/json' -X PUT http://localhost:9192/student

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:31 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
   
## Get changed Student

### Request

`GET /student/id`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/1

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:31 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
   

## Change a Student

### Request

`PUT /student/:id`

    curl -i -H 'Accept: application/json' -X PUT -d 'name=Foo&status=changed2' http://localhost:9192/student/1

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:31 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    
## Attempt to change a Student using partial params

### Request

`PUT /student/:id`

    curl -i -H 'Accept: application/json' -X PUT -d 'status=changed3' http://localhost:9192/student/1

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    
## Attempt to change a Student using invalid params

### Request

`PUT /student/:id`

    curl -i -H 'Accept: application/json' -X PUT -d 'id=99&status=changed4' http://localhost:9192/student/1

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
   

## Change a Student using the _method hack

### Request

`POST /student/:id?_method=POST`

    curl -i -H 'Accept: application/json' -X POST -d 'name=Baz&_method=PUT' http://localhost:9192/student/1

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
   

## Change a Student using the _method hack in the url

### Request

`POST /student/:id?_method=POST`

    curl -i -H 'Accept: application/json' -X POST -d 'name=Qux' http://localhost:9192/student/1?_method=PUT

### Response

    HTTP/1.1 404 Not Found
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: text/html;charset=utf-8

## Delete a Student

### Request

`DELETE /student/id`

    curl -i -H 'Accept: application/json' -X DELETE http://localhost:9192/student/1/

### Response

    HTTP/1.1 204 No Content
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 204 No Content
    Connection: close


## Try to delete same Student again

### Request

`DELETE /student/id`

    curl -i -H 'Accept: application/json' -X DELETE http://localhost:9192/student/1/

### Response

    HTTP/1.1 404 Not Found
    Date: Thu, 24 Feb 2011 12:36:32 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
   

## Get deleted Student

### Request

`GET /student/1`

    curl -i -H 'Accept: application/json' http://localhost:9192/student/1

### Response

    HTTP/1.1 404 Not Found
    Date: Thu, 24 Feb 2011 12:36:33 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
    

## Delete a Student using the _method hack

### Request

`DELETE /student/id`

    curl -i -H 'Accept: application/json' -X POST -d'_method=DELETE' http://localhost:9192/student/2/

### Response

    HTTP/1.1 204 No Content
    Date: Thu, 24 Feb 2011 12:36:33 GMT
    Status: 204 No Content
    Connection: close


