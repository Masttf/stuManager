<script setup>

import { ref, reactive, computed, watch} from 'vue'
import axios from 'axios'
// 初始测试数据
const backendPort = import.meta.env.VITE_backendPort
const students = ref([])
const showform = ref(true)
const showlogin = ref(false);
const msg = ref('登录')
const token = ref('')
const username = ref('')
const password = ref('')
const rspMsg = ref('')
async function getStudentList(){
    const res = await axios({
        url : 'http://localhost:'+backendPort+'/student/',
        method : 'get',
        headers :{
            "token" : token.value,
        }
    })
    students.value=res.data.data
    // console.log(res.data)
}
// 分页相关状态
const currentPage = ref(1)
const pageSize = 5

// 计算总页数
const totalPages = computed(() => {
    return Math.ceil(filteredStudents.value.length / pageSize)
})

// 分页后的学生数据
const paginatedStudents = computed(() => {
    const start = (currentPage.value - 1) * pageSize
    const end = start + pageSize
    return filteredStudents.value.slice(start, end)
})

// 翻页方法
function prevPage(){
  if (currentPage.value > 1) currentPage.value--
}

function nextPage(){
  if (currentPage.value < totalPages.value) currentPage.value++
}
// 模态框相关状态
const showModal = ref(false)
const currentStudent = ref({})
const editableStudent = reactive({
    id: '',
    name: '',
    age: '',
    gpa: '',
    sex: ''
})

// 过滤条件
const filterType = ref('all')
const searchTerm = ref('')

// 计算属性处理过滤
const filteredStudents = computed(() => {
    switch(filterType.value) {
        case 'topGpa':
        return [...students.value]
            .sort((a, b) => b.gpa - a.gpa)
        case 'byId':
        return students.value.filter(s => s.id.toString().includes(searchTerm.value))
        case 'byName':
        return students.value.filter(s => s.name.includes(searchTerm.value))
        default:
        return students.value
    }
})

// 操作方法
function openEditModal(student){
    currentStudent.value = student
    Object.assign(editableStudent, student)
    showModal.value = true
}

function closeModal(){
    showModal.value = false
}

async function saveChanges (){
    const res = await axios({
        url : 'http://localhost:'+backendPort+'/student/update/' + editableStudent.id,
        method : 'post',
        headers :{
            "token" : token.value,
        },
        data : {
            name : editableStudent.name,
            age : editableStudent.age,
            sex : editableStudent.sex,
            gpa : editableStudent.gpa,
        },
    })
    if(res.data.rspCode != 200){
        alert("没有权限")
    }else{
        getStudentList()
        closeModal()
    }
}

async function deleteStudent (id){
    const res = await axios({
        url : 'http://localhost:'+backendPort+'/student/delete/' + id,
        method : 'post',
        headers :{
            "token" : token.value,
        }
    })
    if(res.data.rspCode != 200){
        alert("没有权限")
    }else{
        getStudentList()
    }
}

// 过滤方法
const showAll = () => {
  filterType.value = 'all'
  currentPage.value = 1
}

const showTopGpa = () => {
  filterType.value = 'topGpa'
  currentPage.value = 1
}

const searchById = () => {
  filterType.value = 'byId'
  searchTerm.value = prompt('请输入要搜索的ID:')
  currentPage.value = 1
}

const searchByName = () => {
  filterType.value = 'byName'
  searchTerm.value = prompt('请输入要搜索的姓名:')
  currentPage.value = 1
}
const showadd = ref(false)
function addStudent (){
    currentStudent.value = {
        id: '',
        name: '',
        age: '',
        gpa: '',
        sex: ''
    }
    Object.assign(editableStudent, currentStudent.value)
    showadd.value = true
}
async function saveAddStudent(){
    const res = await axios({
        url : 'http://localhost:'+backendPort+'/student/add',
        method : 'post',
        headers :{
            "token" : token.value,
        },
        data : {
            id : editableStudent.id,
            name : editableStudent.name,
            age : editableStudent.age,
            sex : editableStudent.sex,
            gpa : editableStudent.gpa,
        },
    })
    if(res.data.rspCode != 200){
        alert("没有权限")
    }else{
        getStudentList()
        closeAddStudent()
    }
}
function closeAddStudent(){
    showadd.value = false
    console.log(showadd.value)
}
function choseop(){
    // console.log(msg.value)
    if(msg.value == '登录'){
        back()
    }else{
        ext()
    }
}

async function login(){
    const res = await axios({
        url : 'http://localhost:'+backendPort+'/login',
        method : 'POST',
        data : {
            username : username.value,
            password : password.value
        },
    })
    // console.log(res.data)
    rspMsg.value = res.data.rspMsg
    username.value = ''
    password.value = ''
    console.log(rspMsg.value)
    if(res.data.rspCode == 200){
        token.value = res.data.data.token
        getStudentList()
        // console.log(token.value)
        msg.value = '登出'
        back()
    }
}
function ext(){
    rspMsg.value = ''
    token.value = ''
    username.value = ''
    password.value = ''
    msg.value = '登录'
    // console.log(msg.value)
    currentPage.value = 1
    students.value = []
}
function back(){
    showform.value = !showform.value
    showlogin.value = !showlogin.value
}
</script>
<template>
    <div v-show="showlogin" class="login-container">
        <div class="err">{{ rspMsg }}</div>
        <div class="form-group">
            <label for="username">用户名</label>
            <input 
                v-model="username"
                type="text" 
                id="username" 
                placeholder="请输入用户名"
                required
            >
        </div>

        <div class="form-group">
            <label for="password">密码</label>
            <input 
                v-model="password"
                type="password" 
                id="password" 
                placeholder="请输入密码"
                required
            >
        </div>
        <div class="group1">
            <div @click="back()" class="button">返回</div>
            <div @click="login()" class="button">登录</div>
        </div>
        
    </div>
    
    <div v-show="showform" class="student-manager">
        <div class="tittle">StudentManager</div>
        <!-- 添加学生 -->
        <div v-show="showadd" class="modal">
        <div class="modal-content">
            <h3>添加学生信息</h3>
            <form @submit.prevent="saveAddStudent()">
            <div class="form-group">
                <label>姓名:</label>
                <input type="text" v-model="editableStudent.name" :placeholder="currentStudent.name">
            </div>
            <div class="form-group">
                <label>性别:</label>
                <select v-model="editableStudent.sex">
                <option value="男">男</option>
                <option value="女">女</option>
                </select>
            </div>
            <div class="form-group">
                <label>年龄:</label>
                <input type="number" v-model="editableStudent.age" :placeholder="currentStudent.age">
            </div>
            <div class="form-group">
                <label>GPA:</label>
                <input type="number" step="0.1" v-model="editableStudent.gpa" :placeholder="currentStudent.gpa">
            </div>
            
            <div class="modal-actions">
                <button type="button" @click="closeAddStudent()">取消</button>
                <button type="submit">保存</button>
            </div>
          </form>
        </div>
        </div>
        <!-- 操作栏 -->
        <div class="action-bar">
        <div class="filters">
            <button @click="showAll">全部学生</button>
            <button @click="searchByName">按姓名搜索</button>
            <button @click="searchById">按ID搜索</button>
            <button @click="showTopGpa">GPA Top</button>
            <button @click="addStudent">添加学生</button>
        </div>
        <button class="login-btn" @click="choseop()">{{ msg }}</button>
        </div>
  
        <!-- 学生列表 -->
        <div class="student-list">
        <div v-for="student in paginatedStudents" :key="student.id" class="student-item">
            <div class="info">
            <span>ID: {{ student.id }}</span>
            <span>姓名: {{ student.name }}</span>
            <span>性别: {{ student.sex }}</span>
            <span>年龄: {{ student.age }}</span>
            <span>GPA: {{ student.gpa }}</span>
            </div>
            <div class="actions">
            <button @click="openEditModal(student)">修改</button>
            <button @click="deleteStudent(student.id)">删除</button>
            </div>
        </div>
        </div>
        <!-- 分页控件 -->
        <div class="pagination">
        <button 
            @click="prevPage()" 
            :disabled="currentPage === 1"
            :class="{ disabled: currentPage === 1 }"
        >
            上一页
        </button>
        <span>第 {{ currentPage }} 页 / 共 {{ totalPages }} 页</span>
        <button 
            @click="nextPage()" 
            :disabled="currentPage === totalPages"
            :class="{ disabled: currentPage === totalPages }"
        >
            下一页
        </button>
        </div>
      <!-- 编辑模态框 -->
        <div v-show="showModal" class="modal">
        <div class="modal-content">
            <h3>修改学生信息</h3>
            <form @submit.prevent="saveChanges">
            <div class="form-group">
                <label>ID:</label>
                <input type="text" :value="currentStudent.id" disabled>
            </div>
            <div class="form-group">
                <label>姓名:</label>
                <input type="text" v-model="editableStudent.name" :placeholder="currentStudent.name">
            </div>
            <div class="form-group">
                <label>性别:</label>
                <select v-model="editableStudent.sex">
                <option value="男">男</option>
                <option value="女">女</option>
                </select>
            </div>
            <div class="form-group">
                <label>年龄:</label>
                <input type="number" v-model="editableStudent.age" :placeholder="currentStudent.age">
            </div>
            <div class="form-group">
                <label>GPA:</label>
                <input type="number" step="0.1" v-model="editableStudent.gpa" :placeholder="currentStudent.gpa">
            </div>
            
            <div class="modal-actions">
                <button type="button" @click="closeModal()">取消</button>
                <button type="submit">保存</button>
            </div>
          </form>
        </div>
        </div>
    </div>
    
</template>
  
  
<style scoped>
.login-container {
    position: fixed;
    background: white;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    width: 300px;
}
.err{
    color: red;
}
.login-container .form-group {
    margin-bottom: 1.5rem;
}

.login-container label {
    display: block;
    margin-bottom: 0.5rem;
    color: #666;
}

.login-container input {
    width: 100%;
    padding: 0.8rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}
.login-container .group1 {
    width: 100%;
    display: flex;
    justify-content: space-between;
}
.login-container .button {
    width: 45%;
    padding: 0.8rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    text-align: center;
    box-sizing: border-box
}

.login-container .button:hover {
    background-color: #0056b3;
}
.tittle{
    width: 100%;
    background-color: white;
    text-align: center;
    font-size: xx-large;
    font-weight: 600;
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: solid gainsboro;
}
.student-manager {
    width: 70%;
    height: 80%;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    /* box-sizing: border-box; */
    position: relative;
}

.action-bar {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
}
button{
    height: 40px;
}
.filters button {
    box-sizing: border-box;
    margin-right: 10px;
    padding: 8px 12px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.login-btn {
    padding: 8px 16px;
    background: #2196F3;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.student-list {
    border: 1px solid #ddd;
    border-radius: 4px;
}

.student-item {
    display: flex;
    justify-content: space-between;
    padding: 15px;
    border-bottom: 1px solid #eee;
}

.student-item:last-child {
    border-bottom: none;
}
.info{
    display: flex;
    align-items: center;
}
.info span {
    margin-right: 15px;
}

.actions button {
    margin-left: 10px;
    padding: 5px 10px;
    background: #ff5722;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
}

.modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 2;
}

.modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    width: 400px;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
}

.form-group input,
.form-group select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

.modal-actions {
    margin-top: 20px;
    text-align: right;
}

.modal-actions button {
    margin-left: 10px;
    padding: 8px 16px;
}

.pagination {
    position: absolute;
    bottom: 20px;
    left: 0px;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 15px;
}

.pagination button {
    padding: 8px 16px;
    background: #2196F3;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.pagination button.disabled {
    background: #cccccc;
    cursor: not-allowed;
}

.pagination span {
    color: #666;
}
</style>
