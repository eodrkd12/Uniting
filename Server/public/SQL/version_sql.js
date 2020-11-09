var pool = require('../../config/db_config');

module.exports = function () {
    return {
		check_version: function(callback) {
			pool.getConnection(function(err, con) {
                var sql = `SELECT * FROM version`
				con.query(sql, function(err, result) {
					if(err) callback(err)
					else callback(null, result)
				})
			})
		},
        pool: pool
    }
};
