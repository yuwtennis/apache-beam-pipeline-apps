db = db.getSiblingDB('YOURDB')
db.createUser(
  {
    user: "YOURUSER",
    pwd:  passwordPrompt(),
    roles: [ { role: "readWrite", db: "YOURDB" }, {role: "clusterManager", db: "admin"}]
  }
)