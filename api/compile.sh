#!/bin/bash  

temp=$(pwd)
for file in src/main/resources/thrift/*
do  
if [ -f "$file" ]  
then  
  echo "compile $file"
  thrift --gen java $temp/$file
fi
done
cd src/main/java
rm -rf com
cd ../../../
cd gen-java
mv com ../src/main/java
cd ..
rm -rf gen-java
