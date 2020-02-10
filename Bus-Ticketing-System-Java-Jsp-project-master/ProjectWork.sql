DROP TABLE USERS;
        
SELECT id from users WHERE phone = 'sasi' OR email = 'sasi' AND password = 'sasi';
        
INSERT INTO USERS VALUES('SASI','sasidharant1994@gmail.com',7708016544,'sasi','Chennai','break the rules');
    
 CREATE TABLE booking (
  id number NOT NULL,
  destination_id number NOT NULL,
  booking_date varchar2(30) NOT NULL,
  journey_date varchar2(30) NOT NULL,
  train_id number NOT NULL,
  seat_numbers varchar2(150) NOT NULL,
  passenger_id number NOT NULL,
  number_of_seat number NOT NULL,
  payment_status varchar2(10) NOT NULL,
  status varchar2(10) NOT NULL,
  note varchar2(220) NOT NULL
);

CREATE TABLE destinations (
  id number NOT NULL,
  station_from number NOT NULL,
  station_to number NOT NULL,
  train_id number NOT NULL,
  time varchar2(30) NOT NULL,
  status varchar2(20) NOT NULL,
  fare varchar2(5) NOT NULL,
  last_activity date NOT NULL,
  last_modify_by number NOT NULL,
  total_seat number NOT NULL,
  seat_range varchar2(10) NOT NULL,
  type varchar2(6) NOT NULL
);

CREATE TABLE stations (
  id number NOT NULL,
  name varchar2(50) NOT NULL,
  address varchar2(70) NOT NULL,
  contact varchar2(20) NOT NULL
) ;

DROP TABLE STATIONS;

CREATE TABLE trains (
  id number NOT NULL,
  code varchar2(30) NOT NULL,
  name varchar2(50) NOT NULL,
  total_seat number NOT NULL,
  type varchar2(20) NOT NULL 
);

CREATE TABLE users (
  id number NOT NULL,
  name varchar2(150) NOT NULL,
  email varchar2(50) NOT NULL,
  phone varchar2(13) NOT NULL,
  address varchar2(200) NOT NULL,
  password varchar2(150) NOT NULL,
  rule varchar2(10) NOT NULL,
  reg_date date NOT NULL ,
  last_activity date NOT NULL 
);


-----------------------Dump data---------------------------

INSERT INTO booking (id, destination_id, booking_date, journey_date, train_id, seat_numbers, passenger_id, number_of_seat, payment_status, status, note) VALUES
--(1, 11, '06-10-2018', '06-10-2018', 7, '1,2', 12, 2, 'pending', 'success', 'note');,
--(4, 11, '06-10-2018', '06-10-2018', 7, '3,4', 12, 2, 'pending', 'success', 'note');,
--(5, 16, '06-10-2018', '06-10-2018', 2, '41,42,43', 12, 3, 'pending', 'success', 'note');,
--(6, 13, '06-10-2018', '06-10-2018', 7, '57,58,59,60', 12, 4, 'pending', 'success', 'note');,
--(7, 15, '06-10-2018', '06-10-2018', 2, '21,22,23,24', 12, 4, 'pending', 'success', 'note');,
--(8, 14, '06-10-2018', '06-10-2018', 2, '1', 12, 1, 'pending', 'success', 'note');,
--(9, 14, '06-10-2018', '06-10-2018', 2, '2', 12, 1, 'pending', 'success', 'note');,
(10, 14, '06-10-2018', '06-10-2018', 2, '2', 12, 1, 'pending', 'success', 'note');,
(11, 14, '06-10-2018', '06-10-2018', 2, '2', 12, 1, 'pending', 'success', 'note');,
(12, 14, '06-10-2018', '06-10-2018', 2, '2', 12, 1, 'pending', 'success', 'note');,
(13, 12, '06-10-2018', '06-10-2018', 7, '6,7', 12, 2, 'pending', 'success', 'note');,
(14, 12, '06-10-2018', '06-10-2018', 7, '8,9,10', 12, 3, 'pending', 'success', 'note'),
(15, 12, '06-10-2018', '06-10-2018', 7, '8,9,10', 12, 3, 'pending', 'success', 'note'),
(16, 12, '06-10-2018', '06-10-2018', 7, '8,9,10', 12, 3, 'pending', 'success', 'note'),
(17, 16, '08-10-2018', '08-10-2018', 2, '41,42,43', 12, 3, 'pending', 'success', 'note'),
(18, 16, '10-10-2018', '10-10-2018', 2, '41,42,43', 12, 3, 'pending', 'success', 'note'),
(19, 15, '11-10-2018', '11-10-2018', 2, '21,22', 12, 2, 'pending', 'success', 'note'),
(20, 14, '11-10-2018', '11-10-2018', 2, '1', 12, 1, 'pending', 'success', 'note'),
(21, 27, '16-10-2018', '16-10-2018', 10, '1,2', 12, 2, 'pending', 'success', 'note'),
(22, 27, '11-10-2018', '11-10-2018', 10, '1', 12, 1, 'pending', 'success', 'note'),
(23, 27, '11-10-2018', '11-10-2018', 10, '2', 12, 1, 'pending', 'success', 'note'),
(24, 30, '11-10-2018', '11-10-2018', 8, '9', 12, 1, 'pending', 'success', 'note');

INSERT INTO destinations (id, station_from, station_to, train_id, time, status, fare, last_activity, last_modify_by, total_seat, seat_range, type) VALUES
--(11, 1, 2, 7, '40:30Am', 'active', '40', '05-FEB-18', 0, 5, '1-5', 'up');,
--(12, 1, 3, 7, '40:30Am', 'active', '240', '05-FEB-18', 0, 50, '6-56', 'up');,
--(13, 1, 4, 7, '40:30Am', 'active', '280', '05-FEB-18', 0, 5, '57-61', 'up');,
(14, 1, 2, 2, '1:20AM', 'active', '40', '05-FEB-18', 0, 20, '1-20', 'up');,
(15, 1, 3, 2, '1:20AM', 'active', '240', '2018-09-05 00:00:00', 0, 20, '21-40', 'up'),
(16, 1, 4, 2, '1:20AM', 'active', '280', '2018-09-05 00:00:00', 0, 10, '41-50', 'up'),
(17, 4, 1, 7, '2:50 AM', 'active', '240', '2018-09-05 00:00:00', 0, 20, '1-20', 'up'),
(18, 4, 3, 7, '2:50 AM', 'active', '40', '2018-09-05 00:00:00', 0, 50, '21-50', 'up'),
(23, 6, 4, 9, '10:5AM', 'active', '480', '2018-09-05 00:00:00', 0, 30, '21-50', 'up'),
(25, 6, 2, 9, '10:5AM', 'active', '350', '2018-09-05 00:00:00', 0, 20, '21-50', 'up'),
(27, 7, 1, 10, '10:5AM', 'active', '480', '2018-09-05 00:00:00', 0, 3, '1-3', 'up'),
(28, 7, 3, 10, '10:5AM', 'active', '350', '2018-09-05 00:00:00', 0, 4, '4-8', 'up'),
(29, 7, 4, 10, '10:5AM', 'active', '900', '2018-09-05 00:00:00', 0, 2, '9-10', 'up'),
(30, 7, 1, 8, '6AM', 'active', '480', '2018-09-05 00:00:00', 0, 2, '9-10', 'up');

INSERT INTO stations (id, name, address, contact) VALUES
--(1, 'Dhaka', 'Dhaka, Comlapur', '01733435951');,
--(2, 'Dhaka Bimanbondor', 'Dhaka binmanbondor', '0202');,
--(3, 'Jamalpur', 'Jamalpur railstation', '01733435957');,
--(4, 'Islampur Bazar', 'Islampur , Jamalpur, Dhaka, Bangladesh', '01733');,
--(6, 'Mymensingh', 'Islampur , Jamalpur, Dhaka, Bangladesh', '01733435951');,
(7, 'Mymensingh 2', 'Islampur , Jamalpur, Dhaka, Bangladesh', '01733435951');

-- Dumping data for table trains
--

INSERT INTO trains (id, code, name, total_seat, type) VALUES
--(2, 'B207', 'Bromoputra', 560, 'intercity');,
--(3, 'j152', 'Jomuna', 650, 'intercity');,
--(4, 'SNB-59', 'Sunar Bangla', 700, 'intercity');,
(8, '105', 'Bus 1', 10, 'F-Class');,
(9, '106', 'Demo Bus name', 42, 'S-Chair'),
(10, '108', 'Demo Bus name 2', 10, 'F-Class');

INSERT INTO users (id, name, email, phone, address, password, rule, reg_date, last_activity) VALUES
(6, 'Md Rukon Shekh', 'admin', '123', 'dfsdsf', '123', 'admin', '05-FEB-18', '05-FEB-18');,
--(7, 'sasi', 'sasi', '7708016544', 'Chennai', 'sasi', 'passenger', '05-FEB-18', '05-FEB-18');,