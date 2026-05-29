package com.jobboard;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import java.util.List;
 
// ==================== DATABASE ENTITY ====================
// This is what a Job looks like in the database
@Entity
@Table(name = "jobs")

class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String company;
    private String description;
    private String salary;

    public Job(){

    }
    
    public Job(String title, String company, String description, String salary) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.salary = salary;
    }

    public Long getID(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getCompany(){
        return company;
    }
    public void setCompany(String company){
        this.company = company;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getSalary(){
        return salary;
    }
    public void setSalary(String salary){
        this.salary = salary;
    }

    
}
 
// ==================== DATABASE ACCESS ====================
// This tells Spring: "Here's how to talk to the database"
interface JobRepository extends JpaRepository<Job, Long> {
}
 
// ==================== API ENDPOINTS ====================
// This handles HTTP requests (POST, GET, DELETE)
@RestController
@RequestMapping("/api/jobs")
class JobController {
    private final JobRepository jobRepository;
    
    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    // POST /api/jobs - Create a new job
    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }
    
    // GET /api/jobs - Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    // GET /api/jobs/{id} - Get a single job
    @GetMapping("/{id}")
    public Job getJob(@PathVariable Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    
    // DELETE /api/jobs/{id} - Delete a job
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }
}
 
// ==================== APPLICATION STARTUP ====================
// This starts the server
@SpringBootApplication
public class JobBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobBoardApplication.class, args);
    }
}