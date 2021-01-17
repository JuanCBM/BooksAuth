const { spawnSync } = require('child_process');
function exec(serviceName, command){
  console.log(`Starting docker image for [${serviceName}]`);
  console.log(`Command: ${command}`);

  spawnSync(command, [], {
    shell: true,
    stdio: 'inherit'
  });
}


exec('MongoDBStop', 'docker stop mongo-db');
exec('MySQLStop', 'docker stop mysql-db');
exec('MongoDBRemove', 'docker rm mongo-db');
exec('MySQLRemove', 'docker rm mysql-db');
exec('MongoDB', 'docker run -d -p 27017:27017 --name mongo-db mongo:latest');
exec('MySQL', 'docker run -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -e -d mysql:latest');
