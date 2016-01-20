CREATE TABLE IF NOT EXISTS users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id),
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

insert into users values (1, 'James', 'Gosling');
insert into users values (2, 'Martin', 'Martin');
insert into users values (3, 'Brendan', 'Eich');
insert into users values (4, 'Douglas', 'Crockford');