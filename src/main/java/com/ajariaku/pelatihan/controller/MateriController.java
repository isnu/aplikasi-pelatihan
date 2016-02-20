package com.ajariaku.pelatihan.controller;

import com.ajariaku.pelatihan.dao.MateriDao;
import com.ajariaku.pelatihan.entity.Materi;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MateriController {
    
    @Autowired private MateriDao md;
    
    @RequestMapping(value = "/materi", method = RequestMethod.GET)
    public Page<Materi> daftarMateri(Pageable page){
        return md.findAll(page);
    }
    
        //delete data
    @RequestMapping(value="/materi/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void hapusMateri(@PathVariable("id") String id){
        md.delete(id);
    }
    
        //menambah data
    @RequestMapping(value="/materi", method = RequestMethod.POST)
    //menambahkan status created jika input berhasil
    @ResponseStatus(HttpStatus.CREATED)
    //request body untuk menambahkan parameter yang ada di body
    public void insertMateriBaru(@RequestBody @Valid Materi m){
        md.save(m);
    }


}
