--create schema public;


CREATE
OR REPLACE FUNCTION public.update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= NOW();
RETURN NEW;
END;
$$
LANGUAGE plpgsql;


CREATE TABLE IF NOT EXISTS public.users
(
    id
    SERIAL
    PRIMARY
    KEY,
    username
    VARCHAR
(
    255
) UNIQUE NOT NULL,
    full_name VARCHAR
(
    255
) NOT NULL,
    place_of_birth VARCHAR
(
    255
),
    date_of_birth DATE,
    created_by VARCHAR
(
    255
) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    255
) DEFAULT 'admin',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 1
    );
CREATE TRIGGER set_timestamp_users
    BEFORE UPDATE
    ON public.users
    FOR EACH ROW
    EXECUTE FUNCTION public.update_timestamp();


CREATE TABLE IF NOT EXISTS public.shipping
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    created_by
    VARCHAR
(
    255
) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    255
) DEFAULT 'admin',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 1,
    FOREIGN KEY
(
    user_id
) REFERENCES public.users
(
    id
) ON DELETE CASCADE
    );
CREATE TRIGGER set_timestamp_shipping
    BEFORE UPDATE
    ON public.shipping
    FOR EACH ROW
    EXECUTE FUNCTION public.update_timestamp();

CREATE TABLE IF NOT EXISTS public.address
(
    id
    SERIAL
    PRIMARY
    KEY,
    shipping_id
    INT
    NOT
    NULL,
    country
    VARCHAR
(
    255
) NOT NULL,
    region VARCHAR
(
    255
),
    zip VARCHAR
(
    20
),
    street VARCHAR
(
    255
) NOT NULL,
    created_by VARCHAR
(
    255
) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    255
) DEFAULT 'admin',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 1,
    FOREIGN KEY
(
    shipping_id
) REFERENCES public.shipping
(
    id
) ON DELETE CASCADE
    );
CREATE TRIGGER set_timestamp_address
    BEFORE UPDATE
    ON public.address
    FOR EACH ROW
    EXECUTE FUNCTION public.update_timestamp();


CREATE TABLE IF NOT EXISTS public.phone
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    phone_number
    VARCHAR
(
    20
) NOT NULL,
    created_by VARCHAR
(
    255
) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR
(
    255
) DEFAULT 'admin',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 1,
    FOREIGN KEY
(
    user_id
) REFERENCES public.users
(
    id
) ON DELETE CASCADE
    );
CREATE TRIGGER set_timestamp_phone
    BEFORE UPDATE
    ON public.phone
    FOR EACH ROW
    EXECUTE FUNCTION public.update_timestamp();



insert into public.users(username, full_name, place_of_birth, date_of_birth)
values ('vladsdog', 'Orban Viktor', 'Felcsut', '1969-06-09'),
       ('samizdatboy', 'Rogan Antal', 'Budapest', '1996-09-06') on conflict do nothing;

insert into public.shipping (user_id)
values ('1'),
       ('1'),
       ('2') on conflict do nothing;



insert into public.address (shipping_id, country, region, zip, street)
values (1, 'Hungary', 'Pest', '9999', 'Cinege u.'),
       (2, 'Romania', 'Cluj', '8888', 'strada Emil Cioran'),
       (3, 'Hungary', 'Felcsut', '7777', 'Gazszerelo u.') on conflict do nothing;


insert into public.phone (user_id, phone_number)
values (1, '01923843902'),
       (2, '01994283434'),
       (3, '01123234455') on conflict do nothing;