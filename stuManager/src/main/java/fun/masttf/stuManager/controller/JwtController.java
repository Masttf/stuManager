package fun.masttf.stuManager.controller;

import fun.masttf.stuManager.config.JwtUtil;
import fun.masttf.stuManager.entity.LoginUser;
import fun.masttf.stuManager.result.ExceptionMsg;
import fun.masttf.stuManager.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
class temp_user{
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public ResponseData createAuthenticationToken(@RequestBody temp_user user){
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        //使用用户名和密码进行身份验证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        System.out.println("Authentication successful for user: " + username);
        //如果验证失败，返回错误信息
        if(Objects.isNull(authentication)){
            return new ResponseData(ExceptionMsg.FAILED, "username or password error");
        }
        //获取用户详细信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //生成JWT token
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        //返回给前端
        Map map = new HashMap();
        map.put("token",jwtToken);
        //postman token填值后即可登入
        //redis

        //返回成功信息
        return new ResponseData(ExceptionMsg.SUCCESS,map);
    }
}

