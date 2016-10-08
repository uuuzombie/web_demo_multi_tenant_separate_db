#! /bin/bash

CURRENT_HOME=`pwd`
INIT_SQL=${CURRENT_HOME}/init.sql
SQL_FILES=${CURRENT_HOME}/sql/*.sql

DB_IP=127.0.0.1
DB_PORT=5432
DB_USERNAME=sps
DB_PASSWD=sps
DB_NAME=$1

#export PGPASSWORD=dbuser

DATE=`date +%Y-%m-%d`

ImportSQL()
{
    for FILE in ${SQL_FILES}; do
        echo "==> import sql : ${FILE}"
        #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
        psql -f ${FILE} "host=${DB_IP} port=${DB_PORT} user=${DB_USERNAME} password=${DB_PASSWD}" | grep "ERROR" | tee -a /tmp/$DATE.log
    done
}


#start
echo "====> start import sql"
if [ -n "${INIT_SQL}" ]; then
    echo "==> import init sql"
    #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
    sudo -u postgres psql -f ${INIT_SQL}  | grep "ERROR" | tee -a /tmp/$DATE.log
fi


if [ -n "${SQL_FILES}" ]; then
    ImportSQL
fi