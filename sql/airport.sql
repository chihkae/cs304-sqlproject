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
	flight_number varchar(7) not null,
	departure_date date null,
	name varchar(40) not null,
	phone_number char(14) null,
	address varchar(80) null,
	primary key (id),
	foreign key(flight_number,departure_date) references departure_flight(flight_number, departure_date) ON DELETE CASCADE);
 
create table baggage
	(baggage_number int not null,
	passenger_id int not null,
	carousel_number int not null,
	terminal_number int null,
	primary key (baggage_number),
	foreign key (passenger_id) references passenger(id) ON DELETE CASCADE,
	foreign key (carousel_number, terminal_number) references baggage_carousel(carousel_number, terminal_number) ON DELETE CASCADE);
 
grant select on baggage to public;


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
 
grant select on customer_service to public;

