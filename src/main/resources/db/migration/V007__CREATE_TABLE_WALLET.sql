CREATE TYPE movimentation_type AS ENUM ('Entrada', 'Saída');
CREATE TYPE transaction_type AS ENUM ('Cadastro', 'Cashback', 'Indicação', 'Saque', 'Outros');
CREATE TYPE status_transacion AS ENUM ('Pendente', 'Confirmado', 'Rejeitado');

create table public.wallet (
	id serial primary key not null,
	date TIMESTAMP DEFAULT NOW(),
	transaction_type transaction_type not null,
	movimentation_type movimentation_type not null,
	user_id serial references public.user(id),
	partner_id serial references public.partner (id),
    vlr_purchase NUMERIC(9,2) not null,
	vlr_cashback NUMERIC(9,2) not null,
	vlr_cashback_user  NUMERIC(9,2) not null,
	status status_transacion not null
)
