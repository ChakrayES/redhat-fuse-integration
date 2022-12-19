
# Red Hat Fuse Micro Integration

A basic example with Red Hat Fuse to create a micro integration

## Configuration

- Fuse Boom Version: 7.6.0.fuse-sb2-760028-redhat-00001

## Installation

```
mvn clean package -Pfuse7-generate-imagen
docker run -p 8080:8080 chakray/redhat-fuse-integration:1.0.0
```

## Test

```
curl --location --request POST 'http://server:8080/fuseIntegration/instruction' \
--header 'Content-Type: application/json' \
--data-raw '{
    "serviceId": 1001,
    "description": "Information",
    "date": null,
    "destination": "Salesforce",
    "information":{
        "serial_Number__c" : "1000", 
        "printer_Model__c" : "XZO-5"
      }
}'
```

## Links

