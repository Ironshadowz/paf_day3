package sg.edu.nus.iss.PAFTut3.Model;

import java.sql.Date;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan 
{
    private Integer id;
    private Integer customerid;
    @FutureOrPresent(message="Borrow date must be current or future date")
    private Date loandate;
    @FutureOrPresent(message="Return date must be later than loan date")
    private Date returndate;
}
