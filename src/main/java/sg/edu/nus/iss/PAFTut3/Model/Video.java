package sg.edu.nus.iss.PAFTut3.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video 
{
    private Integer id;
    private String title;
    private String synopsis;
    private int avaliablecount;
}
