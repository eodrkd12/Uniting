var express = require('express');
var router = express.Router();

var express = require('express');
var router = express.Router();

var pool = require('../config/db_config');

router.post('/sql/insert', function(req, res) {
        var sql = req.body.sql

        pool.getConnection(function(err, con) {
                con.query(sql, function(err, result) {
                        con.release();
                        if(err) console.log(err)
                        else {
                                var object = new Object();
                                object.result = "success"
                                res.send(object)
                        }
                })
        })
})

router.post('/sql/select', function(req,res){
        var sql = req.body.sql

        pool.getConnection(function(err, con){
                con.query(sql,function(err,result){
                        con.release()
                        if(err) console.log(err)
                        else {
                                res.send(result)
                                console.log(result)
                        }
                })
        })
})

router.post('/sql/select/single', function(req, res) {
	var sql = req.body.sql

	pool.getConnection(function(err, con) {
		con.query(sql, function(err, result) {
			con.release()
			if(err) console.log(err)
			else {
				res.send(result[0])
			}
		})
	})
})

module.exports = router;