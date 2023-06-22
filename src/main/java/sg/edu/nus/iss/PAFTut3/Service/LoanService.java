package sg.edu.nus.iss.PAFTut3.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.PAFTut3.Model.Customer;
import sg.edu.nus.iss.PAFTut3.Model.Loan;
import sg.edu.nus.iss.PAFTut3.Model.LoanDetails;
import sg.edu.nus.iss.PAFTut3.Model.Video;
import sg.edu.nus.iss.PAFTut3.Repository.CustomerRepo;
import sg.edu.nus.iss.PAFTut3.Repository.LoanDetailsRepo;
import sg.edu.nus.iss.PAFTut3.Repository.LoanRepo;
import sg.edu.nus.iss.PAFTut3.Repository.VideoRepo;

@Service
public class LoanService 
{
    @Autowired
    LoanRepo lRepo;
    @Autowired
    LoanDetailsRepo LDRepo;
    @Autowired
    CustomerRepo cRepo;
    @Autowired
    VideoRepo vRepo;
    
    public Boolean loanVideo(Customer customer, List<Video> videos)
    {
        Boolean loanSuccessful = false;
        //check that all videos are available, count > 0
        Boolean videoAvaliable = false;
        List<Video> allVideos = vRepo.findAll();

        for(Video video:videos)
        {
            List<Video> filteredVideoList = allVideos.stream()
                                            .filter(v->v.getId().equals(video.getId()))
                                            .collect(Collectors.toList());
            if(!filteredVideoList.isEmpty())
            {
                if(filteredVideoList.get(0).getAvaliablecount()==0)
                {
                    videoAvaliable=false;
                    //throw a custom exception / build in exception
                } else
                {
                    //reducing the video quantity in the video table
                    //for the video that the user loan
                    Video updateVideoEnt = filteredVideoList.get(0);
                    updateVideoEnt.setAvaliablecount(updateVideoEnt.getAvaliablecount()-1);
                    vRepo.updateVideo(updateVideoEnt);
                }
            }
        }
        //create a laon record
        //create the loan details that tie to the loan
        if(videoAvaliable)
        {
            Loan loan = new Loan();
            loan.setCustomerid(customer.getId());
            loan.setLoandate(Date.valueOf(LocalDate.now()));

            Integer createdLoanId = lRepo.createLoan(loan);
            for(Video video:videos)
            {
                LoanDetails loandetails = new LoanDetails();
                loandetails.setLoanid(createdLoanId);
                loandetails.setVideoid(video.getId());
                LDRepo.createLoanDetails(loandetails);
            }
            loanSuccessful=true;
        }
        return loanSuccessful;
    }
}
