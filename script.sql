create table t_admin
(
  F_ID    int(5) default '1' not null
    primary key,
  F_SCALE int(5)             not null
);

create table t_client
(
  F_ID         int(5) auto_increment
    primary key,
  F_NAME       varchar(20) not null,
  F_SURNAME    varchar(20) not null,
  F_PASSPORT   varchar(11) not null,
  F_ID_USER_FK int(5)      not null
);

create table t_client_credit
(
  F_ID_CREDIT_FK int         not null,
  F_ID_CLIENT_FK int         not null,
  F_ISSUE_DATA   varchar(45) null
);

create table t_credit
(
  F_ID               int(5) auto_increment
    primary key,
  F_TERM             varchar(40) not null,
  F_PERCENT          varchar(5)  not null,
  F_SUM              varchar(10) not null,
  F_CREDITTYPE_ID_FK int(5)      not null,
  F_ASSESSMENT       varchar(5)  not null
);

create index F_CREDITTYPE_ID_FK_idx
  on t_credit (F_CREDITTYPE_ID_FK);

create table t_credittype
(
  F_ID       int(5) auto_increment
    primary key,
  F_TYPENAME varchar(20) not null
);

create table t_user
(
  F_ID       int(5) auto_increment
    primary key,
  F_USERNAME varchar(10) not null,
  F_PASSWORD varchar(10) not null,
  F_ROLE     varchar(10) not null
);

