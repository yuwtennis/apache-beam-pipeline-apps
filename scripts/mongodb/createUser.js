db = db.getSiblingDB('YOURDB') ;
// clusterManager required to use splitVector
// https://github.com/apache/beam/blob/master/sdks/java/io/mongodb/src/main/java/org/apache/beam/sdk/io/mongodb/MongoDbIO.java#L484
db.createUser(
  {
    user: "YOURUSER",
    pwd:  passwordPrompt(),
    roles: [ { role: "readWrite", db: "YOURDB" }, {role: "clusterManager", db: "admin"}]
  }
) ;