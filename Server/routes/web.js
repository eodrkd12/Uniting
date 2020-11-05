var express = require('express');
var router = express.Router();

router.get('/',function(res, res){
	console.log("asd")
	res.render('box.html')
})


module.exports = router;
