package sg.edu.nus.iss.PAFTut3.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.PAFTut3.Model.Loan;

@Repository
public class LoanRepo 
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String selectAQL = "select * from loan";
    private final String insertSQL = "insert into loan values (null, ?, ?, ?)";
    
    public List<Loan> findAllLoans()
    {
        return jdbcTemplate.query(selectAQL, BeanPropertyRowMapper.newInstance(Loan.class));
    }
    public Integer createLoan(Loan loan)
    {
        KeyHolder generateKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() 
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String [] {"id"});
                ps.setInt(1, loan.getCustomerid());
                ps.setDate(2, loan.getLoandate());
                ps.setDate(3, loan.getReturndate());
                return null;
            }
        };
        jdbcTemplate.update(psc, generateKey);
        Integer result = generateKey.getKey().intValue();
        return result;
    }
}
