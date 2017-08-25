CREATE TABLE holiday_user (
  holiday_id int REFERENCES holiday (holiday_id) ON UPDATE CASCADE ON DELETE CASCADE,
  user_id int REFERENCES "user" (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);