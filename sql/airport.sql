drop database if exists airport;
create database airport;
use airport;

create table terminals
	(terminal_number int not null,
	address varchar(80) null,
	phone_number varchar(20) null,
	vip_lounge boolean null,
	primary key (terminal_number));

create table baggage_carousel
	(carousel_number int not null,
	 terminal_number int not null,
	 primary key (carousel_number, terminal_number),
	 foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE);

create table general_service
	(id int not null,
	phone_number char(12) null,
	operating_hours char(9) null,       
	primary key (id));
 

create table gates
	(gate_number int not null,
	terminal_number int not null,
	primary key (gate_number, terminal_number),
	foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE);


create table airlines
	(name varchar(25) not null,
	 primary key(name));

create table arrival_flight
	(flight_number varchar(10) not null,
	arrival_date date not null,
	departure_destination varchar(20) null,
	arrival_time time not null,
	terminal_number int not null,
	carousel_number int not null,
	gate_number int not null,
	airline_name varchar(25) not null,
	primary key (flight_number, arrival_date),
	foreign key (gate_number, terminal_number) references gates(gate_number, terminal_number) ON DELETE CASCADE,
	foreign key (airline_name) references airlines(name) ON DELETE CASCADE,
	foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE,
	foreign key (carousel_number, terminal_number) references baggage_carousel(carousel_number,terminal_number) ON DELETE CASCADE);
	
create table departure_flight
	(flight_number char(7) not null,
	departure_date date not null,
	destination varchar(20) null,
	departure_time time not null,
	terminal_number int not null,
	gate_number int not null,
	airline_name varchar(25) not null,
	primary key (flight_number, departure_date),
	foreign key (gate_number,terminal_number) references gates(gate_number, terminal_number) ON DELETE CASCADE,
	foreign key (airline_name) references airlines(name) ON DELETE CASCADE
	);

 
create table passenger
	(id int not null,
	departure_flight_number varchar(7) null,
	departure_date date null,
	arrival_flight_number varchar(7) null,
	arrival_date date null,
	name varchar(40) not null,
	phone_number char(14) null,
	address varchar(80) null,
	primary key (id),
	foreign key (departure_flight_number, departure_date) references departure_flight(flight_number, departure_date) on DELETE CASCADE,
	foreign key (arrival_flight_number, arrival_date) references arrival_flight(flight_number, arrival_date) on DELETE CASCADE);



 
create table baggage
	(baggage_number int not null,
	passenger_id int not null,
	carousel_number int not null,
	terminal_number int null,
	primary key (baggage_number),
	foreign key (passenger_id) references passenger(id) ON DELETE CASCADE,
	foreign key (carousel_number, terminal_number) references baggage_carousel(carousel_number, terminal_number) ON DELETE CASCADE);
 



create table uses
	(passenger_id int not null,
	general_service_id int not null,
	primary key (passenger_id, general_service_id),
	foreign key (passenger_id) references passenger(id) ON DELETE CASCADE,
	foreign key (general_service_id) references general_service(id) ON DELETE CASCADE);

create table restaurant
	(id int not null,
	terminal_number int not null,
	restaurant_name varchar(80) null,
	cuisine_type varchar(40) null,
	yelp_rating int null,
	primary key (id),
	foreign key (id) references general_service(id) ON DELETE CASCADE,
	foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE);
 
create table shop
	(id int not null,
	terminal_number int not null,
	shop_name varchar(40) null,
	category varchar(60) null,
	takes_credit_card boolean null,
	primary key (id),
	foreign key (id) references general_service(id) ON DELETE CASCADE,
	foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE);
 
create table customer_service
	(id int not null,
	terminal_number int not null,
	type varchar(80) null,
	non_english_service boolean null,
	primary key (id),
	foreign key (id) references general_service(id) ON DELETE CASCADE,
	foreign key (terminal_number) references terminals(terminal_number) ON DELETE CASCADE);
 

#grant select on customer_service to public;

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/terminals.txt ' into table terminals
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/baggage_carousel.txt ' into table baggage_carousel
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/General_Service.txt ' into table general_service
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/gates.txt ' into table gates
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/airlines.txt ' into table airlines
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/arrivals.txt ' into table arrival_flight
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/departure.txt ' into table departure_flight
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/passenger.txt ' into table passenger
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/baggage.txt' into table baggage
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/uses.txt ' into table uses
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/restaurants.txt ' into table restaurant
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/shops.txt ' into table shop
fields terminated by '\t';

load data local infile '/Users/Stanley/Documents/CS304/cs304-project/sql/customer_service.txt ' into table customer_service
fields terminated by '\t';
