create table Schema_Visitors
(
    VISIT_ID                  int                       not null,
    VISIT_VISITOR             varchar(50) charset utf8  null,
    FROM_STAMP                datetime                  null,
    TO_STAMP                  datetime                  null,
    VISIT_PURPOSE             varchar(50) charset utf8  null,
    VISIT_PURPOSE_DESCRIPTION varchar(250) charset utf8 null,
    VISIT_PURPOSE_STATUS_TYPE varchar(10) charset utf8  null,
    constraint Schema_Visitors_VISIT_ID_uindex
        unique (VISIT_ID)
);

alter table Schema_Visitors
    add primary key (VISIT_ID);


