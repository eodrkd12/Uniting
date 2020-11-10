var express = require('express');
var router = express.Router();

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var db_user = require('../public/SQL/user_sql')();

router.post('/login', function(req, res, next) {
        var curDate = moment().format('YYYY-MM-DD');

        var user_id = req.body.user_id
        var user_pw = req.body.user_pw

        db_user.login(user_id, function(err, result) {
                if(err) console.log(err)
                else {
                        if(result.length == 0) {
                                var object = new Object();
                                object.result = "fail";
                                res.send(object)
                                console.log(object)
                        } else {
                                if(result[0]["user_pw"] == user_pw) {
                                        if(result[0]["blacklist_period"] == null) {
                                                result[0]["result"] = "success"
                                                res.send(result[0])
                                                db_user.update_recent_time(user_id, curDate)
                                                console.log(result[0])
                                        } else {
                                                result[0]["result"] = "blacklist"
                                                res.send(result[0])
                                        }
                                } else {
                                        result[0]["result"] = "fail";
                                        res.send(result[0])
                                        console.log(result[0]);
                                }
                        }
                }
        })
})

module.exports = router;