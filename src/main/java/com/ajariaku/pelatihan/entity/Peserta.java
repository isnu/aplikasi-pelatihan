/*
 * kelas untuk membuat Table pada databases dengan nama Peserta
 */
package com.ajariaku.pelatihan.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;



@Entity //membuat entitas / tabel dengan nama peserta
public class Peserta {
    //Membaca perintah dibawah sebagai Primary Key
    //menambahkan fitur uuid (universally unique id), melakukan penambahan unique id secara otomatis dengan strategi penambahan sequence di belakangnya
    @Id @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(nullable = false)
    private String id;
    
    @Column(nullable = false, length = 50)
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 100)
    private String nama;
    
    @Column(nullable = false, unique = true) //jenis kolom not null & uniq(tidak boleh ada yg sama)
    @NotEmpty
    @NotNull
    @Email
    private String email;
    
    @Column(name = "tanggal_lahir", nullable = false)
    @Temporal(TemporalType.DATE)
    @Past
    private Date tanggalLahir;

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
