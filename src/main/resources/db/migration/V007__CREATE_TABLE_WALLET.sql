CREATE TYPE movimentation_type AS ENUM ('Entrada', 'Saída');
CREATE TYPE transaction_type AS ENUM ('Cadastro', 'Cashback', 'Indicação', 'Saque', 'Outros');
CREATE TYPE status_transacion AS ENUM ('Pendente', 'Confirmado', 'Rejeitado');

create table public.wallet (
	id serial primary key not null,
	date TIMESTAMP DEFAULT NOW(),
	description varchar not null,
	transaction_type transaction_type not null,
	movimentation_type movimentation_type not null,
	user_id serial references public.user(id),
	value  NUMERIC(9,2) not null,
	status status_transacion not null
)
