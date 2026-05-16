<template>
    <div>
        <PageHead pageTitle="知识文章">
            <template #buttons>
                <el-button type="primary">新增</el-button>
                <el-button type="primary">编辑</el-button>
            </template>
        </PageHead>
        <Tabelserch :formItem="formItem" @search="handleSearch"></Tabelserch>
    </div>
</template>

<script setup>
import PageHead from '@/components/PageHead.vue'
import Tabelserch from '@/components/Tabelserch.vue'

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
        placeholder: '请选择文章类型',
        options: [
            {
                label: '心理问题',
                value: '1'
            },
            {
                label: '心理健康',
                value: '2'
            }
        ]
    }
]

const handleSearch = (formData) => {
    console.log(formData,'formData')
}

</script>


