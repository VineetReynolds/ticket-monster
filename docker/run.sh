docker run --name mysqldb -e MYSQL_ROOT_PASSWORD=mysecretpassword -d mysql
docker run -it --link mysqldb:db jbossdeveloper/ticketmonster