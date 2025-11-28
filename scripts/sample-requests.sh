#!/bin/bash
# create
curl -s -X POST -H "Content-Type: application/json" -d '{"item":"Laptop","quantity":1,"price":1200}' http://localhost:8080/orders | jq
# list
curl -s http://localhost:8080/orders | jq
