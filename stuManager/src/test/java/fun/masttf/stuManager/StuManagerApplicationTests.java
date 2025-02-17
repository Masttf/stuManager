package fun.masttf.stuManager;

import fun.masttf.stuManager.config.JwtUtil;
import fun.masttf.stuManager.entity.Student;
import fun.masttf.stuManager.service.PermissionService;
import fun.masttf.stuManager.service.RoleService;
import fun.masttf.stuManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Work72ApplicationTests {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private fun.masttf.stuManager.service.StudentService studentService;
	@Test
	void contextLoads() {
		String token = jwtUtil.generateToken("admin");
		System.out.println(token);
		System.out.println(jwtUtil.extractUsername(token));
	}
	@Test
	void init(){
		roleService.add("admin");
		roleService.add("user");
		permissionService.add("admin");
		permissionService.add("user");
		userService.add("admin","123456");
		userService.add("user","123456");
		userService.addRole("admin","admin");
		userService.addRole("user","user");
		roleService.addPermission("admin","admin");
		roleService.addPermission("user","user");
		// for(Integer i = 1; i <= 20; i++){
		// 	Student student = new Student();
		// 	student.setName("stu"+i);
		// 	student.setAge(18 + i);
		// 	student.setGpa(5.0 - i * 0.1);
		// 	if(i % 2 == 1)student.setSex("男");
		// 	else student.setSex("女");
		// 	studentService.add(student);
		// }
	}
}
