package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.genre.Genre;
import com.example.zoutohanafansite.entity.review.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GenreMapper {
    @Select("SELECT * FROM genres WHERE deleted = false")
    List<Genre> selectAllGenre();
}
