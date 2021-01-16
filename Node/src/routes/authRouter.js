const express = require('express');
const router = express.Router();
const { User } = require('../models/user.js');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const config = require('../../config');

const INVALID_CREDENTIALS = {"error": "Invalid credentials"};

router.post('/', async (req, res) => {
  const user = await User.findOne({nick: req.body.nick});

  if (!user) {
    return res.status(404).send(INVALID_CREDENTIALS);
  }

  const passwordIsValid = bcrypt.compareSync(req.body.password, user.password);
  if (!passwordIsValid) {
    return res.status(401).send({auth: false, token: null});
  }

  const token = jwt.sign({id: user._id}, config.secret, {
    expiresIn: 86400 // expires in 24 hours
  });

  res.status(200).send({auth: true, token: token});

});

module.exports = router;