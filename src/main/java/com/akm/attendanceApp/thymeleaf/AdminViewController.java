package com.akm.attendanceApp.thymeleaf;

import com.akm.attendanceApp.features.adminAttendanceManagement.service.AdminAttendanceService;
import com.akm.attendanceApp.features.adminUserManagement.dto.response.UserResponse;
import com.akm.attendanceApp.features.adminUserManagement.service.adminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminViewController {

    private final AdminAttendanceService adminAttendanceService;
    private final adminUserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("attendanceList", adminAttendanceService.getAllUsersAttendances());
        return "admin/dashboard";
    }

    @GetMapping("/manageUserPage")
    public String manageUserPage(Model model) {
        List<UserResponse> userList = userService.getAllUsers();

        model.addAttribute("users", userList);
        return "admin/manageUserPage";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            // Add a success message for the UI
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            // Handle database constraint errors (e.g., user has attendance records)
            redirectAttributes.addFlashAttribute("error", "Could not delete user. They may have attendance history.");
        }
        return "redirect:/admin/manageUserPage"; // Redirect to your Manage Users GET mapping
    }
}
