package com.ajariaku.pelatihan.dao;

import com.ajariaku.pelatihan.AplikasiPelatihanApplication;
import com.ajariaku.pelatihan.entity.Materi;
import com.ajariaku.pelatihan.entity.Peserta;
import com.ajariaku.pelatihan.entity.Sesi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//dibutuhkan agar tes bisa dijalankan
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AplikasiPelatihanApplication.class)
//menjalankan pertama kali sebelum semua dijalankan dengan mengexecute script peserta.sql
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/data/peserta.sql", "/data/materi.sql", "/data/batch.sql"}
)
public class SesiDaoTest {

    @Autowired
    private SesiDao sd;

    @Autowired
    private DataSource ds;

    @Test
    public void testCariByMateri() {
        Materi m = new Materi();
        m.setId("m002");
        //halaman yang di request dan berapa banyaknya field per halaman
        PageRequest page = new PageRequest(0, 5);

        Page<Sesi> hasilQuery = sd.findByMateri(m, page);
        //karena cuma 1 page yang akan tampil
        Assert.assertEquals(2L, hasilQuery.getTotalElements());

        //getContent menghasilkan array, karena cuma 1 jadi yang diambil index pertama
        Sesi s = hasilQuery.getContent().get(0);
        Assert.assertNotNull(s);
        Assert.assertEquals("Java Web", s.getMateri().getNama());
    }

    @Test
    public void testCariBerdasarkanTanggalMulai() throws ParseException {
        PageRequest page = new PageRequest(0, 5);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formatter.parse("2015-01-01");
        Date sampai = formatter.parse("2015-01-03");

        Page<Sesi> hasil = sd.cariBerdasarkanTanggalMulai(sejak, sampai, "JF-002", page);

        Assert.assertEquals(1L, hasil.getTotalElements());
        Assert.assertFalse(hasil.getContent().isEmpty());

        Sesi s = hasil.getContent().get(0);
        Assert.assertEquals("Java Web", s.getMateri().getNama());
    }

    @Test
    public void testSaveSesi() throws Exception {
        Peserta p1 = new Peserta();
        p1.setId("002");

        Peserta p2 = new Peserta();
        p2.setId("003");

        Materi m = new Materi();
        m.setId("m004");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sejak = formatter.parse("2015-03-01");
        Date sampai = formatter.parse("2015-03-03");

        //melakukan save ke table sesi
        Sesi s = new Sesi();
        s.setMateri(m);
        s.setMulai(sejak);
        s.setSampai(sampai);
        s.getDaftarPeserta().add(p1);
        s.getDaftarPeserta().add(p2);

        sd.save(s);
        //cek id baru dari sesi
        String idSesiBaru = s.getId();
        Assert.assertNotNull(idSesiBaru);

        String sql = "select count(*) from sesi where id_materi='m004'";
        String sqlManyToMany = "select count(*) from peserta_pelatihan where id_sesi=?";
        try (Connection c = ds.getConnection()) {
            //cek tabel sesi
            ResultSet rs = c.createStatement().executeQuery(sql);
            Assert.assertTrue(rs.next());
            Assert.assertEquals(1L, rs.getLong(1));

            //cek tabel relasi many to many dengan peserta
            //query ke table menggunakan parameter (?)
            PreparedStatement ps = c.prepareStatement(sqlManyToMany);
            ps.setString(1, idSesiBaru);
            ResultSet rs2 = ps.executeQuery();

            Assert.assertTrue(rs2.next());
            Assert.assertEquals(2L, rs2.getLong(1));
        }

    }
}
