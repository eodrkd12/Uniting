var pool = require('../../config/db_config');

module.exports = function () {
    return {
		login: function(user_id, callback) {
			pool.getConnection(function(err, con) {
				var sql = `SELECT * FROM blacklist right outer join user ON user.user_id = blacklist.user_id WHERE user.user_id = '${user_id}';`
				con.query(sql, function(err, result) {
					if(err) callback(err)
					else callback(null, result)
				})
			})
		},
		update_recent_time: function(user_id, curDate) {
			pool.getConnection(function(err, con) {
				var sql = `UPDATE user SET user_recent_time = '${curDate}' WHERE user_id = '${user_id}'`
				con.query(sql)
			})
		},
        pool: pool
    }
};
