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
                <div class="footer">
                    <span>还没有账号？ <router-link to="/register">去注册</router-link></span>
                </div>
            </div>
         </div>
       </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '@/api/admin'
import { useRouter } from 'vue-router'

const router = useRouter()

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
            console.log('表单数据:', formData.value)
            login(formData.value).then(res => {
                let loginData = null
                if (res && res.data) {
                    loginData = res.data
                } else if (res && res.token) {
                    loginData = res
                } else if (res && res.code === 200 && res.data) {
                    loginData = res.data
                }
                localStorage.setItem('token', loginData.token)
                localStorage.setItem('userInfo', JSON.stringify(loginData.userInfo || {}))

                if(loginData.userInfo.userType === 2){
                    router.push('/user/dashboard')
                } else{}

            }).catch(err => {
                console.error('登录错误:', err)
            })
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
            margin-top: 30px;
       }
       .login-btn {
            margin-top: 20px;
            width: 100%;
       }
       .footer {
            margin-top: 20px;
            text-align: center;
       }
    }
}
</style>

