/*
 * Penghubung antara Database / Entity dengan web
 * 
 * PagingAndSorting merupakan fungsi yang berisi kumpulan perintah CRUD
 */
package com.ajariaku.pelatihan.dao;

import com.ajariaku.pelatihan.entity.Peserta;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PesertaDao extends PagingAndSortingRepository<Peserta, String>{
    
}
