alter table product alter column price type decimal;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";



ALTER TABLE product ALTER COLUMN id DROP DEFAULT,
ALTER COLUMN id SET DATA TYPE UUID USING (uuid_generate_v4()),
ALTER COLUMN id SET DEFAULT uuid_generate_v4();

ALTER TABLE product  DROP constraint product_manufacturer_id_fkey;

ALTER TABLE product ALTER COLUMN manufacturer_id DROP DEFAULT,
ALTER COLUMN manufacturer_id SET DATA TYPE UUID USING (uuid_generate_v4()),
ALTER COLUMN manufacturer_id SET DEFAULT uuid_generate_v4();

ALTER TABLE manufacturer ALTER COLUMN id DROP DEFAULT,
ALTER COLUMN id SET DATA TYPE UUID USING (uuid_generate_v4()),
ALTER COLUMN id SET DEFAULT uuid_generate_v4();

ALTER TABLE product ADD constraint product_manufacturer_id_fkey
foreign key (manufacturer_id) references manufacturer (id);

ALTER TABLE users ALTER COLUMN id DROP DEFAULT,
ALTER COLUMN id SET DATA TYPE UUID USING (uuid_generate_v4()),
ALTER COLUMN id SET DEFAULT uuid_generate_v4();

ALTER TABLE users  DROP constraint users_role_id_fkey;
ALTER TABLE users DROP COLUMN role_id;

ALTER table role ALTER COLUMN id DROP DEFAULT,
ALTER COLUMN id SET DATA TYPE UUID USING (uuid_generate_v4()),
ALTER COLUMN id SET DEFAULT uuid_generate_v4();


