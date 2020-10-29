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

module.exports = router;
