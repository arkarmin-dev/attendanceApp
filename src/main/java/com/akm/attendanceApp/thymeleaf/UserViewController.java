package com.akm.attendanceApp.thymeleaf;

import com.akm.attendanceApp.data.enums.AttendanceType;
import com.akm.attendanceApp.data.models.User;
import com.akm.attendanceApp.data.repositories.UserRepository;
import com.akm.attendanceApp.features.userAttendanceManagement.service.UserAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserViewController {

    private final UserAttendanceService userAttendanceService;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        String username = principal.getName();
        LocalDate today = LocalDate.now();

        Long userId = userAttendanceService.getIdByUsername(username);

        boolean checkedIn = userAttendanceService.hasAttendanceToday(userId, today, AttendanceType.PUNCH_IN);
        boolean checkedOut = userAttendanceService.hasAttendanceToday(userId, today, AttendanceType.PUNCH_OUT);

        String inTime = userAttendanceService.getPunchLocalTimeToday(userId, AttendanceType.PUNCH_IN);
        String outTime = userAttendanceService.getPunchLocalTimeToday(userId, AttendanceType.PUNCH_OUT);
        model.addAttribute("localDate", today);
        model.addAttribute("inTime", inTime);
        model.addAttribute("outTime", outTime);
        model.addAttribute("username", username);
        model.addAttribute("hasCheckedInToday", checkedIn);
        model.addAttribute("hasCheckedOutToday", checkedOut);

        return "/user/index";
    }

    @PostMapping("/attendance/check-in")
    public String attendanceCheckIn(Principal principal, RedirectAttributes redirectAttributes) {
        try {
            User user = userRepository.findByUsername(principal.getName());
            userAttendanceService.createAttendance(user, AttendanceType.PUNCH_IN);
            redirectAttributes.addFlashAttribute("success", "Attendance check in successful");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Failed to check in: " + e.getMessage());
        }
        return "redirect:/user/home";
    }

    @PostMapping("/attendance/check-out")
    public String attendanceCheckOut(Principal principal, RedirectAttributes redirectAttributes) {
        try {
            User user = userRepository.findByUsername(principal.getName());
            userAttendanceService.createAttendance(user, AttendanceType.PUNCH_OUT);
            redirectAttributes.addFlashAttribute("success", "Attendance check out successful");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","Failed to check out: " + e.getMessage());
        }
        return "redirect:/user/home";
    }


}
