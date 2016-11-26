if [ $# -ne 1 ];
then
	echo "usage: $0 token_id"
	exit -1;
fi

TOKEN=$1

curl -i -X GET http://localhost:8080/api/account -H "Content-Type:application/json; charset=UTF-8" -H "Authorization: Bearer $TOKEN"
