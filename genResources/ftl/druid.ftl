driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/${databaseName}?useSSL=false&serverTimezone=UTC&user=root&password=&useUnicode=true&characterEncoding=UTF8&autoReconnect=true&failOverReadOnly=false
username=${username}
password=${password}


filters=stat
initialSize=2
maxActive=300
maxWait=60000
timeBetweenEvictionRunsMillis=60000
minEvictableIdleTimeMillis=300000
validationQuery=SELECT 1
testWhileIdle=true
testOnBorrow=false
testOnReturn=false
poolPreparedStatements=false
maxPoolPreparedStatementPerConnectionSize=200