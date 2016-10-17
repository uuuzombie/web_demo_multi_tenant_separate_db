#! /bin/bash

echo "====> drop database"
sudo -u postgres dropdb $1
