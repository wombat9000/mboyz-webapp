CREATE TABLE participation (
  participation_id SERIAL,
  holiday_id int REFERENCES holiday (holiday_id) ON DELETE CASCADE,
  user_id int REFERENCES "user" (user_id) ON DELETE CASCADE,
  PRIMARY KEY (participation_id)
);