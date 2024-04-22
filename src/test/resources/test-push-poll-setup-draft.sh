#!/bin/sh

ACCESS_TOKEN='PLACEHOLDER_TOKEN'

response=$(curl --location -g --request POST 'http://localhost:9607/application' \
--header 'Authorization: Bearer $ACCESS_TOKEN' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "TEST APP",
    "access": {
        "accessType": "STANDARD",
        "redirectUris": [],
        "overrides": []
    },
    "environment": "SANDBOX",
    "collaborators": [
        {
            "emailAddress": "test@test.com",
            "role": "ADMINISTRATOR",
            "userId": "61e93581-5028-4475-912b-e8df6f406e2f"
        }
    ]
}')

clientId=$(echo $response | egrep -o '"clientId":\s?.*?".*?"' | egrep -o '[^"clientId":][^"].*[^"]')

echo "Client Id is: $clientId"
export CLIENT_ID=$clientId

echo "##################################################################"
echo "Creating PPNS box..."
echo "##################################################################"

boxIdResponse=$(curl --location --request PUT 'http://localhost:6701/box' \
--header 'User-Agent: api-subscription-fields' \
--header 'Authorization: Bearer $ACCESS_TOKEN' \
--header 'Content-Type: application/json' \
--data '{ "boxName":"customs/excise##1.0##notificationUrl", "clientId":'\"$clientId\"'}')

echo "BoxId: $boxIdResponse"
