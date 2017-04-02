#!/usr/bin/env bash

function createdb() {
	DATABASE=$1
    PGPASSWORD=postgres psql -U postgres -h localhost -c "drop database if exists ${DATABASE}"
    PGPASSWORD=postgres psql -U postgres -h localhost -c "create database ${DATABASE} OWNER = mboyz_app"
}

createdb "mboyz"