create table public.master_configuration (
	key varchar primary key not null,
	description varchar not null,
	value varchar not null
);

insert into master_configuration values ('WALLET_GUID', 'GUID da carteira que será debitada', '4b8cd8e9-9480-44cc-b7f2-527e98ee3287');
insert into master_configuration values ('WALLET_ADDRESS', 'Endereço da carteira que será debitada', '12AaMuRnzw6vW6s2KPRAGeX53meTf8JbZS');
insert into master_configuration values ('WALLET_PASSWORD', 'Senha da carteira que será debitada', 'Cashback@99');

insert into provider 
values (2, 'Awin', '22faab66-ac70-49b8-be90-5a048694dad4', '', '741107', 'clickRef', null, 'https://api.awin.com/publishers/');
