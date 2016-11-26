CLIENT=coinceltapp
SECRET=mySecretOAuthSecret
USER=user
PASS=user

curl -X POST -vu $CLIENT:$SECRET http://localhost:8080/oauth/token -H "Accept: application/json" -d "username=$USER&password=$PASS&grant_type=password&scope=read%20write&client_id=$CLIENT&client_secret=$SECRET"
