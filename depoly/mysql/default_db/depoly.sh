#! /bin/bash

CURRENT_HOME=`pwd`
INIT_SQL=${CURRENT_HOME}/init.sql
SQL_FILES=${CURRENT_HOME}/sql/*.sql

DB_IP=127.0.0.1
DB_PORT=3306
DB_USERNAME=sps
DB_PASSWD=sps
DB_NAME=default_db

#export PGPASSWORD=sps

DATE=`date +%Y-%m-%d`

ImportSQL()
{
    for FILE in ${SQL_FILES}; do
        echo "==> import sql file : ${FILE}"
        #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
        psql -f ${FILE} "host=${DB_IP} port=${DB_PORT} user=${DB_USERNAME} password=${DB_PASSWD} dbname=${DB_NAME}" | grep "ERROR" | tee -a /tmp/${DATE}.log
    done
}


#start
echo "====> start import sql..."
if [ -n "${DB_USERNAME}" ]; then
    echo -e "\n ==> create user..."
    mysql -uroot -e "CREATE USER '${DB_USERNAME}'@'%' IDENTIFIED BY '$DB_PASSWD'"
	mysql -uroot -e "GRANT ALL PRIVILEGES ON *.* TO '${DB_USERNAME}'@'%' WITH GRANT OPTION"


    echo -e "\n ==> import business sql..."
    if [ -n "${SQL_FILES}" ]; then
        ImportSQL
    fi
else
    echo -e "init sql not exists!! \n\n"
fi
echo -e "import sql completed...\n\n"
