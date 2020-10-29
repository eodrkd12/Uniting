var pool=require('../../config/db_config');
  
module.exports=function(){
    return {
	    insert_blocking : function(user_id, dept_name, blocking_date, callback){
		    pool.getConnection(function(err,con){
                var sql = `insert into blocking values('${user_id}', '${dept_name}', '${blocking_date}')`
				con.query(sql,function(err,result,fields){
					con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
		},
		delete_blocking : function(user_id, callback) {
			pool.getConnection(function(err, con) {
                var sql = `delete from blocking where user_id='${user_id}'`
				con.query(sql, function(err, result, fields) {
					con.release()
					if(err) callback(err)
					else callback(null, result)
				})
			})
        },
        update_blocking : function(user_id, update_value, callback) {
            pool.getConnection(function(err, con) {
                var sql = `update user set blocking_dept = ${update_value} where user_id = '${user_id}'`
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

