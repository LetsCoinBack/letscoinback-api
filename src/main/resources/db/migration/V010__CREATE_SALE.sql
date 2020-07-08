create table public.sale (
	id serial primary key not null,
	wallet_id serial not null references public.wallet (id),
	pre_sale_id serial not null references public.pre_sale (id),
	sale_value numeric (9,2) not null,
	cashback_value numeric (9, 2) not null,
	transection_provider varchar not null
)
