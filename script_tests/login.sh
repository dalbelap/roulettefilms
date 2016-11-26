USER=user
PASS=user

curl -X POST http://localhost:8080/api/authenticate -H "Content-Type: application/json;charset=UTF-8" -H "Accept: application/json, text/plain, */*" -d "{
  \"password\": \"$USER\",
  \"rememberMe\": true,
  \"username\": \"$USER\"
}"
