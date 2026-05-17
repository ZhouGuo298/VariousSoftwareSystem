<template>
    <div>
        <PageHead pageTitle="知识文章">
            <template #buttons>
                <el-button type="primary">新增</el-button>
                <el-button type="primary">编辑</el-button>
            </template>
        </PageHead>
        <Tabelserch :formItem="formItem" @search="handleSearch"></Tabelserch>
        <el-table :data="tableData" style="width: 100%; margin-top: 25px;">
            <el-table-column label="文章标题" fixed="left">
                <template #default="scope">
                   <div style="display: flex;align-items: center;">
                    <el-icon> <timer /></el-icon>
                    <span>{{scope.row.title}}</span>
                   </div>
                </template>
            </el-table-column>
            <el-table-column label="文章类型" width="200">
                <template #default="scope">
                    {{categoryMap[scope.row.categoryId] || scope.row.categoryId}}
                </template>
            </el-table-column>
            <el-table-column prop="authorName" label="作者" width="150"></el-table-column>
            <el-table-column prop="readCount" label="阅读量" width="150"></el-table-column>
            <el-table-column prop="publishedAt" label="发布时间" width="150"></el-table-column>
            <el-table-column label="操作" width="250" fixed="right">
                <template #default="scope">
                   <el-button text type="primary">编辑</el-button>
                   <el-button v-if="scope.row.status === 0 || scope.row.status === 2" text type="success">发布</el-button>
                   <el-button v-if="scope.row.status === 1" text type="warning">下线</el-button>
                   <el-button  text type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            style="margin-top: 25px;"
            layout="prev, pager, next"
            :total="pagination.total"
            :page-size="pagination.size"
            @change="handleChange"
        />
       </div>
</template>

<script setup>
import PageHead from '@/components/PageHead.vue'
import Tabelserch from '@/components/Tabelserch.vue'
import { categoryTree, articleList } from '@/api/admin'
import { onMounted, reactive, ref } from 'vue'

// 生命周期钩子
// onMounted(() => {
//     categoryTree().then(data => {
//         console.log(data,'data')

//     })
// })

// 什么时候用ref，什么时候用reactive
// ref用于响应式数据，reactive用于响应式对象
// 当需要在模板中使用数据时，使用ref
// 当需要在模板中使用对象属性时，使用reactive

// 分类映射
const categoryMap = reactive({})
// 分类列表
const categories = ref([])
// 表格数据
const tableData = ref([])

const formItem = [
    {
        comp: 'input',
        prop: 'title',
        label: '文章标题',
        placeholder: '请输入文章标题'
    },
    {
        comp: 'select',
        prop: 'categoryId',
        label: '文章类型',
        placeholder: '请选择文章类型'
    },
    {
        comp: 'select',
        prop: 'status',
        label: '文章状态',
        placeholder: '请选择文章状态',
        options: [
            {
                label: '草稿',
                value: 0
            },
            {
                label: '已发布',
                value: 1
            },
            {
                label: '已下线',
                value: 2
            }
        ]
    }
]

// 分页改变时触发
const handleChange = (page) => {
    pagination.currentPage = page   
    handleSearch()
}

// 分页参数
const pagination = reactive({
    currentPage: 1,
    size: 10,
    total: 0
})

// 状态映射
const statusMap = {
    0: '草稿',
    1: '已发布',
    2: '已下线'
}

const handleSearch = async (formData) => {
    // 合并分页参数和查询参数
    const params = {
        ...pagination,
        ...formData
    }

    console.log('查询参数:', params)
    const res = await articleList(params)
    console.log('articleList响应:', res)
    
    // 提取数据，兼容多种返回格式
    const data = res?.data || res
    tableData.value = data?.records || data || []
    if (data?.total !== undefined) {
        pagination.total = data.total
    }
}



onMounted(async () => {
    // 初始化分类映射
    const res = await categoryTree()
    // 提取数据，兼容多种返回格式
    const data = res?.data || res || []
    
    categories.value = data.map(item => {
        categoryMap[item.id] = item.categoryName
        return {
            label: item.categoryName,
            value: item.id
        }
    })
    formItem[1].options = categories.value


    // 初始化查询
    handleSearch()
})
</script>


