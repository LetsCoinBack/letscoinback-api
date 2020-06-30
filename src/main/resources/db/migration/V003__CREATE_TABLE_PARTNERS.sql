create table public.partner (
  id serial primary key not null,
  name varchar not null,
  photo varchar not null,
  redirect varchar not null,
  cashback int,
  user_cashback int,
  position int
);

create table public.configuration (
	key varchar primary key not null,
	description varchar not null,
	value varchar not null
);

insert into public.configuration values ('DEFAULT_CASHBACK_PARTNER', 'Valor padrão de cashback das lojas', '5');
insert into public.configuration values ('DEFAULT_CASHBACK_USER', 'Valor padrão de cashback repassado ao usuário', '3');

