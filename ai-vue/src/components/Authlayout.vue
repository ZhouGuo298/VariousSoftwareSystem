<template>
    <div class="auth-layout">
        <div class="auth-container">
            <div class="left-section">
                <div class="content">
                    <h2 class="title">心理健康AI助手</h2>
                    <p class="text">为您的心理健康提供专业的支持</p>
                    <div class="robot">
                        <el-image :src="logoUrl" alt="心理健康AI助手" style="width: 120px; height: 120px;"/>
                    </div>
                </div>
            </div>
            <div class="right-section">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
const logoUrl = ref(new URL('@/assets/logo.svg', import.meta.url).href)
</script>

<style lang="scss" scoped>
.auth-layout {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #5A9A8F 0%, #4A857A 100%);
    padding: 20px;
}

.left-section {
    flex: 1;
    background: linear-gradient(135deg, #5A9A8F 0%, #4A857A 100%);
    padding: 60px 40px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: white;
    border-radius: 16px 0 0 16px;
}

.content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 20px;
}

.text {
    font-size: 16px;
    line-height: 1.8;
    opacity: 0.9;
    margin-bottom: 40px;
}

.robot {
    width: 120px;
    height: 120px;
}

.right-section {
    flex: 1;
    background: white;
    padding: 60px 50px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 0 16px 16px 0;
}

.auth-container {
    display: flex;
    background: white;
    border-radius: 16px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
    overflow: hidden;
    width: 900px;
    min-height: 600px;
}

@media (max-width: 768px) {
    .auth-container {
        flex-direction: column;
        width: 90%;
        max-width: 400px;
    }

    .left-section {
        padding: 40px 30px;
        border-radius: 16px 16px 0 0;
    }

    .right-section {
        padding: 40px 30px;
        border-radius: 0 0 16px 16px;
    }
}
</style>
