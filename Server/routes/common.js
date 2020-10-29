var express = require('express');
var router = express.Router();
<<<<<<< HEAD
var admin = require('firebase-admin');

var pool = require('../config/db_config');

var serviceAccount = require("../uniting-bad5d-firebase-adminsdk-twdjo-c79176a642.json")

admin.initializeApp({
	credential: admin.credential.cert(serviceAccount),
	databaseURL: "https://uniting-bad5d.firebaseio.com"
})

router.post('/sql/insert', function(req, res) {
	var sql = req.body.sql

	console.log(sql)

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

	console.log(sql)

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
=======

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
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
})

router.post('/sql/select/single', function(req, res) {
	var sql = req.body.sql

<<<<<<< HEAD
	console.log(sql)

=======
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
	pool.getConnection(function(err, con) {
		con.query(sql, function(err, result) {
			con.release()
			if(err) console.log(err)
			else {
<<<<<<< HEAD
				console.log(result[0])
=======
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
				res.send(result[0])
			}
		})
	})
})

<<<<<<< HEAD
router.post('/fcm', function(req,res){
	var topic = req.body.topic
	var content = req.body.content
	var title = req.body.title

	var message = {
		notification: {
			body: content,
			title: title
		},
		topic: topic
	}
	admin.messaging().send(message)
		.then((response) => {
			console.log("Successfully sent message: ", response)
		})
		.catch((error) => {
			console.log("Error sending message: ", error)
		})

	var object = new Object();
        object.result = "success"
        res.send(object)
})

router.post('/subscribe/fcm', function(req,res){
	var roomId = req.body.room_id
	var userId = req.body.user_id

	var sql = `SELECT token FROM user WHERE user_id = '${userId}'`

	pool.getConnection(function(err,con){
		con.query(sql, function(err, result){
			con.release()
			if(err) console.log(err)
			else {
				admin.messaging().subscribeToTopic(result, roomId)
					.then(function(response){
						console.log("Succesfully subscribe topic", response)
						var message = {
							notification: {
								body: conetnt,
								title: title
							},
							topic: topic
						}
						admin.messaging().send(message)
							.then((response) => {
								console.log("Successfully sent message", response)

								var obj = new Object()
								object.result = "success"
								res.send(object)
							})
							.catch((error) => {
								console.log("Error sending message", error)
							})
					})
					.catch(function(error){
						console.log("Error subscribe topic", error)
					})
			}
		})
	})
	
})

module.exports = router;
=======
module.exports = router;
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
