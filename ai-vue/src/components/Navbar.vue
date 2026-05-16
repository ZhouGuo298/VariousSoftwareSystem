<template>
    <div class="navbar"> 
        <div class="flex-box">
            <el-button  @click="handleExpandClick">
                <el-icon><Expand /></el-icon>
            </el-button>
            <p class="page-title">导航栏</p>
        </div>    
        <div class="flex-box">
            <el-dropdown @command="handleCommand">
                <div class="flex-box">
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
                    <p class="user-name">admin</p>
                    <el-icon><ArrowDown /></el-icon>
                </div>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item command="1">退出登录</el-dropdown-item>
                        <el-dropdown-item command="2">选项2</el-dropdown-item>
                        <el-dropdown-item command="3">选项3</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script setup>
import { useAdminStore } from '@/stores/admin'
import { ref } from 'vue'

const handleCommand = (command) => {
    console.log(command)
    if (command === 'logout') {
        
    }
}
const handleExpandClick = () => {
    useAdminStore().toggleAdminStatus()
}

</script>

<style lang="scss" scoped>
.navbar {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 15px;
    background: white;
    border-bottom: 1px solid #e5e7eb;
    .flex-box {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 10px;
    }
    .page-title {
        margin-left: 20px;
        font-size: 26px;
        font-weight: bold;
        color: #1f2937;
    }
}

</style>