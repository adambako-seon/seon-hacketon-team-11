--create schema public;

create or replace function public.update_timestamp()
returns trigger as $$
begin
    new.updated_at = now();
return new;
end;
$$ language plpgsql;


create table if not exists public.users (
                                                    id serial primary key,
                                                    username varchar(255) unique not null,
    full_name varchar(255) not null,
    place_of_birth varchar(255),
    date_of_birth date,
    created_by varchar(255) default 'admin',
    created_at timestamp default current_timestamp,
    updated_by varchar(255) default 'admin',
    updated_at timestamp default current_timestamp,
    version int default 1
    );
create trigger set_timestamp_users
    before update on public.users
    for each row
    execute function public.update_timestamp();


create table if not exists public.shipping (
                                                       id serial primary key,
                                                       user_id int not null,
                                                       created_by varchar(255) default 'admin',
    created_at timestamp default current_timestamp,
    updated_by varchar(255) default 'admin',
    updated_at timestamp default current_timestamp,
    version int default 1,
    foreign key (user_id) references public.users(id) on delete cascade
    );
create trigger set_timestamp_shipping
    before update on public.shipping
    for each row
    execute function public.update_timestamp();

create table if not exists public.address (
                                                      id serial primary key,
                                                      shipping_id int not null,
                                                      country varchar(255) not null,
    region varchar(255),
    zip varchar(20),
    street varchar(255) not null,
    created_by varchar(255) default 'admin',
    created_at timestamp default current_timestamp,
    updated_by varchar(255) default 'admin',
    updated_at timestamp default current_timestamp,
    version int default 1,
    foreign key (shipping_id) references public.shipping(id) on delete cascade
    );
create trigger set_timestamp_address
    before update on public.address
    for each row
    execute function public.update_timestamp();

create table if not exists public.phone (
                                                    id serial primary key,
                                                    shipping_id int not null,
                                                    phone_number varchar(20) not null,
    created_by varchar(255) default 'admin',
    created_at timestamp default current_timestamp,
    updated_by varchar(255) default 'admin',
    updated_at timestamp default current_timestamp,
    version int default 1,
    foreign key (shipping_id) references public.shipping(id) on delete cascade
    );
create trigger set_timestamp_phone
    before update on public.phone
    for each row
    execute function public.update_timestamp();



insert into public.users(username, full_name, place_of_birth, date_of_birth) values
                                                                                         ('vladsdog', 'orban viktor', 'felcsut', '1969-06-09'),
                                                                                         ('samizdatboy', 'rogan antal', 'budapest', '1996-09-06');

insert into public.shipping (user_id) values
                                                  ('1'),
                                                  ('1'),
                                                  ('2');


insert into public.address (shipping_id, country, region, zip, street) values
                                                                                   (1, 'hungary', 'pest', '9999', 'cinege u.'),
                                                                                   (2, 'romania', 'cluj', '8888', 'strada emil cioran'),
                                                                                   (3, 'hungary', 'felcsut', '7777', 'gazszerelo u.')
;


insert into public.phone (shipping_id, phone_number) values
                                                                 (1, '01923843902'),
                                                                 (2, '01994283434'),
                                                                 (3, '01123234455');