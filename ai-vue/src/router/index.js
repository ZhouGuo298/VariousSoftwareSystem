import { createRouter, createWebHistory } from 'vue-router'  
import BackEndLayout from '@/components/backendlayout.vue'
import AuthLayout from '@/components/Authlayout.vue'

// 路由配置-嵌套路由
const BackEndLayoutRoutes = [
    {
        path:'/user',
        redirect:'/user/dashboard',
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
                path:'chat',
                component:()=>import('@/views/chat.vue'),
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
            },
            {
                path:'register',
                component:()=>import('@/views/register.vue'),
                meta:{
                    title:'注册'
                }
            }
        ],
    }
]

// 路由实例
const router = createRouter({
    history:createWebHistory(),  // 路由模式
    routes:BackEndLayoutRoutes   // 路由配置
})

// 路由前置守卫
router.beforeEach((to,from,next)=>{
    const token = localStorage.getItem('token')
    // 当前是否登录
    if(token){
        const userInfo = JSON.parse(localStorage.getItem('userInfo'))
        if(userInfo.userType == 2){
            if(to.path.startsWith('/user')){
                next()
            }else{
                next('/user/dashboard')
            }
        } else if(userInfo.userType == 1){
            next('/')
        }
    }else{
        if(to.path.startsWith('/user')){
            // 如果访问后台页面，且没有token，重定向到登录页
            next('/auth/login')
        }else{
            next()
        }
    }
})

export default router


