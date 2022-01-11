CREATE TABLE user_to_role(user_id uuid not null, role_id uuid not null,
FOREIGN KEY(user_id) REFERENCES users (id),
	FOREIGN KEY(role_id) REFERENCES role (id));

