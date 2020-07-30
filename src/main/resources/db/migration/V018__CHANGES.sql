UPDATE public."configuration"
SET description='Valor padrão de cashback repassado ao usuário (% em cima do cashback recebido)'
WHERE "key"='DEFAULT_CASHBACK_USER';

create table partner_X_segment (
	id serial primary key not null,
	partner_id serial not null references public.partner (id),
	partner_segment_id serial not null references public.partner_segment (id)
);

alter table partner  drop column partner_segment_id;

