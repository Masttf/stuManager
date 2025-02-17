<script setup>
import { ref, defineEmits } from 'vue';
import axios from 'axios'
const username = ref('')
const password = ref('')
const emit = defineEmits(['loginSuc'])
async function submitlogin(){
    const res = await axios({
        url : 'http://localhost:8083/login',
        method : 'POST',
        data : {
            username : username.value,
            password : password.value
        },
    })
    console.log(res)
}
function back(){
    
}
</script>
<template>
    <div class="login-container">
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
        <div @click="submitlogin()" class="button">登录</div>
        <div @click="back()" class="button">返回</div>
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

.form-group {
    margin-bottom: 1.5rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    color: #666;
}

input {
    width: 100%;
    padding: 0.8rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

.button {
    width: 100%;
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

.button:hover {
    background-color: #0056b3;
}
</style>
