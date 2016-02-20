/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajariaku.pelatihan.dao;

import com.ajariaku.pelatihan.AplikasiPelatihanApplication;
import com.ajariaku.pelatihan.entity.Peserta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//dibutuhkan agar tes bisa dijalankan
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AplikasiPelatihanApplication.class)
//menjalankan pertama kali sebelum semua dijalankan dengan mengexecute script peserta.sql
@Sql (executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data/peserta.sql")
public class PesertaDaoTest {
    
    @Autowired
    private PesertaDao pd;
    
    @Autowired
    private DataSource ds;
    
       
    //dijalankan setelah semua test selesai dijalankan
    @After
    public void hapudData() throws SQLException{
        String sql = "delete from peserta where email = 'peserta.test.001@gmail.com'";
        try (Connection c = ds.getConnection()) {
            c.createStatement().executeUpdate(sql);
        }
    }

    @Test
    public void testInsert() throws SQLException  {
        Peserta p = new Peserta();
        //p.setId("001");
        p.setNama("Peserta 001");
        p.setEmail("peserta.test.001@gmail.com");
        p.setTanggalLahir(new Date());
        
        pd.save(p);
        
        String sql = "select count(*) as jumlah "
                + "from peserta "
                + "where email = 'peserta.test.001@gmail.com'";
        
        try (Connection c = ds.getConnection()) {
            ResultSet rs = c.createStatement().executeQuery(sql);
            //melakukan test, jika true(masih ada query selanjutnya) maka kursor akan pindah ke query yg selanjutnya
            Assert.assertTrue(rs.next());
            
            Long jumlahRow = rs.getLong("jumlah");
            //mengecek apakah jumlah row telah sama dengan yang dibuat
            Assert.assertEquals(1L, jumlahRow.longValue());
        }
    }
    
    
    
    @Test
    public void testHitung(){
        Long jumlah = pd.count();
        Assert.assertEquals(3L, jumlah.longValue());
    }
    
    @Test
    public void cari(){
        Peserta p = pd.findOne("003");
        //data harus ada
        Assert.assertNotNull(p);
        Assert.assertEquals("Peserta test 3", p.getNama());
        
        //test yang tidak ada di table
        Peserta px = pd.findOne("xxx");
        //data harus tidak ada/kosong
        Assert.assertNull(px);
        
    }
    
}
