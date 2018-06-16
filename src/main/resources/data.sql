/*Id need to be set for negative values because
  https://softwareengineering.stackexchange.com/questions/356896/using-import-sql-vs-persisting-jpa-entities
*/
INSERT INTO Company (id, name, address, city, country, email, phone, owners) VALUES
 (-1, 'Dis Parturient Montes Company', '3237 Sapien. St.', 'Bristol','Equatorial Guinea', 'aaa@aaa.pl', '+45 777 322 666', 'Joe Nol;Tristan Hox'),
 (-2, 'Egestas Sed Pharetra Foundation','Ap #557-7938 Eget Ave','Madison','Sao Tome and Principe', 'aaa@aaa.pl', '+45 444 333 222', 'Tristan Hox'),
 (-3, 'Morbi Industries','617-2870 Ultrices Ave','Asse','Liechtenstein', 'aaa@aaa.pl', '+45 855 855 855', 'Cola Briks'),
 (-4, 'Mauris Magna Duis PC','Ap #799-9385 Accumsan Av.','Zedelgem','Jamaica','rrr@aaa.pl', '+45 232 552 323', 'Tristan Hox;Jenny Roth'),
 (-5, 'Nisi LLC','599-6649 Imperdiet Rd.','Schaarbeek','Sao Tome and Principe', 'dsd@aaa.pl', '+45 232 366 323', 'Tristan Hox');

