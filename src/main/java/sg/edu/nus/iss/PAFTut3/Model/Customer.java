package sg.edu.nus.iss.PAFTut3.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer 
{
    private Integer id;
    private String firstname;
    private String lastname;   
}
