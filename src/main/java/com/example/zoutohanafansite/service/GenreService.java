package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.genre.Genre;
import com.example.zoutohanafansite.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * ジャンル全取得
     *
     * @return List<Genre>
     */
    public List<Genre> selectAllGenre() {
        return genreRepository.selectAllGenre();
    }
}
