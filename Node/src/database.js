const mongoose = require('mongoose');
const url = "mongodb://localhost:27017/booksDB";
const User = require('./models/user.js').User;
const Book = require('./models/book.js').Book;

async function connect() {

    await mongoose.connect(url, {
        useUnifiedTopology: true,
        useNewUrlParser: true,
        useFindAndModify: false
    });

    console.log("Connected to Mongo");

    await init();
}

async function disconnect() {
    await mongoose.connection.close();
    console.log("Disconnected from MongoDB")
}

async function init() {

    console.log('Initializing database');

    console.log('Populating database with users');

    await User.deleteMany({});

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed"),
        nick: "jua_ma",
        email: "user1@email.es",
        password: "$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP."
    }).save();

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bef"),
        nick: "jua_ma2",
        email: "user2@email.es",
        password: "$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP."
    }).save();

    console.log('Populating database with books');

    await Book.deleteMany({});

    await new Book({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bf0"),
        title: "Book 1 title",
        resume: "Book 1 resume",
        author: "Book 1 author",
        editorial: "Book 1 editorial",
        publicationYear: 1992
    }).save();

    const book2 = await new Book({
        _id: new mongoose.Types.ObjectId("5fda350d3749aa4832165b84"),
        title: "Book 2 title",
        resume: "Book 2 resume",
        author: "Book 2 author",
        editorial: "Book 2 editorial",
        publicationYear: 2006
    }).save();

    console.log('Populating database with comments');

    book2.comments.push({
        _id: new mongoose.Types.ObjectId("5fdb4812df5c2555a401b6da"),
        content: "Book 2 comment 1 from user 1",
        rating: 2.6,
        user: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    });
    book2.comments.push({
        _id: new mongoose.Types.ObjectId("5fdb4812df5c2555a401b6db"),
        content: "Book 2 comment 2 from user 1",
        rating: 4,
        user: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    });

    await book2.save();

    console.log('Database initialized');
}

module.exports = { connect, disconnect }