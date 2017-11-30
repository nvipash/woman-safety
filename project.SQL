create database WomanSafety;

create table WomanSafety.surveys (
id_survey varchar(10) not null,
survey nvarchar(200) not null,
description  text not null,
primary key (id_survey)
);

insert into WomanSafety.surveys
values('test1', 'опитування для визначення ризику насильства', 'Шановний користувач, зараз Ви маєте змогу пройти опитування для визначення ризику стати жертвою насилля.
Якщо Ви вже були жертвою насилля або вважаєте, що можете нею стати, то пройдіть опитування, щоб дізнатися у якій групі ризику Ви зараз перебуваєте.
Для того, щоб почати,натисніть кнопку "Start".');

create table WomanSafety.questions (
id_question int not null auto_increment,
question nvarchar(200) not null,
id_survey varchar(10) not null,
points int not null,
primary key (id_question),
foreign key (id_survey) references WomanSafety.surveys(id_survey)
);

insert into WomanSafety.questions ( question, id_survey, points)
values 
('Чи відчуваєте ви себе у небезпеці поряд з Вашим партнером?','test1', 1),
('Чи має він пристрасть до алкоголю\наркотиків?', 'test1', 1), 
('Чи проявляє він надмірні ревнощі?', 'test1', 1), 
('Чи часто Ваш партнер підвищує голос на вас або використовує нецензурні слова у вашу адресу?', 'test1', 1), 
('Чи обмежує він ваше спілкування з друзями або родичами?', 'test1', 1),
('Чи бувають у вашого партнера приступи агресії?', 'test1', 1),
('Чи скоював він раніше серйозне насильство?', 'test1', 1),
('Чи має він доступ до зброї?', 'test1'),
('Чи зривав він злість на домашніх тваринах?', 'test1', 1),
('Чи погрожує він самогубством?', 'test1', 1),
('Чи погрожує він вбивством?', 'test1', 1),
('Чи застосовував він фізичну силу до вас?','test1', 1);



create table WomanSafety.user_score(
id_score int auto_increment not null,
user_phone varchar(20) not null,
score int not null,
id_survey varchar(10) not null,
primary key (id_score),
foreign key (id_survey) references WomanSafety.surveys(id_survey));

create table WomanSafety.instructions(
id_instruction int auto_increment not null,
title nvarchar(100) not null,
instruction text not null,
range_start int not null,
range_end int not null,
id_survey varchar(10) not null,
primary key (id_instruction),
foreign key (id_survey) references WomanSafety.surveys(id_survey));

/*create table WomanSafety.answers(
id_answer int auto_increment not null,
answer varchar(100) not null,
primary key (idAnswer));*/

/*create table WomanSafety.points(
id_p int auto_increment not null,
idQuestion int not null,
idAnswer int not null,
point int not null,
primary key (id_p),
foreign key (idQuestion) references WomanSafety.questions(id_q),
foreign key (idAnswer) references WomanSafety.answers(id_asw));*/

/*create table WomanSafety.locations(
place nvarchar(200) not null,
coordinates point not null,
primary key (place));

create table WomanSafety.phones (
owner nvarchar(200) not null,
phone varchar(20) not null,
primary key(owner)
);*/





