#! /bin/bash

echo "====> drop database"
sudo -u postgres dropdb default_db

#echo "====> drop user"
#sudo -u postgres dropuser sps