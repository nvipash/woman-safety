CREATE USER IF NOT EXISTS 'safety'@'localhost' IDENTIFIED BY '457417';

create database if not exists WomanSafety;

GRANT ALL ON WomanSafety.* TO 'safety'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS WomanSafety.surveys (
    id_survey INT NOT NULL AUTO_INCREMENT,
    survey NVARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    PRIMARY KEY (id_survey)
);

insert into WomanSafety.surveys (survey , description)
values('Опитування для визначення ризику насильства', 'Шановний користувач, зараз Ви маєте змогу пройти опитування для визначення ризику стати жертвою насилля.
Якщо Ви вже були жертвою насилля або вважаєте, що можете нею стати, то пройдіть опитування, щоб дізнатися у якій групі ризику Ви зараз перебуваєте.');

CREATE TABLE IF NOT EXISTS WomanSafety.questions (
    id_question INT NOT NULL AUTO_INCREMENT,
    question NVARCHAR(200) NOT NULL,
    id_survey INT NOT NULL,
    points INT NOT NULL,
    PRIMARY KEY (id_question),
    FOREIGN KEY (id_survey)
        REFERENCES WomanSafety.surveys (id_survey)
);

insert into WomanSafety.questions (question, id_survey, points)
values 
('Чи відчуваєте ви себе у небезпеці поряд з Вашим партнером?', 1, 1),
('Чи має він пристрасть до алкоголю\наркотиків?', 1, 1), 
('Чи проявляє він надмірні ревнощі?', 1, 1), 
('Чи часто Ваш партнер підвищує голос на вас або використовує нецензурні слова у вашу адресу?', 1, 1), 
('Чи обмежує він ваше спілкування з друзями або родичами?', 1, 1),
('Чи бувають у вашого партнера приступи агресії?', 1, 1),
('Чи скоював він раніше серйозне насильство?', 1, 1),
('Чи має він доступ до зброї?', 1, 1),
('Чи зривав він злість на домашніх тваринах?', 1,1),
('Чи погрожує він самогубством?', 1, 1),
('Чи погрожує він вбивством?', 1, 1),
('Чи застосовував він фізичну силу до вас?',1, 1);



CREATE TABLE IF NOT EXISTS WomanSafety.user_score (
    id_score INT AUTO_INCREMENT NOT NULL,
    user_phone VARCHAR(20) NOT NULL,
    score INT NOT NULL,
    id_survey INT NOT NULL,
    PRIMARY KEY (id_score),
    FOREIGN KEY (id_survey)
        REFERENCES WomanSafety.surveys (id_survey)
);

CREATE TABLE IF NOT EXISTS WomanSafety.instructions (
    id_instruction INT AUTO_INCREMENT NOT NULL,
    title NVARCHAR(150) NOT NULL,
    instruction TEXT NOT NULL,
    range_start INT NOT NULL,
    range_end INT NOT NULL,
    id_survey INT NOT NULL,
    PRIMARY KEY (id_instruction),
    FOREIGN KEY (id_survey)
        REFERENCES WomanSafety.surveys (id_survey)
);

insert into WomanSafety.instructions (title, instruction, range_start, range_end, id_survey) 
values
('Все в нормі', 'Вам немає за що хвилюватись. Не забувайте про спілкування зі своїм партнером та не тримайте у собі, якщо бачите проблеми у ваших стосунках', 1, 3, 1),
('Вам варто поговорити зі своїм партнером', 'Якщо щось йде не так, як хотілося б, необхідно просто поговорити і пошукати вирішення проблеми. Пам’ятайте, розмова і пошуки рішення повинні проходити в максимально доброзичливому тоні, з урахуванням думки всіх сторін.', 4, 6, 1),
('Ви можете бути потенційною жертвою насильства', 'Щоденно в Україні фіксують численну кількість випадків насильства в сім’ї, а близько половини жінок страждають від домашнього насильства на постійній основі. Не зволікайте, якщо вважаєте що вам потрібна допомога - натисність SOS і ми обовяково допоможемо вам', 7, 9, 1),
('Ви в небезпеці!', 'Пам’ятайте, що спричинення будь-якого виду тілесних ушкоджень, а також вчинення будь-яких інших дій проти життя та здоров’я, є злочинами. Перебування з кривдником в сімейних відносинах не виключає необхідності притягнення його до кримінальної відповідальності. Якщо вам буде потрібна допомога, не вагайтесь та натисніть SOS!', 10, 12, 1)


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





