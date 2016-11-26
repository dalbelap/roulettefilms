if [ $# -ne 1 ];
then
	echo "usage: $0 token_id"
	exit -1;
fi

TOKEN=$1

curl -i http://localhost:8080/api/genres -H "Authorization: Bearer $TOKEN"

