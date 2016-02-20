package com.ajariaku.pelatihan.controller;

import com.ajariaku.pelatihan.dao.PesertaDao;
import com.ajariaku.pelatihan.entity.Peserta;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PesertaController {
    
    @Autowired
    private PesertaDao pd;
    
    //mengambil seluruh data
    @RequestMapping(value="/peserta", method = RequestMethod.GET)
    public Page<Peserta> cariPeserta(Pageable page){
        return pd.findAll(page);
    }
    
    //menambah data
    @RequestMapping(value="/peserta", method = RequestMethod.POST)
    //menambahkan status created jika input berhasil
    @ResponseStatus(HttpStatus.CREATED)
    //request body untuk menambahkan parameter yang ada di body
    public void insertPesertaBaru(@RequestBody @Valid Peserta p){
        pd.save(p);
    }
    
    
    //mengupdate data sesuai id yang diinput
    @RequestMapping(value="/peserta/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatePeserta(@PathVariable("id") String id, @RequestBody @Valid Peserta p){
        p.setId(id);
        pd.save(p);
    }
    
    //menampilkan 1 data sesuai id yang diinput
    @RequestMapping(value="/peserta/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Peserta> cariPesertaById(@PathVariable("id") String id){
        Peserta hasil = pd.findOne(id);
        //menambahkan error 404 jika data yang dicari tidak ada
        if(hasil == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hasil, HttpStatus.OK);
    }
    
    //delete data
    @RequestMapping(value="/peserta/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void hapusPeserta(@PathVariable("id") String id){
        pd.delete(id);
    }
}
