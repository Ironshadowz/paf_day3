package sg.edu.nus.iss.PAFTut3.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.PAFTut3.Model.Customer;

@Repository
public class CustomerRepo 
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSql="select * from customer";
    private final String findByIdSql="select * from customer where id=?";
    private final String findAllLimitOffsetSql="select * from customer limit ? offset ?";
    private final String insertSQL="insert into customer values(null, ?, ?)";
    private final String updateSQL="update customer set firstname=?, lastname=? where id=?)";
    
    public List<Customer> getAllCustomers()
    {
        List<Customer> customerList = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllSql);
        while(rs.next())
        {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstname(rs.getString("firstname"));
            customer.setLastname(rs.getString("lastname"));
            customerList.add(customer);
        }
        return Collections.unmodifiableList(customerList);
    }

    public List<Customer> getCustomersLimitOffset(int limit, int offset)
    {
        List<Customer> customerList = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllLimitOffsetSql, limit, offset);
        while(rs.next())
        {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstname(rs.getString("firstname"));
            customer.setLastname(rs.getString("lastname"));
            customerList.add(customer);
        }
        return Collections.unmodifiableList(customerList);
    }

    public Customer getCustomerById(int id)
    {
        Customer customer = new Customer();
        customer = jdbcTemplate.queryForObject(findByIdSql, BeanPropertyRowMapper.newInstance(Customer.class), id);
        return customer;
    }

    public Integer createCustomer(Customer customer)
    {
        KeyHolder generateKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() 
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String[] {"id"});
                ps.setString(1, customer.getFirstname());
                ps.setString(2, customer.getLastname());
                return ps;
            }
        };
        jdbcTemplate.update(psc, generateKey);
        Integer createCustomerId = generateKey.getKey().intValue();
        return createCustomerId;
    }

    public Integer updateCustomer(Customer customer)
    {
        int update = 0;
        update = jdbcTemplate.update(updateSQL, customer.getFirstname(), customer.getLastname(), customer.getId());
        return update;
    }
}
