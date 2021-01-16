const { spawnSync } = require('child_process');

function exec(serviceName, command){

  console.log(`Starting docker image for [${serviceName}]`);
  console.log(`Command: ${command}`);

  spawnSync(command, [], {

    shell: true,
    stdio: 'inherit'
  });
}

exec('MySQL', 'docker run -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -e -d mysql:latest');
