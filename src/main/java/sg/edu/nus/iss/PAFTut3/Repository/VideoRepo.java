package sg.edu.nus.iss.PAFTut3.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.PAFTut3.Model.Video;

@Repository
public class VideoRepo 
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private String findAllVideosSQL = "select * from video";
    private String insertVideoSQL = "insert into video(title, synopsis, available_count) values (?,?,?)";
    private String updateVideoSQL = "update video set title=?, synopsis=?, available_count=? where id=?";

    public List<Video>findAll()
    {
        return jdbcTemplate.query(findAllVideosSQL, BeanPropertyRowMapper.newInstance(Video.class));
    }
    public int updateVideo(Video video)
    {
        Integer result = jdbcTemplate.update(updateVideoSQL, video.getTitle(), video.getSynopsis(), video.getAvaliablecount(), video.getId());
        return result;
    }
    public int insertVideo(Video video)
    {
        Integer result = jdbcTemplate.update(insertVideoSQL, video.getTitle(), video.getSynopsis(), video.getAvaliablecount());
        return result;
    }
}
