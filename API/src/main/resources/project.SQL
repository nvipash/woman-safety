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
    points_often INT NOT NULL,
    points_seldom INT NOT NULL,
    points_never INT NOT NULL,
    PRIMARY KEY (id_question),
    FOREIGN KEY (id_survey)
        REFERENCES WomanSafety.surveys (id_survey)
);

insert into WomanSafety.questions (question, id_survey, points_often, points_seldom, points_never)
values
('Чи відчуваєте ви себе у небезпеці поряд з Вашим партнером?', 1, 2, 1, 0),
('Чи має він пристрасть до алкоголю\наркотиків?', 1, 2, 1, 0),
('Чи проявляє він надмірні ревнощі?', 1, 2, 1, 0),
('Ваш партнер підвищує на вас голос або ображає словесно? (використовує нецензурну лексику, ображає прізвиськами чи прилюдно)', 1, 2, 1, 0),
('Чи обмежує він ваше спілкування з друзями або родичами?', 1, 2, 1, 0),
('Чи бувають у вашого партнера приступи агресії?', 1, 2, 1, 0),
('Чи скоював він раніше серйозне насильство? (штовхав, давав ляпаса, піднімав руку або бив іншими предметами)', 1, 2, 1, 0),
('Чи контролює куди ви ходите, перевіряє вміст смс повідомлень, або вимагає паролі доступу до пошти, чи соцмереж?', 1, 2, 1, 0),
('Ваш партнер контролює ваші фінансові витрати? (перевіряє за чеком закупки, забирає зарплату)', 1, 2, 1, 0),
('Ваш партнер змушував Вас до статевих стосунків? (заставляв вмовлянням, використовував фізичну силу)', 1, 2, 1, 0),
('Чи має він доступ до зброї?', 1, 2, 1, 0),
('Чи зривав він злість на домашніх тваринах?', 1,2, 1, 0),
('Чи погрожує він самогубством?', 1, 2, 1, 0),
('Чи погрожує він вбивством? (словесно або ж діями)', 1, 2, 1, 0),
('Чи застосовував він фізичну силу до вас? (залишались синяки чи гематоми від ударів)',1, 2, 1, 0);

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
('Все в нормі', 'Вам немає за що хвилюватись. Не забувайте про спілкування зі своїм партнером та не тримайте у собі, якщо бачите проблеми у ваших стосунках', 0, 6, 1),
('Вам варто поговорити зі своїм партнером', 'Якщо щось йде не так, як хотілося б, необхідно просто поговорити і пошукати вирішення проблеми. Пам’ятайте, розмова і пошуки рішення повинні проходити в максимально доброзичливому тоні, з урахуванням думки всіх сторін.', 7, 14, 1),
('Ви можете бути потенційною жертвою насильства', 'Щоденно в Україні фіксують численну кількість випадків насильства в сім’ї, а близько половини жінок страждають від домашнього насильства на постійній основі. Не зволікайте, якщо вважаєте що вам потрібна допомога - натисність SOS і ми обовяково допоможемо вам', 15, 22, 1),
('Ви в небезпеці!', 'Пам’ятайте, що спричинення будь-якого виду тілесних ушкоджень, а також вчинення будь-яких інших дій проти життя та здоров’я, є злочинами. Перебування з кривдником в сімейних відносинах не виключає необхідності притягнення його до кримінальної відповідальності. Якщо вам буде потрібна допомога, не вагайтесь та натисніть SOS!', 23, 30, 1);

CREATE TABLE IF NOT EXISTS WomanSafety.locations (
    id_place INT AUTO_INCREMENT NOT NULL,
    name_of_place NVARCHAR(200) NOT NULL,
    place_description TEXT NULL,
    phone VARCHAR(20) NULL,
    lat DOUBLE NOT NULL,
    lng DOUBLE NOT NULL,
    PRIMARY KEY (id_place)
);

insert into WomanSafety.locations (name_of_place, place_description, phone, lat, lng)
values
('Головне управління національної поліції у Львівській області', '','0322586840', 49.84161, 24.0220921),
('Управління патрульної поліції у м. Львові', '','0322340932', 49.8143646, 23.9876744),
('Залізничний районний відділ поліції', '', '0322586900',49.834893, 23.991080),
('Франківський районний відділ поліції', '', '0322389551',49.828575, 24.005964),
('Шевченківський районний відділ поліції', '', '0322586719',49.843883, 24.019660),
('Личаківський районний відділ поліції', '', '0322762400',49.833691, 24.038584);

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
foreign key (idAnswer) references WomanSafety.answers(id_asw));
create table WomanSafety.phones (
owner nvarchar(200) not null,
phone varchar(20) not null,
primary key(owner)
);*/