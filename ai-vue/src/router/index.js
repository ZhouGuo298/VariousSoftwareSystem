import { createRouter, createWebHistory } from 'vue-router'  
import BackEndLayout from '@/components/BackendLayout.vue'
import AuthLayout from '@/components/Authlayout.vue'

// 路由配置-嵌套路由
const BackEndLayoutRoutes = [
    {
        path:'/user',
        component:BackEndLayout,
        children:[
            {
                path:'dashboard',
                component:()=>import('@/views/dashboard.vue'),
                meta:{
                    title:'数据分析',
                    icon:'PieChart'
                }
            },
            {
                path:'knowledge',
                component:()=>import('@/views/knowledge.vue'),
                meta:{
                    title:'知识文章',
                    icon:'ChatLineSquare'
                }
            },
            {
                path:'consultations',
                component:()=>import('@/views/consultations.vue'),
                meta:{
                    title:'咨询记录',
                    icon:'Message'
                }
            },
            {
                path:'emotion',
                component:()=>import('@/views/emotion.vue'),
                meta:{
                    title:'情绪日志',
                    icon:'User'
                }
            }
        ]   
    },
    {
        path:'/auth',
        component:AuthLayout,
        children:[
            {
                path:'login',
                component:()=>import('@/views/login.vue'),
                meta:{
                    title:'登录'
                }
            }
        ],
    },
    {
        path:'/',
        redirect:'/user/dashboard'
    }

]

// 路由实例
const router = createRouter({
    history:createWebHistory(),  // 路由模式
    routes:BackEndLayoutRoutes   // 路由配置
})

export default router


