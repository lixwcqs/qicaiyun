- create mongo service by docker
```
#docker run --name mongo -v /data/mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=qicaiyun -e MONGO_INITDB_ROOT_PASSWORD=1111  -d mongo
docker run --name mongo -v /data/mongo:/data/db   -d mongo
```
- connect mongo  
```
docker run -it --link mongo:mongo --rm mongo mongo  --host mongo qicaiyun 
```

- create database
```
use qicaiyun

```

- create user
```
db.createUser(
  { 
    user:"dev",
    pwd:"1111",
    roles:[{role:"readWrite",db:"qicaiyun"}]
  }
)
```

3.user info
```
show users
```

