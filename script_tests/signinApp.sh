if [ $# -ne 3 ];
then
	echo "usage: $0 tokenId langKey providerId"
	exit -1;
fi

curl -i -X POST --header "Content-Type: application/json" \
 --header "Accept: application/json" \
 -d "{ \
  \"tokenId\": \"$1\",
  \"langKey\": \"$2\",
  \"providerId\": \"$3\"
}" "http://127.0.0.1:8080/social/signin-app"

