#!/bin/zsh

curl \
-X POST \
--location "http://localhost:8080/knowledge" \
-H "Content-Type: application/json" \
-d "{ \"id\": 3000, \"name\": \"Уно\"}"

curl \
-X POST \
--location "http://localhost:8080/knowledge" \
-H "Content-Type: application/json" \
-d "{ \"id\": 3001, \"name\": \"Дуо\"}"


'# проверка вставки в _знание_' |
curl \
-X POST \
--location "http://localhost:8080/knowledge/3001" \
-H "Content-Type: application/json" \
-d "{ \"id\": 3002, \"name\": \"Дуо 2 with parent\"}"
