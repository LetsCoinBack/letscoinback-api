create table public.provider (
	id serial primary key not null,
	name varchar not null,
	token varchar,
	"user" varchar,
	password varchar,
	publisher varchar,
	param_send varchar
);

insert into public.provider
values (1, 'Lomadee', null, 'andrerbrant@gmail.com', 'Cashback@99', '22905908', 'mdasc');

select * from public.provider;

alter table partner add column provider_id serial;

update public.partner set provider_id = 1;

alter table public.partner ADD CONSTRAINT fk_provider_id_partner FOREIGN KEY (provider_id) REFERENCES public.provider (id);

create table public.pre_sale (
	id serial primary key not null,
	user_id serial not null references public.user(id),
	partner_id serial not null references public.partner (id)
);
