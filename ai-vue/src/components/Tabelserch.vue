<template>
    <el-form :model="formData" ref="formRef">
        <el-row :gutter="24">
         <template v-for="item in formItemWithCol" :key="item.prop">
            <el-col v-bind="item.col">
            <el-form-item :label="item.label" :prop="item.prop" >
                <component v-model="formData[item.prop]" :is="isComp(item.comp)" :placeholder="item.placeholder" >
                <template v-if="item.comp === 'select'">
                    <el-option label="全部" value="" />
                    <el-option 
                    v-for="opt in item.options"
                    :label="opt.label"
                    :key="opt.value"
                    :value="opt.value" />
                </template>
                </component>
            </el-form-item>
            </el-col>
        
        </template>
        </el-row>
        <el-row>
            <el-button type="primary" @click="submitForm">查询</el-button>
            <el-button  @click="resetForm(formRef)" >重置</el-button>
        </el-row>
    </el-form>
</template>

<script setup>
import { ref,reactive,computed } from 'vue'

const formData = reactive({})

const props = defineProps({
    formItem: {
        type: Array,
        default: () => []
    }
})

const emit = defineEmits(['search'])
const formRef = ref(null)
const formItemWithCol = computed(() => {
   const {formItem} = props
   formItem.forEach(item => {
     item.col = {
        xs: 24,
        sm: 12,
        md: 8,
        lg: 6,
        xl: 4,
     }
   })
   return formItem
})

const isComp = (comp) => {
    return {
        input: 'elInput',
        select: 'elSelect',
    }[comp]
}

const submitForm = () => {
    emit('search', formData)
}

const resetForm = (formEL) => {
    // 重置表单再查询
    if(!formEL) {
        return
    }
    formEL.resetFields()
    emit('search', formData)
}



</script>