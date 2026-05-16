<template>
    <div>
       <div class="container">
        <div class="title">
            <div class="back-home">
                <el-icon><Back /></el-icon>
                <span>返回首页</span>
            </div>
            <div class="title-text">
                <h2>登录</h2>
                <p>登录以继续使用心理健康AI助手</p>
            </div>
            <div class="form-container">
                <el-form
                    ref="ruleFormRef"
                    :model="formData"
                    :rules="rules"
                    label-position="top"
                >
                    <el-form-item label="用户名或邮箱" prop="username">
                        <el-input v-model="formData.username" size="large" placeholder="请输入用户名" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="formData.password" size="large" type="password" show-password placeholder="请输入密码" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm(ruleFormRef)" class="login-btn">登录</el-button>
                    </el-form-item>
                </el-form>
            </div>
         </div>
       </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const formData = ref({
    username: '',
    password: ''
})

const rules = ref({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ]
})

const ruleFormRef = ref()

const submitForm = async (formEL) => {
    if(!formEL) {
        return
    }
    await formEL.validate((valid,fields) => {   
        if(valid) {
            console.log(fields)
        }
    })
}
</script>

<style lang="scss" scoped>
.container {
    width: 300px;
    .title {
       .back-home {
            margin-bottom: 60px;
       }
       .title-text {
         text-align: center;
            h2 {
                font-size: 32px;
                margin-bottom: 10px;
            }
            p {
                font-size: 16px;
                color: #6b7280;
            }
       }
       .form-container {
            margin-top: 40px;
       }
       .login-btn {
            margin-top: 20px;
            width: 100%;
       }
       
    }
}
</style>

