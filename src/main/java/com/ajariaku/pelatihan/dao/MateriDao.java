package com.ajariaku.pelatihan.dao;

import com.ajariaku.pelatihan.entity.Materi;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MateriDao extends PagingAndSortingRepository<Materi, String>{
    
}
