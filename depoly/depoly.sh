#! /bin/bash

CURRENT_HOME=`pwd`
INIT_SQL=${CURRENT_HOME}/init.sql
INIT_TENANT_SQL=${CURRENT_HOME}/init_tenant.sql
DEFAULT_DB_SQL_FILES=${CURRENT_HOME}/sql/default_db/*.sql
TENANT_SQL_FILES=${CURRENT_HOME}/sql/tenant/*.sql

DB_IP=127.0.0.1
DB_PORT=5432
DB_USERNAME=sps
DB_PASSWD=sps
DB_NAME=default_db

export PGPASSWORD=admin


ImportSQL()
{
    for FILE in ${DEFAULT_DB_SQL_FILES}; do
        echo "==> import sql : ${FILE}"
        #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
        psql -f ${FILE} "host=${DB_IP} port=${DB_PORT} user=${DB_USERNAME} password=${DB_PASSWD}" | grep "ERROR"
    done
}

ImportTenantSQL()
{
    for FILE in ${TENANT_SQL_FILES}; do
        echo "==> import sql : ${FILE}"
        #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
        psql -f ${FILE} "host=${DB_IP} port=${DB_PORT} user=${DB_USERNAME} password=${DB_PASSWD}" | grep "ERROR"
    done
}


#start
echo "====> start import sql"
if [ -n "${INIT_SQL}" ]; then
    echo "==> import init sql"
    #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
    sudo -u postgres psql -f ${INIT_SQL}  | grep "ERROR"
fi

if [ -n "${INIT_TENANT_SQL}" ]; then
    echo "==> import init sql"
    #psql -h ${DB_IP} -p ${DB_PORT} -U ${DB_USERNAME} -d ${DB_NAME} < ${FILE}
    sudo -u postgres psql -f ${INIT_TENANT_SQL}  | grep "ERROR"
fi


if [ -n "${DEFAULT_DB_SQL_FILES}" ]; then
    ImportSQL
fi

if [ -n "${TENANT_SQL_FILES}" ]; then
    ImportTenantSQL
fi