package com.ajariaku.pelatihan.dao;

import com.ajariaku.pelatihan.entity.Materi;
import com.ajariaku.pelatihan.entity.Sesi;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SesiDao extends PagingAndSortingRepository<Sesi, String>{
    //jika ingin menggunakan paging kita harus tau page berapa
    public Page<Sesi> findByMateri(Materi m, Pageable page);
    
    //melakukan query method custom
    //x, m, s, k merupakan nama variable
    @Query ("select x from Sesi x where x.mulai>=:m "
            + "and x.mulai<:s "
            + "and x.materi.kode = :k "
            + "order by x.mulai desc"
    )
    public Page<Sesi> cariBerdasarkanTanggalMulai(
            @Param("m") Date mulai, 
            @Param("s") Date sampai, 
            @Param("k") String kode, 
            Pageable page);
}
