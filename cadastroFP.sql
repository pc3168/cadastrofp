CREATE DATABASE cadastrofp;

use cadastrofp;

GRANT ALL ON *.* TO 'root'@'%' IDENTIFIED BY 'cadastrofp' WITH GRANT OPTION; 

FLUSH PRIVILEGES;

drop table if exists CLIENTES;

drop table if exists DADOSFP;

drop table if exists PRODUTO;

drop table if exists VENDAS;

/*==============================================================*/
/* Table: CLIENTES                                              */
/*==============================================================*/
create table CLIENTES
(
   CLI_CPF              varchar(11) not null,
   CLI_NOME             varchar(50) not null,
   CLI_ENDERECO         varchar(50) not null,
   CLI_NUMERO           numeric(5,0) not null,
   CLI_BAIRRO           varchar(50),
   CLI_COMPLEMENTO      varchar(50),
   CLI_CEP              varchar(9),
   CLI_CIDADE           varchar(50),
   CLI_ESTADO           varchar(2),
   CLI_TELEFONE1        varchar(19) not null,
   CLI_TELEFONE2        varchar(19),
   CLI_CODIGOSISTEMA    numeric(10,0),
   primary key (CLI_CPF)
)ENGINE=MyISAM;

/*==============================================================*/
/* Table: DADOSFP                                               */
/*==============================================================*/
create table DADOSFP
(
   FP_AUTORIZACAO       numeric(15,0) not null,
   CLI_CPF              varchar(11) not null,
   FP_DATA_VENDA        date not null,
   FP_DATA_PROX_VENDA   date not null,
   FP_CUPOM             numeric(9,0) not null,
   primary key (FP_AUTORIZACAO)
) ENGINE=MyISAM;

/*==============================================================*/
/* Table: PRODUTO                                               */
/*==============================================================*/
create table PRODUTO
(
   PRO_ID               int not null,
   PRO_DESCRICAO        varchar(80) not null,
   PRO_BARRAS           varchar(14),
   primary key (PRO_ID)
) ENGINE=MyISAM;

/*==============================================================*/
/* Table: VENDAS                                                */
/*==============================================================*/
create table VENDAS
(
   VEN_ID               int not null,
   PRO_ID               int not null,
   CLI_CPF              varchar(11) not null,
   VEN_AUTORIZACAO      varchar(19) not null,
   VEN_DATA_VENDA       datetime not null,
   VEN_CUPOM            numeric(9,0) not null,
   VEN_CAIXA            varchar(3) not null,
   primary key (VEN_ID)
) ENGINE=MyISAM;

alter table DADOSFP add constraint FK_DADOSFP_CLIENTES foreign key (CLI_CPF)
      references CLIENTES (CLI_CPF) on delete restrict on update restrict;

alter table VENDAS add constraint FK_VENDAS_CLIENTES foreign key (CLI_CPF)
      references CLIENTES (CLI_CPF) on delete restrict on update restrict;

alter table VENDAS add constraint FK_VENDAS_PRODUTOS foreign key (PRO_ID)
      references PRODUTO (PRO_ID) on delete restrict on update restrict;
