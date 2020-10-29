var express = require('express');

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var router = express.Router();

var db_cafeteria = require('../public/SQL/cafeteria_sql')();

router.post('/get/list', function(req,res,next){
<<<<<<< HEAD
    var univ_name = req.body.univ_name
=======
    var univ_name = req.body[0].univ_name
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958

    var koreanFood = new Array()
    var chineseFood = new Array()
    var westernFood = new Array()
    var japaneseFood = new Array()
    var chickenFood = new Array()

	db_cafeteria.get_cafeteria_list(univ_name, function(err, result) {
        if(err) console.log(err)
        else {
            for(i in result) {
                switch(result[i].cafe_type) {
                    case "한식" :
                        koreanFood.push(result[i]);
                        break;
                    case "중식" :
                        chineseFood.push(result[i]);
                        break;
                    case "양식" :
                        westernFood.push(result[i]);
                        break;
                    case "일식" :
                        japaneseFood.push(result[i]);
                        break;
                    case "치킨" :
                        chickenFood.push(result[i])
                        break;
                }
            }
<<<<<<< HEAD
	    var object = new Object()
=======

            var object = new Object()
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
            object.koreanFood = koreanFood
            object.japaneseFood = japaneseFood
            object.chineseFood = chineseFood
            object.westernFood = westernFood
            object.chickenFood = chickenFood
            res.send(object)
<<<<<<< HEAD
		console.log(object)
=======
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
        }
    })
})

<<<<<<< HEAD
=======

>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
router.post('/get/inform', function(req, res, next) {
	var cafe_no = req.body.cafe_no

	db_cafeteria.get_cafeteria_inform(cafe_no, function(err, inform) {
		if(err) console.log(err)
		else {
			db_cafeteria.get_cafeteria_menu(cafe_no, function(err, menu) {
				if(err) console.log(err)
				else {
					db_cafeteria.get_cafeteria_review(cafe_no, function(err, review) {
						if(err) console.log(err)
						else {
							var object = new Object()
<<<<<<< HEAD
							object.inform = inform[0]
=======
							object.inform = inform
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
							object.menu = menu
							object.review = review
							res.send(object)
						}
					})
				}
			})
		}
	})
})

<<<<<<< HEAD
=======



router.post('/insert', function(req,res,next){
	var nickname=req.body.nickname
	var date=moment().format('YYYY-MM-DD HH:mm:ss')
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name
	var point=req.body.point
	var content=req.body.content

	db_review.insert_review(nickname,date,cafe_name,univ_name,point,content,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/update', function(req,res,next){	
	var nickname=req.body.nickname
	var date=req.body.date
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name
	var point=req.body.point
	var content=req.body.content

	db_review.update_review(nickname,date,cafe_name,univ_name,point,content,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/delete', function(req,res,next){
	var nickname=req.body.nickname
	var date=req.body.date
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.cafe_name

	db_review.delete_review(nickname,date,cafe_name,univ_name,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/get_my_review', function(req,res,next){
	var nickname=req.body[0].nickname
	
	db_review.get_my_review(nickname,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
})

router.post('/get_average', function(req,res,next){
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name

	db_review.get_average_review(cafe_name, univ_name, function(err, result){
		if(err) console.log(err)
		else{
			var object= new Object()
			object.average=result[0].avg
			res.send(object)
		}
	})
})

>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
module.exports = router;
