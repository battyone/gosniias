import groovy.sql.Sql

def url = 'jdbc:postgresql://localhost:5432/gosniias'
def user = 'gosniias'
def password = 'gosniias'
def driver = 'org.postgresql.Driver'
def sql = Sql.newInstance(url, user, password, driver)

//sql.execute '''
//  CREATE TABLE users (
//     id bigserial PRIMARY KEY NOT NULL,
//    username   VARCHAR(20),
//    role    VARCHAR(20)
//  );
//'''

def username='admin';

//sql.execute "INSERT INTO users (username, role) VALUES ('admin', 'ADMIN')"
//sql.execute "INSERT INTO users (username, role) VALUES ('user', 'USER')"
//sql.execute "INSERT INTO users (username, role) VALUES ('dba', 'DBA')"

def row = sql.firstRow ("SELECT username, role FROM users where username='"+username+"'")


sql.close()
println row
return row