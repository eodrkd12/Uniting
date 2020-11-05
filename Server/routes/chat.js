var express=require('express');
var router=express.Router()

var pool = require('../config/db_config')

router.post('/sql',function(req,res,next){
	var sql=req.body.sql

	pool.getConnection(function(err,con){
		con.query(sql,function(err,result){
			con.release()
			if(err) console.log(err)
			else {
				var obj=new Object()
				obj.result="success"
				console.log(obj)
				res.send(obj)
			}
		})
	})
})

module.exports=router
