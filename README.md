## 学生管理系统

![展示图片](https://github.com/Masttf/stuManager/blob/master/src/images/image.png)

### 项目结构

```
project/
├── src/
│   ├── images/	
├── vue-springboot/          # Vue项目
│   ├── public/
│   ├── src/
│   └── .env.example
├── stuManager/           # Spring Boot项目
│   ├── src/
│   └── pom.xml
├── .gitignore
└── README.md
```

### 技术

- 前端使用vite vue3

- 后端使用springboot2

- 数据库mysql

### 快速上手

需要提交下好mysql数据库

```bash
cp vue-springboot/.env.example vue-springboot/.env
cp stuManager/src/main/resources/application.yml.example stuManager/src/main/resources/application.yml
```

在application.yml中修改自己数据库用户和密码

springboot后端的接口默认是8080如果修改，请把vue-springboot/.env中端口号也更改

vue前端的接口默认是5173，如果需要更改请在vue-springboot\vite.config.js 中修改端口号,  同时修改springboot的配置文件application.yml中前端的端口号



安装依赖

```bash
cd vue-springboot && npm install
```

前端启动

```bash
cd vue-springboot && npm run dev
```

后端启动

```bash
cd stuManager && mvn spring-boot:run
```

