CREATE TABLE comment (
  comment_id SERIAL,
  holiday_id int REFERENCES holiday (holiday_id) ON DELETE CASCADE,
  user_id int REFERENCES "user" (user_id) ON DELETE CASCADE,
  text TEXT,
  created TIMESTAMP,
  PRIMARY KEY (comment_id)
);