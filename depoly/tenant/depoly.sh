#! /bin/bash

CURRENT_HOME=`pwd`
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
        psql -f ${FILE} "host=${DB_IP} port=${DB_PORT} user=${DB_USERNAME} password=${DB_PASSWD} dbname=${DB_NAME}" | grep "ERROR" | tee -a /tmp/$DATE.log
    done
}


#start
echo "====> start import sql..."
if [ -n "${DB_NAME}" ]; then
    echo -e "\n create tenat db : ${DB_NAME}"
    sudo -u postgres createdb -E utf8 ${DB_NAME} -O ${DB_USERNAME} | grep "ERROR" | tee -a /tmp/$DATE.log


    echo -e "\n start import sqls to db : ${DB_NAME}"
    if [ -n "${SQL_FILES}" ]; then
        ImportSQL
    fi
else
    echo -e "input database name is error! \n\n"
fi
echo -e "import sql completed...\n\n"

