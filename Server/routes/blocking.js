var express=require('express');
var router=express.Router();

var db_blocking = require('../public/SQL/blocking_sql')();


router.post('/insert',function(req,res,next){
    var user_id = req.body.user_id
    var dept_name = req.body.dept_name
    var blocking_date = req.body.blocking_date
    var update_value = req.body.update_value


    db_blocking.insert_blocking(user_id, dept_name, blocking_date, function(err, result) {
        if(err) console.log(err)
        else {
            db_blocking.update_blocking(user_id, update_value, function(err, result) {
                if(err) console.log(err)
                else {
                    var object = new Object()
                    object.result = "success"
                    res.send(object)
                }
            })
        }
    })
})

router.post('/delete',function(req,res,next){
    var user_id = req.body.user_id
    var update_value = req.body.update_value

    db_blocking.delete_blocking(user_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_blocking.update_blocking(user_id, update_value, function(err, result) {
                if(err) console.log(err)
                else {
                    var object = new Object()
                    object.result = "success"
                    res.send(object)
                }
            })
        }
    })
})

module.exports=router;
