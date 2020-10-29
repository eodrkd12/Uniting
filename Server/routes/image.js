var express = require('express');
var router = express.Router();
var multer = require('multer');
var fs = require('fs');
var path = require("path");
var db_image = require('../public/SQL/image_sql')()

var upload = multer({storage: multer.diskStorage({
	destination: function(req, file, cb) {
		cb(null, 'uploads/')
	},
	filename: function(req, file, cb) {
		cb(null, new Date().valueOf() +  path.extname(file.originalname));
	}
}),
});

router.post('/review/insert', upload.single('img'), (req, res) => {

<<<<<<< HEAD
    var user_id = req.body.user_id.replace(/"/gi, '');
    var user_nickname = req.body.user_nickname.replace(/"/gi, '');
    var cafe_no = req.body.cafe_no.replace(/"/gi, '');
    var review_content = req.body.review_content.replace(/"/gi, '');
    var review_date = req.body.review_date.replace(/"/gi, '');
    var review_point = req.body.review_point
    var image = "http://52.78.27.41:1901/" + req.file.filename

	console.log(user_id + user_nickname + cafe_no);

    db_image.insert_review(user_id, user_nickname, cafe_no, review_content, review_date, review_point, image, function (err, result) {
        if (err) console.log(err)
        else {
            console.log(req.file.filename + user_id)
		console.log(req.file)
=======
    var user_id = req.body.user_id
    var user_nickname = req.body.user_nickname
    var cafe_no = req.body.cafe_no
    var review_content = req.body.review_content
    var review_date = req.body.review_date
    var review_point = req.body.review_point
    var image = "http://52.78.27.41:1901" + req.file.filename

   
    db_image.insert_review(user_id, user_nickname, cafe_name, review_content, review_date, review_point, image, function (err, result) {
        if (err) console.log(err)
        else {
            console.log(req.file.filename + user_id)
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
            var object = new Object();
            object.result = "success"
            res.send(object)
        }
    })
})

router.post('/review/insert/noimage', function(req, res) {

    var user_id = req.body.user_id
    var user_nickname = req.body.user_nickname
    var cafe_no = req.body.cafe_no
    var review_content = req.body.review_content
    var review_date = req.body.review_date
    var review_point = req.body.review_point

   
    db_image.insert_review(user_id, user_nickname, cafe_no, review_content, review_date, review_point, null, function (err, result) {
        if (err) console.log(err)
        else {
            var object = new Object();
            object.result = "success"
            res.send(object)
        }
    })
})

router.post('/review/delete', function(req, res) {
    var review_no = req.body.review_no
    var image_path = req.body.image_path

    if(image_path == "noimage") {
        db_image.delete_review(review_no, function(err, result) {
            if(err) console.log(err)
            else {
                var object = new Object();
                object.result = "success"
                res.send(object)
            }
        })
    }
    else {
        db_image.delete_review(review_no, function(err, result) {
            if(err) console.log(err)
            else {
                fs.unlink("uploads/"+image_path, (err) => {
                    if(err) console.log(err)
                    else {
                      var object = new Object()
                      object.result = "success"
                      res.send(object)
                    }
                })
            }
        })
    }
})

<<<<<<< HEAD
module.exports = router;
=======















router.post('/upload/story', upload.single('img'), (req, res) => {

    var user_id = req.body.user_id
    var image = "http://18.217.130.157:3000/" + req.file.filename
    var image_content = req.body.image_content
    var image_date = req.body.image_date

    db_image.insert_image(user_id, image, "story", image_content, image_date, function(err, result) {
            if(err) console.log(err)
            else {
                    console.log(req.file.filename + user_id)
                    res.send(req.file)
            }
    })
})

router.post('/delete', function(req, res, next) {
  var image_id = req.body.image_id
  var delete_image = req.body.delete_image
  db_image.delete_image(image_id, function(err, result) {
      if(err) console.log(err)
      else {
        fs.unlink("uploads/"+delete_image, (err) => {
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

router.post('/get/profile', function(req, res, next) {
    var user_id = req.body[0].user_id


    db_image.get_profile_image(user_id, function(err, result) {
        if(err) console.log(err)
        else{
		console.log(`======${user_id}======`)
		console.log(result)
		res.send(result)
	}
    })
})

router.post('/get/story', function(req, res, next) {
    var user_id = req.body[0].user_id
    var image_usage = req.body[0].image_usage
    var user_gender = req.body[0].user_gender

    db_image.get_story_image(user_id, image_usage, user_gender, function(err, result) {
        if(err) console.log(err)
        else {
		console.log(user_id)
		console.log(result)
		res.send(result)
        }
    })
})

router.post('/get/my/story', function(req, res, next) {
    var user_id = req.body.user_id

    db_image.get_my_story_image(user_id, function(err, result) {
        if(err) console.log(err)
        else res.send(result[0])
    })
})

router.post('/get/story/user', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id

    db_image.increase_view(image_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.get_story_user(image_id, function(err, user) {
                if(err) console.log(err)
                else {
                    db_image.check_expression_data(user_id, image_id, function(err, check) {
                        if(err) console.log(err)
                        else {
                            if(check[0].count == 1) {
                                user[0]["like"] = 1;
                                res.send(user[0]);
                                console.log(user[0]);
                            }
                            else if(check[0].count == 0) {
                                user[0]["like"] = 0;
                                res.send(user[0]);
                                console.log(user[0]);
                            }
                        }
                    })
                }
            })
        }
    })
})

router.post('/insert/expression', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id
    var expression_date = req.body.expression_date

    db_image.insert_expression_data(user_id, image_id, expression_date, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.increase_like(image_id, function(err, result) {
                if(err) console.log(err)
                else {
                    console.log("스토리 데이터 삽입완료");
		    var object = new Object()
		    object.result = "success"
		    res.send(object)
                }
            })
        }
    })
})

router.post('/delete/expression', function(req, res, next) {
    var user_id = req.body.user_id
    var image_id = req.body.image_id

    db_image.delete_expression_data(user_id, image_id, function(err, result) {
        if(err) console.log(err)
        else {
            db_image.decrease_like(image_id, function(err, result) {
                if(err) console.log(err)
                else {
                    console.log("스토리 데이터 제거완료");
		    var object = new Object()
			object.result="success"
			res.send(object);
                }
            })
        }
    })
})


module.exports = router;

>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
