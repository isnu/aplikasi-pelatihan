delete from peserta_pelatihan;
delete from sesi;

insert into sesi (id, id_materi, mulai, sampai) 
values ('s01', 'm002', '2015-01-01', '2015-01-05');

insert into sesi (id, id_materi, mulai, sampai) 
values ('s02', 'm002', '2015-01-08', '2015-01-12');

insert into sesi (id, id_materi, mulai, sampai) 
values ('s03', 'm003', '2015-01-01', '2015-01-05');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s01', '002');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s01', '003');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s01', '004');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s02', '002');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s02', '004');

insert into peserta_pelatihan (id_sesi, id_peserta) 
values ('s01', '003');