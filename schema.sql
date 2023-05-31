create table if not exists author
(
    id          int auto_increment
    primary key,
    name        varchar(255) null,
    description longtext     null,
    picture     varchar(255) null
    );

create table if not exists book
(
    id           int auto_increment
    primary key,
    cover        varchar(255) null,
    description  longtext     null,
    pages        int          not null,
    price        float        not null,
    quantity     int          not null,
    release_date date         null,
    title        varchar(255) null,
    author_id    int          null,
    sold         int          not null,
    constraint FKklnrv3weler2ftkweewlky958
    foreign key (author_id) references author (id)
    );

create table if not exists category
(
    id   int auto_increment
    primary key,
    name varchar(255) null
    );

create table if not exists book_category
(
    book_id     int not null,
    category_id int not null,
    constraint UKotu2m6hn1bdw12cpuyts2seki
    unique (book_id, category_id),
    constraint FKam8llderp40mvbbwceqpu6l2s
    foreign key (category_id) references category (id),
    constraint FKnyegcbpvce2mnmg26h0i856fd
    foreign key (book_id) references book (id)
    );

create table if not exists user
(
    id              int auto_increment
    primary key,
    address         varchar(255) null,
    email           varchar(255) null,
    name            varchar(255) null,
    password        varchar(255) null,
    role            varchar(255) null,
    username        varchar(255) null,
    profile_picture varchar(255) null,
    phone_number    varchar(255) null
    );

create table if not exists bill
(
    id           int auto_increment
    primary key,
    time_created datetime(6) null,
    total        float       not null,
    user_id      int         null,
    is_paid      bit         null,
    quantity     int         not null,
    book_id      int         null,
    constraint FKnfltxsahn5a5wle9sud82dymv
    foreign key (book_id) references book (id),
    constraint FKqhq5aolak9ku5x5mx11cpjad9
    foreign key (user_id) references user (id)
    );

create table if not exists review
(
    id      int auto_increment
    primary key,
    content longtext    null,
    date    datetime(6) null,
    score   int         not null,
    book_id int         null,
    user_id int         null,
    constraint FK70yrt09r4r54tcgkrwbeqenbs
    foreign key (book_id) references book (id),
    constraint FKiyf57dy48lyiftdrf7y87rnxi
    foreign key (user_id) references user (id)
    );

