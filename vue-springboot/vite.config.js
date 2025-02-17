import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  plugins: [vue()],
  server: {
    host: '0.0.0.0', // 这个用于启动
    port: 5173, // 指定启动端口
    open: true //启动后是否自动打开浏览器
  }
})
