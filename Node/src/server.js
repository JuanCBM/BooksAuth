const express = require('express');
const database = require('./database.js');
const booksRouter = require('./routes/bookRouter.js');
const usersRouter = require('./routes/userRouter.js');
const authRouter = require('./routes/authRouter.js');

const app = express();
const https = require('https');
const fs = require('fs');
const path = require("path");

//Convert json bodies to JavaScript object
app.use(express.json());
app.use('/api/v1/books', booksRouter);
app.use('/api/v1/users', usersRouter);
app.use('/api/v1/login', authRouter);

async function main() {

    await database.connect();

    https.createServer({
        key: fs.readFileSync(path.resolve(__dirname, "../server.key")),
        cert: fs.readFileSync(path.resolve(__dirname, "../server.cert"))
    }, app).listen(3000, () => {
        console.log('Server listening on port 3000!');
    });

    process.on('SIGINT', () => {
        database.disconnect();
        console.log('Process terminated');
        process.exit(0);
    });
}

main();