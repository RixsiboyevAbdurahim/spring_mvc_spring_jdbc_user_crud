package entity.controller;

import entity.user.User;
import entity.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/home");
    }

    @GetMapping("/adduser")
    public ModelAndView adduser() {
        return new ModelAndView("/adduser");
    }

    @PostMapping("/adduser")
    public ModelAndView adduser(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        ModelAndView view = new ModelAndView("/home");
        User user = User.builder()
                .username(username)
                .password(password).build();
        userDao.save(user);
        return view;
    }

    @GetMapping("/showallusers")
    public ModelAndView showallusers() {
        ModelAndView view = new ModelAndView("/showallusers");
        List<User> users = userDao.getAllUsers();
        view.addObject("users", users);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam("id") int id) {
        ModelAndView view = new ModelAndView("/delete");
        view.addObject("id", id);
        return view;
    }

    @PostMapping("/deletewithid")
    public ModelAndView deletewithid(@RequestParam("id") int id) {
        ModelAndView view = new ModelAndView("/home");
        userDao.delete(id);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@RequestParam("id") int id) {
        ModelAndView view = new ModelAndView("/update");
        User user = userDao.getUserById(id);
        view.addObject("user", user);
        return view;
    }

    @PostMapping("/updatewithid")
    public ModelAndView updatewithid(
            @RequestParam("id") int id,
            @RequestParam("username") String username,
            @RequestParam("password") String password
            ) {
        ModelAndView view = new ModelAndView("/home");
        User user = userDao.getUserById(id);
        user.setUsername(username);
        user.setPassword(password);
        userDao.update(user);
        return view;
    }

}
