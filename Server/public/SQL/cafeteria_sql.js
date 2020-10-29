var pool=require('../../config/db_config');
  
module.exports=function(){
    return {
	    get_cafeteria_list : function(univ_name, callback) {
		    pool.getConnection(function(err, con) {
			    var sql = `SELECT cafeteria.cafe_no, cafeteria.cafe_name, avg(review_point) as star_point, cafe_type, cafe_thumbnail FROM review right outer join cafeteria ON cafeteria.cafe_no = review.cafe_no WHERE cafeteria.univ_name ='${univ_name}' GROUP BY cafeteria.cafe_name;`
			    con.query(sql, function(err, result, fields) {
				    con.release()
				    if(err) callback(err)
				    else callback(null, result)
			    })
		    })
	    },
	    get_cafeteria_inform : function(cafe_no, callback) {
		    pool.getConnection(function(err, con) {
			    var sql = `SELECT cafe_name, cafe_address, cafe_phone, cafe_bizhour, cafe_mapx, cafe_mapy FROM cafeteria WHERE cafe_no = ${cafe_no}`
			    con.query(sql, function(err, result, fields) {
				    con.release()
				    if(err) callback(err)
				    else callback(null, result)
			    })
		    })
	    },
	    get_cafeteria_review : function(cafe_no, callback) {
		    pool.getConnection(function(err, con) {
			    var sql = `SELECT * FROM review WHERE cafe_no = ${cafe_no} ORDER BY review_date desc`
			    con.query(sql, function(err, result, fields) {
				    con.release()
				    if(err) callback(err)
				    else callback(null, result)
			    })
		    })
	    },
	    get_cafeteria_menu : function(cafe_no, callback) {
		    pool.getConnection(function(err, con) {
			    var sql = `SELECT menu_title, menu_price FROM menu WHERE cafe_no = ${cafe_no}`
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

