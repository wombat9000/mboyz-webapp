CREATE TABLE role (
  role_id SERIAL,
  name varchar(100) NOT NULL
);

CREATE TABLE privilege (
  privilege_id SERIAL,
  name varchar(100) NOT NULL
);

ALTER TABLE role ADD PRIMARY KEY (role_id);
ALTER TABLE privilege ADD PRIMARY KEY (privilege_id);

CREATE TABLE user_role (
  user_id int REFERENCES "user" (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  role_id int REFERENCES role (role_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE role_privilege (
  role_id int REFERENCES role (role_id) ON UPDATE CASCADE ON DELETE CASCADE,
  privilege_id int REFERENCES privilege (privilege_id) ON UPDATE CASCADE ON DELETE CASCADE
);