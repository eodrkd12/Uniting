var express = require('express');
var router = express.Router();
var admin = require('firebase-admin');

var pool = require('../config/db_config');

router.post('/delete', function(req, res) {
        var roomId = req.body.room_id
	var userId = req.body.user_id

	var sql1 = `DELETE FROM room WHERE room_id = '${roomId}'`
	var sql2 = `DELETE FROM joined WHERE room_id = '${roomId}' AND user_id = '${userId}'`

        pool.getConnection(function(err, con) {
                con.query(sql1, function(err, result) {
                        con.release();
                })
        })

	pool.getConnection(function(err, con){
		con.query(sql2, function(err,result){
			con.release();
			if(err){
				console.log(err)
			}
			else{
				var obj = new Object()
				obj.result = "success"
				res.send(obj)
			}
		})
	})
})

router.post('/exit', function(req, res){
	var roomId = req.body.room_id
	var userId = req.body.user_id

	var sql = `DELETE FROM joined WHERE room_id = '${roomId}' AND user_id = '${userId}'`

	pool.getConnection(function(err, con){
		con.query(sql, function(err,result){
			con.release()
			if(err){
				console.log(err)
			}
			else{
				var ob = new Object()
				obj.result = "success"
				res.send(obj)
			}
		})
	})
})

module.exports = router
