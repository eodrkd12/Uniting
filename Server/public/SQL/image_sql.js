var pool=require('../../config/db_config');
  
module.exports=function(){
    return {
	    insert_review : function(user_id, user_nickname, cafe_no, review_content, review_date, review_point, image_url, callback){
		    pool.getConnection(function(err,con){
<<<<<<< HEAD
				var sql = `insert into review(user_id, user_nickname, cafe_no, review_content, review_date, review_point, image_url) values('${user_id}','${user_nickname}', ${cafe_no}, '${review_content}', '${review_date}', ${review_point}, '${image_url}')`
=======
				var sql = `insert into review(user_id, user_nickname, cafe_no, review_content, review_date, review_point, image_url) values('${user_id}','${user_nickname}', '${cafe_no}', '${review_content}', '${review_date}', ${review_point}, '${image_url}')`
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
				con.query(sql,function(err,result,fields){
					con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
<<<<<<< HEAD
	    delete_review : function(review_no, callback) {
		    pool.getConnection(function(err, con) {
			    var sql = `delete from review where review_no=${review_no}`
			    con.query(sql, function(err, result, fields) {
				    con.release()
				    if(err) callback(err)
				    else callback(null, result)
			    })
		    })
	    },
	    pool : pool
    }
}

=======
	    pool : pool
    }
}
>>>>>>> 6ab14aa1f96c106934d5cc84eff6bf532a8e1958
