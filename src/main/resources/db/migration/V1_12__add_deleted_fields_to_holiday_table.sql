ALTER TABLE holiday ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE holiday ADD COLUMN deleting_user_id INTEGER;
ALTER TABLE holiday ADD FOREIGN KEY (deleting_user_id) REFERENCES "user" (user_id);