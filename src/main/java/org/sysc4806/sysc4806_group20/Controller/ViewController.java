package org.sysc4806.sysc4806_group20.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.sysc4806.sysc4806_group20.Model.*;

import org.sysc4806.sysc4806_group20.Model.Professor;
import org.sysc4806.sysc4806_group20.Model.ProgramRestrictions;
import org.sysc4806.sysc4806_group20.Model.Status;
import org.sysc4806.sysc4806_group20.Model.Student;

import org.sysc4806.sysc4806_group20.Service.ProfessorService;
import org.sysc4806.sysc4806_group20.Service.StudentService;
import org.sysc4806.sysc4806_group20.Service.TopicService;

import java.util.*;

@Controller
public class ViewController {
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping("/login")
    public String login(){ return "login";}

    /**
     * Testing
     */
    @GetMapping("/listStudentTopics")
    public String registerStudentTopicList(Model model, HttpSession session){
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_STUDENT") || session.getAttribute("userRole").equals("ROLE_ADMIN")) {
            long id = (long) session.getAttribute("userSpecialId");
            Student student = studentService.findById(id).orElseThrow(() -> new ResourceNotFoundException
                    ("No Student found with id" + id));
            model.addAttribute("topics", topicService.findAll());
            model.addAttribute("student_id", id);
            model.addAttribute("student", student);
            return "topicListStudent";
        }
        else return "login";
    }

    @GetMapping("/listTopics")
    public String topicList(Model model){
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("topics", topicService.findAll());
        return "topicList";
    }

    /**
     * For Testing only, maybe moved somewhere else later
     */
    @GetMapping("/newTopic")
    public String newTopic(Model model, HttpSession session){
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_PROFESSOR") || session.getAttribute("userRole").equals("ROLE_ADMIN")) {
            long id = (long) session.getAttribute("userSpecialId");
            model.addAttribute("status", new ArrayList<>(EnumSet.allOf(Status.class)));
            model.addAttribute("programs", new ArrayList<>(EnumSet.allOf(ProgramRestrictions.class)));
            model.addAttribute("profId", id);
            return "newTopic";
        }
        else return "login";
    }

    /**
     * For Testing
     */
    @GetMapping("/profprofile")
    public String profProfile(Model model, HttpSession session){
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_PROFESSOR")) {
            long id = (long) session.getAttribute("userSpecialId");
            Professor profreturn = professorService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Professor not found with id " + id));
            model.addAttribute("professor", profreturn);
            List<String> weekdays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
            model.addAttribute("weekdays", weekdays);

            List<List<String>> availabilityList = new ArrayList<>();

            Map<String, String> availability = profreturn.getAvailability();
            if (availability == null) {
                availability = new HashMap<>();
            }

            // Populate availabilityList
            for (String day : weekdays) {
                List<String> availableHours = new ArrayList<>();
                String timeRange = availability.get(day);

                if (timeRange != null && !timeRange.equals(" - ")) {
                    String[] times = timeRange.split(" - ");
                    String startHour = times[0].split(":")[0]; // Extract the hour part
                    String endHour = times[1].split(":")[0]; // Extract the hour part

                    // Create a list of hours from start to end
                    for (int i = Integer.parseInt(startHour); i <= Integer.parseInt(endHour); i++) {
                        availableHours.add(i + ":00");
                    }
                } else {
                    availableHours.add("Not Available");
                }

                availabilityList.add(availableHours);
            }

            // Add availability list to model
            model.addAttribute("availabilityList", availabilityList);

            System.out.println("Professor: " + profreturn);
            System.out.println("Availability: " + availability);
            System.out.println("Availability List: " + availabilityList);
            return "ProfProfile";
        }
        else return "login";
    }

    /**
     * For Testing
     */
    @GetMapping("/studentprofile")
    public String studentProfile(Model model, HttpSession session){
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_STUDENT")) {
            long id = (long) session.getAttribute("userSpecialId");
            Student studentreturn = studentService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
            model.addAttribute("student", studentreturn);
            List<String> weekdays = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
            model.addAttribute("weekdays", weekdays);

            List<List<String>> availabilityList = new ArrayList<>();

            Map<String, String> availability = studentreturn.getAvailability();
            if (availability == null) {
                availability = new HashMap<>();
            }

            // Populate availabilityList
            for (String day : weekdays) {
                List<String> availableHours = new ArrayList<>();
                String timeRange = availability.get(day);

                if (timeRange != null && !timeRange.equals(" - ")) {
                    String[] times = timeRange.split(" - ");
                    String startHour = times[0].split(":")[0]; // Extract the hour part
                    String endHour = times[1].split(":")[0]; // Extract the hour part

                    // Create a list of hours from start to end
                    for (int i = Integer.parseInt(startHour); i <= Integer.parseInt(endHour); i++) {
                        availableHours.add(i + ":00");
                    }
                } else {
                    availableHours.add("Not Available");
                }

                availabilityList.add(availableHours);
            }

            // Add availability list to model
            model.addAttribute("availabilityList", availabilityList);

            return "StudentProfile";
        }
        else return "login";
    }

    @GetMapping("/listTopics/{id}")
    public String getTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));
        model.addAttribute("topicView", topic);
        return "TopicView";
    }


    @GetMapping("/profprofile/{id}")
    public String removeTopic(@PathVariable Long id, HttpSession session) {
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_PROFESSOR")) {
            long pId = (long) session.getAttribute("userSpecialId");
            Topic topic = topicService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + id));

            Professor professor = topic.getProf();
            if (professor != null) {
                professor.removeTopic(topic);
                professorService.save(professor);
            }
            topicService.deleteById(id);
            return "redirect:/profprofile";
        }
        else return "login";
    }

    @GetMapping("/profprofile/search")
    public String searchForStudents(Model model, HttpSession session){
        if(session.getAttribute("userRole") == null) {return "login";}
        if(session.getAttribute("userRole").equals("ROLE_PROFESSOR")) {
            long pId = (long) session.getAttribute("userSpecialId");
            Iterable<Student> students = studentService.findAll();
            ArrayList<Student> studentsWithNoTopic = new ArrayList<>();
            for (Student s : students) {
                //if student has no topic
                if (!s.getHasTopic()) {
                    studentsWithNoTopic.add(s);
                }
            }
            model.addAttribute("studentsWithNoTopic", studentsWithNoTopic);

            return "NoTopicStudents";
        }
        else return "login";
    }


}