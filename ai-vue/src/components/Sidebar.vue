<template>
   <el-aside :width="isAdmin ? '64px' : '264px'">
        <el-menu
            default-active="1"
            :collapse="isAdmin"
            :collapse-transition="false"
            @open="handleOpen"
            @close="handleClose"
            class="menu-style"
        >
            <div class="brand">
                <el-image :src="logoUrl" style="width: 50px; height: 50px; margin-right: 10px;"  alt="logo" />
                <div class="info-card" v-show="!isAdmin">
                    <h1 class="brand-title">心理健康AI助手</h1>
                    <p class="brand-subtitle">管理后台</p>
                </div>
            </div>
            <el-menu-item @click="selectMenu" v-for="item in router.options.routes[0].children" :index="item.path" :key="item.path">
                <el-icon><component :is="item.meta.icon" /></el-icon>
                <span>{{item.meta.title}}</span>
            </el-menu-item>
        </el-menu>
    </el-aside>
</template>

<script setup>
 import { computed } from 'vue'
 import { useRouter } from "vue-router"
 import { useAdminStore } from '@/stores/admin'
// import { R } from "vue-router/dist/router-CWoNjPRp.mjs"
 const router = useRouter()
 const logoUrl = new URL('@/assets/logo.png', import.meta.url).href 
 const isAdmin = computed(()=>useAdminStore().isAdmin)  // 监听isAdmin状态变化,使用Computed计算属性
 
 
 console.log(router,'router')
//  const handleOpen = () => {}
//  const handleClose = () => {}
 const selectMenu = (key) => {
    const currentRouter = router.options.routes[0]
    console.log(`${currentRouter.path}/${key.index}`)
    router.push(`${currentRouter.path}/${key.index}`)
 }

 </script>
 <style lang="scss" scoped>
 .menu-style {
    height: 100%;
    .brand {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
    background-color: #fff;
    border-bottom: 1px solid #e5e7eb;
    .info-card {
        .brand-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 5px;
            color: #1f2937;
        }
        .brand-subtitle {
            font-size: 14px;
            font-weight: normal;
            color: #6b7280;
        }
    }
 }
 }


 </style>