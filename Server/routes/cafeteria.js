var express = require('express');

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var router = express.Router();

var db_cafeteria = require('../public/SQL/cafeteria_sql')();

router.post('/get/list', function(req,res,next){
    var univ_name = req.body.univ_name

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
						if(koreanFood.length != 10) {
							koreanFood.push(result[i]);
						}
                        break;
                    case "중식" :
						if(chineseFood.length != 10) {
							chineseFood.push(result[i]);
						}
                        break;
                    case "양식" :
						if(westernFood.length != 10) {
							westernFood.push(result[i]);
						}
                        break;
                    case "일식" :
						if(japaneseFood.length != 10) {
							japaneseFood.push(result[i]);
						}
                        break;
                    case "패스트푸드" :
						if(fastFood.length != 10) {
							fastFood.push(result[i])
						}
                        break;
                }
            }

            var object = new Object()
            object.koreanFood = koreanFood
            object.japaneseFood = japaneseFood
            object.chineseFood = chineseFood
            object.westernFood = westernFood
            object.fastFood = fastFood
            res.send(object)
        }
    })
})


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
							object.inform = inform[0]
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

module.exports = router;
