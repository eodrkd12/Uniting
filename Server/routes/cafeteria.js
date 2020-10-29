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
	    var object = new Object()
            object.koreanFood = koreanFood
            object.japaneseFood = japaneseFood
            object.chineseFood = chineseFood
            object.westernFood = westernFood
            object.chickenFood = chickenFood
            res.send(object)
		console.log(object)
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

module.exports = router;
