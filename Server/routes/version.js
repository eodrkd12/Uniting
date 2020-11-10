var express = require('express');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')();

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

router.post('/login', function(req, res, next) {
	var user_id = req.body.user_id
	var user_pw = req.body.user_pw

	
})

module.exports = router;
