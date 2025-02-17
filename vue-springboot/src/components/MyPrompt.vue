<script setup>
import { ref, watch, nextTick } from 'vue'

const props = defineProps({
title: {
    type: String,
    default: '请输入'
},
message: String,
placeholder: {
    type: String,
    default: ''
},
defaultValue: {
    type: String,
    default: ''
}
})

const emit = defineEmits(['confirm', 'cancel'])

const visible = ref(false)
const inputValue = ref('')
const inputField = ref(null)

// 自动聚焦输入框
watch(visible, (val) => {
if (val) {
    nextTick(() => {
    inputField.value.focus()
    })
}
})

// 打开弹窗
const open = (defaultVal = '') => {
inputValue.value = defaultVal || props.defaultValue
visible.value = true
}

// 确认操作
const confirm = () => {
if (inputValue.value.trim()) {
    emit('confirm', inputValue.value)
    visible.value = false
}
}

// 取消操作
const cancel = () => {
emit('cancel')
visible.value = false
}

// 暴露方法给父组件
defineExpose({ open })
</script>
<template>
    <div v-if="visible" class="prompt-overlay">
      <div class="prompt-modal">
        <div class="prompt-header">
          <h3>{{ title }}</h3>
        </div>
        <div class="prompt-body">
          <label v-if="message">{{ message }}</label>
          <input
            ref="inputField"
            type="text"
            v-model="inputValue"
            :placeholder="placeholder"
            @keyup.enter="confirm"
          />
        </div>
        <div class="prompt-footer">
          <button class="cancel-btn" @click="cancel">取消</button>
          <button class="confirm-btn" @click="confirm">确认</button>
        </div>
      </div>
    </div>
</template>


<style scoped>
.prompt-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.prompt-modal {
    background: white;
    border-radius: 8px;
    width: 400px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    animation: modalSlideIn 0.3s ease-out;
}

.prompt-header {
    padding: 16px;
    border-bottom: 1px solid #eee;
}

.prompt-header h3 {
    margin: 0;
    color: #333;
    font-size: 18px;
}

.prompt-body {
    padding: 20px;
}

.prompt-body label {
    display: block;
    margin-bottom: 8px;
    color: #666;
}

.prompt-body input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    transition: border-color 0.3s;
}

.prompt-body input:focus {
    outline: none;
    border-color: #2196f3;
    box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.2);
}

.prompt-footer {
    padding: 16px;
    border-top: 1px solid #eee;
    text-align: right;
}

button {
    padding: 8px 16px;
    margin-left: 8px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.2s;
}

.confirm-btn {
    background-color: #2196f3;
    color: white;
}

.confirm-btn:hover {
    background-color: #1976d2;
}

.cancel-btn {
    background-color: #f5f5f5;
    color: #666;
}

.cancel-btn:hover {
    background-color: #eee;
}

@keyframes modalSlideIn {
from {
    transform: translateY(-20px);
    opacity: 0;
}
to {
    transform: translateY(0);
    opacity: 1;
}
}
</style>